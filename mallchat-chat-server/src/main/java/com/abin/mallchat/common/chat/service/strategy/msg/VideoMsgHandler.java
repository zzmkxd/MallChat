package com.abin.mallchat.common.chat.service.strategy.msg;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.chat.dao.MessageDao;
    import com.abin.mallchat.common.chat.domain.entity.Message;
    import com.abin.mallchat.common.chat.domain.entity.msg.MessageExtra;
    import com.abin.mallchat.common.chat.domain.entity.msg.VideoMsgDTO;
    import com.abin.mallchat.common.chat.domain.enums.MessageTypeEnum;
    import org.springframework.stereotype.Component;
    import java.util.Optional;
    /**
 * Description:视频消息
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-06-04
 */
@Component
@RequiredArgsConstructor
public class VideoMsgHandler extends AbstractMsgHandler<VideoMsgDTO> {    
    private final MessageDao messageDao;
    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.VIDEO;
    }

    @Override
    public void saveMsg(Message msg, VideoMsgDTO body) {
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
    Message update = new Message();
    update.setId(msg.getId());
    update.setExtra(extra);
    extra.setVideoMsgDTO(body);
    messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getVideoMsgDTO();
    }

    @Override
    public Object showReplyMsg(Message msg) {
        return "视频";
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[视频]";
    }
}
