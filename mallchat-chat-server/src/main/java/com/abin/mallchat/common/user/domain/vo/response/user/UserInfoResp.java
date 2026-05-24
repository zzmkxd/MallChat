package com.abin.mallchat.common.user.domain.vo.response.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Description: 用户信息返回
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-22
 */
@Data
public class UserInfoResp {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户昵称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "性别 1为男性，2为女性")
    private Integer sex;

    @Schema(description = "剩余改名次数")
    private Integer modifyNameChance;

}
