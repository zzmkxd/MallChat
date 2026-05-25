package com.abin.mallchat.common.user.service.handler;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.user.service.WxMsgService;
    import me.chanjar.weixin.common.error.WxErrorException;
    import me.chanjar.weixin.common.session.WxSessionManager;
    import me.chanjar.weixin.mp.api.WxMpService;
    import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
    import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
    import org.springframework.stereotype.Component;
    import java.util.Map;
    @Component
@RequiredArgsConstructor
public class ScanHandler extends AbstractHandler {    
    private final WxMsgService wxMsgService;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 扫码事件处理
        return wxMsgService.scan(wxMpService, wxMpXmlMessage);
    }

}
