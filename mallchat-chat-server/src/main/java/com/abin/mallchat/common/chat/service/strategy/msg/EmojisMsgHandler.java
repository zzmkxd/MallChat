package com.abin.mallchat.common.chat.service.strategy.msg;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.chat.dao.MessageDao;
    import com.abin.mallchat.common.chat.domain.entity.Message;
    import com.abin.mallchat.common.chat.domain.entity.msg.EmojisMsgDTO;
    import com.abin.mallchat.common.chat.domain.entity.msg.MessageExtra;
    import com.abin.mallchat.common.chat.domain.enums.MessageTypeEnum;
    import org.springframework.stereotype.Component;
    import java.util.Optional;
    /**
 * Description:表情消息
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-06-04
 */
@Component
@RequiredArgsConstructor
public class EmojisMsgHandler extends AbstractMsgHandler<EmojisMsgDTO> {    
    private final MessageDao messageDao;
    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.EMOJI;
    }

    @Override
    public void saveMsg(Message msg, EmojisMsgDTO body) {
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
    Message update = new Message();
    update.setId(msg.getId());
    update.setExtra(extra);
    extra.setEmojisMsgDTO(body);
    messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getEmojisMsgDTO();
    }

    @Override
    public Object showReplyMsg(Message msg) {
        return "表情";
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[表情包]";
    }
}
