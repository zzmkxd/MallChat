package com.abin.mallchat.common.common.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhongzb create on 2021/05/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdRespVO {
    @Schema(description = "id")
    private long id;

    public static IdRespVO id(Long id) {
        IdRespVO idRespVO = new IdRespVO();
        idRespVO.setId(id);
        return idRespVO;
    }
}
