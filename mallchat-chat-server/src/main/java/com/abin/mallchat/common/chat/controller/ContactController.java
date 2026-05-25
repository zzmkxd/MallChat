package com.abin.mallchat.common.chat.controller;
    import com.abin.mallchat.common.chat.domain.vo.request.ContactFriendReq;
    import com.abin.mallchat.common.chat.domain.vo.response.ChatRoomResp;
    import com.abin.mallchat.common.chat.service.ChatService;
    import com.abin.mallchat.common.chat.service.RoomAppService;
    import com.abin.mallchat.common.common.domain.vo.request.CursorPageBaseReq;
    import com.abin.mallchat.common.common.domain.vo.request.IdReqVO;
    import com.abin.mallchat.common.common.domain.vo.response.ApiResult;
    import com.abin.mallchat.common.common.domain.vo.response.CursorPageBaseResp;
    import com.abin.mallchat.common.common.utils.RequestHolder;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import io.swagger.v3.oas.annotations.Operation;
    import lombok.extern.slf4j.Slf4j;
    import lombok.RequiredArgsConstructor;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import jakarta.validation.Valid;
    /**
 * <p>
 * 会话相关接口
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/capi/chat")
@Tag(name = "聊天室相关接口")
@Slf4j
@RequiredArgsConstructor
public class ContactController {    
    private final ChatService chatService;
    private final RoomAppService roomService;
    @GetMapping("/public/contact/page")
    @Operation(summary = "会话列表")
    public ApiResult<CursorPageBaseResp<ChatRoomResp>> getRoomPage(@Valid CursorPageBaseReq request) {
        Long uid = RequestHolder.get().getUid();
    return ApiResult.success(roomService.getContactPage(request, uid));
    }

    @GetMapping("/public/contact/detail")
    @Operation(summary = "会话详情")
    public ApiResult<ChatRoomResp> getContactDetail(@Valid IdReqVO request) {
        Long uid = RequestHolder.get().getUid();
    return ApiResult.success(roomService.getContactDetail(uid, request.getId()));
    }

    @GetMapping("/public/contact/detail/friend")
    @Operation(summary = "会话详情(联系人列表发消息用)")
    public ApiResult<ChatRoomResp> getContactDetailByFriend(@Valid ContactFriendReq request) {
        Long uid = RequestHolder.get().getUid();
    return ApiResult.success(roomService.getContactDetailByFriend(uid, request.getUid()));
    }
}

