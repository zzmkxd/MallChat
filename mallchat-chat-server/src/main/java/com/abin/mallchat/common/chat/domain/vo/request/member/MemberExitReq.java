package com.abin.mallchat.common.chat.domain.vo.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * @Author Kkuil
 * @Date 2023/10/30 11:49
 * @Description 退出群聊
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberExitReq {
    @NotNull
    @Schema(description = "会话id")
    private Long roomId;
}
