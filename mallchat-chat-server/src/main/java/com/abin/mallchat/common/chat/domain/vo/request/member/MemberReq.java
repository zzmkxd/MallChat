package com.abin.mallchat.common.chat.domain.vo.request.member;

import com.abin.mallchat.common.common.domain.vo.request.CursorPageBaseReq;
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
public class MemberReq extends CursorPageBaseReq {
    @Schema(description = "房间号")
    private Long roomId = 1L;
}
