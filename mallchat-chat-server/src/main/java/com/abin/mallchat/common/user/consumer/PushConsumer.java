package com.abin.mallchat.common.user.consumer;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.common.constant.MQConstant;
    import com.abin.mallchat.common.common.domain.dto.PushMessageDTO;
    import com.abin.mallchat.common.user.domain.enums.WSPushTypeEnum;
    import com.abin.mallchat.common.user.service.WebSocketService;
    import org.apache.rocketmq.spring.annotation.MessageModel;
    import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
    import org.apache.rocketmq.spring.core.RocketMQListener;
    import org.springframework.stereotype.Component;
    /**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-08-12
 */
@RocketMQMessageListener(topic = MQConstant.PUSH_TOPIC, consumerGroup = MQConstant.PUSH_GROUP, messageModel = MessageModel.BROADCASTING)
@Component
@RequiredArgsConstructor
public class PushConsumer implements RocketMQListener<PushMessageDTO> {    
    private final WebSocketService webSocketService;
    @Override
    public void onMessage(PushMessageDTO message) {
        WSPushTypeEnum wsPushTypeEnum = WSPushTypeEnum.of(message.getPushType());
    switch (wsPushTypeEnum) {
            case USER:
                message.getUidList().forEach(uid -> {
                    webSocketService.sendToUid(message.getWsBaseMsg(), uid);
    });
    break;
    case ALL:
                webSocketService.sendToAllOnline(message.getWsBaseMsg(), null);
    break;
    }
    }
}
