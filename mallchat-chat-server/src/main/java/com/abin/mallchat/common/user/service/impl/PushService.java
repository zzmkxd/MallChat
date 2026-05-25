package com.abin.mallchat.common.user.service.impl;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.common.constant.MQConstant;
    import com.abin.mallchat.common.common.domain.dto.PushMessageDTO;
    import com.abin.mallchat.common.user.domain.enums.WSBaseResp;
    import com.abin.mallchat.transaction.service.MQProducer;
    import org.springframework.stereotype.Service;
    import java.util.List;
    /**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-08-12
 */
@Service
@RequiredArgsConstructor
public class PushService {    
    private final MQProducer mqProducer;
    public void sendPushMsg(WSBaseResp<?> msg, List<Long> uidList) {
        mqProducer.sendMsg(MQConstant.PUSH_TOPIC, new PushMessageDTO(uidList, msg));
    }

    public void sendPushMsg(WSBaseResp<?> msg, Long uid) {
        mqProducer.sendMsg(MQConstant.PUSH_TOPIC, new PushMessageDTO(uid, msg));
    }

    public void sendPushMsg(WSBaseResp<?> msg) {
        mqProducer.sendMsg(MQConstant.PUSH_TOPIC, new PushMessageDTO(msg));
    }
}
