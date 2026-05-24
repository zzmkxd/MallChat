package com.abin.mallchat.common.user.domain.vo.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;


/**
 * Description: 拉黑目标
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackReq {

    @NotNull
    @Schema(description = "拉黑目标uid")
    private Long uid;

}
