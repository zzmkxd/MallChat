package com.abin.mallchat.common.chat.domain.vo.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @Author Kkuil
 * @Date 2023/10/24 12:46
 * @Description 撤销管理员请求信息
 */
@Data
public class AdminRevokeReq {
    @NotNull
    @Schema(description = "房间号")
    private Long roomId;

    @NotNull
    @Size(min = 1, max = 3)
    @Schema(description = "需要撤销管理的列表")
    private List<Long> uidList;
}
