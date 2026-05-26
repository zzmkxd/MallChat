package com.abin.mallchat.common.chat.service.strategy.msg;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.chat.dao.MessageDao;
    import com.abin.mallchat.common.chat.domain.dto.ChatMsgRecallDTO;
    import com.abin.mallchat.common.chat.domain.entity.Message;
    import com.abin.mallchat.common.chat.domain.entity.msg.MessageExtra;
    import com.abin.mallchat.common.chat.domain.entity.msg.MsgRecall;
    import com.abin.mallchat.common.chat.domain.enums.MessageTypeEnum;
    import com.abin.mallchat.common.chat.service.cache.MsgCache;
    import com.abin.mallchat.common.common.event.MessageRecallEvent;
    import com.abin.mallchat.common.user.domain.entity.User;
    import com.abin.mallchat.common.user.service.cache.UserCache;
    import org.springframework.context.ApplicationEventPublisher;
    import org.springframework.stereotype.Component;
    import java.time.LocalDateTime;
    import java.util.Objects;
    /**
 * Description: 撤回文本消息
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * LocalDateTime: 2023-06-04
 */
@Component
@RequiredArgsConstructor
public class RecallMsgHandler extends AbstractMsgHandler<Object> {    
    private final MessageDao messageDao;
    private final UserCache userCache;
    private final MsgCache msgCache;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.RECALL;
    }

    @Override
    public void saveMsg(Message msg, Object body) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object showMsg(Message msg) {
        MsgRecall recall = msg.getExtra().getRecall();
    User userInfo = userCache.getUserInfo(recall.getRecallUid());
    if (!Objects.equals(recall.getRecallUid(), msg.getFromUid())) {
            return "管理员\"" + userInfo.getName() + "\"撤回了一条成员消息";
    }
        return "\"" + userInfo.getName() + "\"撤回了一条消息";
    }

    @Override
    public Object showReplyMsg(Message msg) {
        return "原消息已被撤回";
    }

    public void recall(Long recallUid, Message message) {//todo 消息覆盖问题用版本号解决
        MessageExtra extra = message.getExtra();
    extra.setRecall(new MsgRecall(recallUid, LocalDateTime.now()));
    Message update = new Message();
    update.setId(message.getId());
    update.setType(MessageTypeEnum.RECALL.getType());
    update.setExtra(extra);
    messageDao.updateById(update);
    applicationEventPublisher.publishEvent(new MessageRecallEvent(this, new ChatMsgRecallDTO(message.getId(), message.getRoomId(), recallUid)));
    }

    @Override
    public String showContactMsg(Message msg) {
        return "撤回了一条消息";
    }
}
