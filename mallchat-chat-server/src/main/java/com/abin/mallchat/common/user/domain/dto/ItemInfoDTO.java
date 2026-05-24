package com.abin.mallchat.common.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Description: 修改用户名
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoDTO {
    @Schema(description = "徽章id")
    private Long itemId;
    @Schema(description = "是否需要刷新")
    private Boolean needRefresh = Boolean.TRUE;
    @Schema(description = "徽章图像")
    private String img;
    @Schema(description = "徽章说明")
    private String describe;

    public static ItemInfoDTO skip(Long itemId) {
        ItemInfoDTO dto = new ItemInfoDTO();
        dto.setItemId(itemId);
        dto.setNeedRefresh(Boolean.FALSE);
        return dto;
    }
}
