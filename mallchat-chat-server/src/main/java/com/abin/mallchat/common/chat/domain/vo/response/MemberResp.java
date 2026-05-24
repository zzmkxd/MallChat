package com.abin.mallchat.common.chat.domain.vo.response;

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
public class MemberResp {
    @Schema(description = "房间id")
    private Long roomId;
    @Schema(description = "群名称")
    private String groupName;
    @Schema(description = "群头像")
    private String avatar;
    @Schema(description = "在线人数")
    private Long onlineNum;//在线人数
    /**
     * @see com.abin.mallchat.common.chat.domain.enums.GroupRoleAPPEnum
     */
    @Schema(description = "成员角色 1群主 2管理员 3普通成员 4踢出群聊")
    private Integer role;
}
