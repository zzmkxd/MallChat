package com.abin.mallchat.common.chat.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 群成员列表的成员信息
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMemberListResp {
    @Schema(description = "uid")
    private Long uid;
    @Schema(description = "用户名称")
    private String name;
    @Schema(description = "头像")
    private String avatar;
}
