package com.abin.mallchat.common.chat.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;


/**
 * Description: 消息基础请求体
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageBaseReq {
    @NotNull
    @Schema(description = "消息id")
    private Long msgId;

    @NotNull
    @Schema(description = "会话id")
    private Long roomId;
}
