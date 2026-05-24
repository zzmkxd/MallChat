package com.abin.mallchat.common.chat.domain.vo.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @Author Kkuil
 * @Date 2023/10/24 12:46
 * @Description 添加管理员请求信息
 */
@Data
public class AdminAddReq {
    @NotNull
    @ApiModelProperty("房间号")
    private Long roomId;

    @NotNull
    @Size(min = 1, max = 3)
    @ApiModelProperty("需要添加管理的列表")
    private List<Long> uidList;
}
