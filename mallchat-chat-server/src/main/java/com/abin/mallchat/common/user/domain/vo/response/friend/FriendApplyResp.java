package com.abin.mallchat.common.user.domain.vo.response.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Description: 好友校验
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendApplyResp {
    @Schema(description = "申请id")
    private Long applyId;

    @Schema(description = "申请人uid")
    private Long uid;

    @Schema(description = "申请类型 1加好友")
    private Integer type;

    @Schema(description = "申请信息")
    private String msg;

    @Schema(description = "申请状态 1待审批 2同意")
    private Integer status;
}
