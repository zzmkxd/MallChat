package com.abin.mallchat.common.chat.domain.vo.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * Description: 移除群成员
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDelReq {
    @NotNull
    @Schema(description = "会话id")
    private Long roomId;

    @NotNull
    @Schema(description = "被移除的uid（主动退群填自己）")
    private Long uid;
}
