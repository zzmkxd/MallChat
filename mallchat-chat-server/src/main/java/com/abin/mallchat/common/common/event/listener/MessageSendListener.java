package com.abin.mallchat.common.common.event.listener;
    import com.abin.mallchat.common.chat.dao.ContactDao;
    import com.abin.mallchat.common.chat.dao.MessageDao;
    import com.abin.mallchat.common.chat.dao.RoomDao;
    import com.abin.mallchat.common.chat.dao.RoomFriendDao;
    import com.abin.mallchat.common.chat.domain.entity.Message;
    import com.abin.mallchat.common.chat.domain.entity.Room;
    import com.abin.mallchat.common.chat.domain.enums.HotFlagEnum;
    import com.abin.mallchat.common.chat.service.ChatService;
    import com.abin.mallchat.common.chat.service.WeChatMsgOperationService;
    import com.abin.mallchat.common.chat.service.cache.GroupMemberCache;
    import com.abin.mallchat.common.chat.service.cache.HotRoomCache;
    import com.abin.mallchat.common.chat.service.cache.RoomCache;
    import com.abin.mallchat.common.chatai.service.IChatAIService;
    import com.abin.mallchat.common.common.constant.MQConstant;
    import com.abin.mallchat.common.common.domain.dto.MsgSendMessageDTO;
    import com.abin.mallchat.common.common.event.MessageSendEvent;
    import com.abin.mallchat.common.user.service.WebSocketService;
    import com.abin.mallchat.common.user.service.cache.UserCache;
    import com.abin.mallchat.transaction.service.MQProducer;
    import lombok.extern.slf4j.Slf4j;
    import lombok.RequiredArgsConstructor;
    import org.jetbrains.annotations.NotNull;
    import org.springframework.context.ApplicationEventPublisher;
    import org.springframework.stereotype.Component;
    import org.springframework.transaction.event.TransactionPhase;
    import org.springframework.transaction.event.TransactionalEventListener;
    import java.util.Objects;
    /**
 * 消息发送监听器
 *
 * @author zhongzb create on 2022/08/26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSendListener {    
    private final WebSocketService webSocketService;
    private final ChatService chatService;
    private final MessageDao messageDao;
    private final IChatAIService openAIService;
    private final WeChatMsgOperationService weChatMsgOperationService;
    private final RoomCache roomCache;
    private final RoomDao roomDao;
    private final GroupMemberCache groupMemberCache;
    private final UserCache userCache;
    private final RoomFriendDao roomFriendDao;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ContactDao contactDao;
    private final HotRoomCache hotRoomCache;
    private final MQProducer mqProducer;
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void messageRoute(MessageSendEvent event) {
        Long msgId = event.getMsgId();
    mqProducer.sendSecureMsg(MQConstant.SEND_MSG_TOPIC, new MsgSendMessageDTO(msgId), msgId);
    }

    @TransactionalEventListener(classes = MessageSendEvent.class, fallbackExecution = true)
    public void handlerMsg(@NotNull MessageSendEvent event) {
        Message message = messageDao.getById(event.getMsgId());
    Room room = roomCache.get(message.getRoomId());
    if (isHotRoom(room)) {
            openAIService.chat(message);
    }
    }

    public boolean isHotRoom(Room room) {
        return Objects.equals(HotFlagEnum.YES.getType(), room.getHotFlag());
    }

    /**
     * 给用户微信推送艾特好友的消息通知
     * （这个没开启，微信不让推）
     */
    @TransactionalEventListener(classes = MessageSendEvent.class, fallbackExecution = true)
    public void publishChatToWechat(@NotNull MessageSendEvent event) {
        Message message = messageDao.getById(event.getMsgId());
    if (Objects.nonNull(message.getExtra().getAtUidList())) {
            weChatMsgOperationService.publishChatMsgToWeChatUser(message.getFromUid(), message.getExtra().getAtUidList(),
                    message.getContent());
    }
    }
}
