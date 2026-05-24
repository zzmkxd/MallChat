package com.abin.mallchat.common.chat.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-07-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgReadInfoDTO {
    @Schema(description = "消息id")
    private Long msgId;

    @Schema(description = "已读数")
    private Integer readCount;

    @Schema(description = "未读数")
    private Integer unReadCount;

}
