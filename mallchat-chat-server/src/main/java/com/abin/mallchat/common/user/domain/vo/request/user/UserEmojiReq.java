package com.abin.mallchat.common.user.domain.vo.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Description: 表情包反参
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-07-09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEmojiReq {
    /**
     * 表情地址
     */
    @Schema(description = "新增的表情url")
    private String expressionUrl;

}
