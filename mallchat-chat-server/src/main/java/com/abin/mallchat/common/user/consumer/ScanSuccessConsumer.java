package com.abin.mallchat.common.user.consumer;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.common.constant.MQConstant;
    import com.abin.mallchat.common.common.domain.dto.ScanSuccessMessageDTO;
    import com.abin.mallchat.common.user.service.WebSocketService;
    import org.apache.rocketmq.spring.annotation.MessageModel;
    import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
    import org.apache.rocketmq.spring.core.RocketMQListener;
    import org.springframework.stereotype.Component;
    /**
 * Description: 将扫码成功的信息发送给对应的用户,等待授权
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-08-12
 */
@RocketMQMessageListener(consumerGroup = MQConstant.SCAN_MSG_GROUP, topic = MQConstant.SCAN_MSG_TOPIC, messageModel = MessageModel.BROADCASTING)
@Component
@RequiredArgsConstructor
public class ScanSuccessConsumer implements RocketMQListener<ScanSuccessMessageDTO> {    
    private final WebSocketService webSocketService;
    @Override
    public void onMessage(ScanSuccessMessageDTO scanSuccessMessageDTO) {
        webSocketService.scanSuccess(scanSuccessMessageDTO.getCode());
    }

}
