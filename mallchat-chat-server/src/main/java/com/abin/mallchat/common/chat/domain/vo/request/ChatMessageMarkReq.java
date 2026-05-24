package com.abin.mallchat.common.chat.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * Description: 消息标记请求
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageMarkReq {
    @NotNull
    @Schema(description = "消息id")
    private Long msgId;

    @NotNull
    @Schema(description = "标记类型 1点赞 2举报")
    private Integer markType;

    @NotNull
    @Schema(description = "动作类型 1确认 2取消")
    private Integer actType;
}
