package com.abin.mallchat.common.user.domain.vo.response.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Description: 徽章信息
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-22
 */
@Data
public class BadgeResp {

    @Schema(description = "徽章id")
    private Long id;

    @Schema(description = "徽章图标")
    private String img;

    @Schema(description = "徽章描述")
    private String describe;

    @Schema(description = "是否拥有 0否 1是")
    private Integer obtain;

    @Schema(description = "是否佩戴  0否 1是")
    private Integer wearing;
}
