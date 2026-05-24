package com.abin.mallchat.common.user.controller;

import com.abin.mallchat.common.common.domain.vo.response.ApiResult;
import com.abin.mallchat.common.common.utils.RequestHolder;
import com.abin.mallchat.common.user.domain.vo.request.oss.UploadUrlReq;
import com.abin.mallchat.common.user.service.OssService;
import com.abin.mallchat.oss.domain.OssResp;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Description: oss控制层
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-06-20
 */
@RestController
@RequestMapping("/capi/oss")
@Tag(name = "oss相关接口")
public class OssController {
    @Autowired
    private OssService ossService;

    @GetMapping("/upload/url")
    @Operation(summary = "获取临时上传链接")
    public ApiResult<OssResp> getUploadUrl(@Valid UploadUrlReq req) {
        return ApiResult.success(ossService.getUploadUrl(RequestHolder.get().getUid(), req));
    }
}
