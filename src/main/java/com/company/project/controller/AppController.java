package com.company.project.controller;

import com.company.project.core.Result;
import com.company.project.enums.ResourceEnum;
import com.company.project.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/app")
@Api(value = "app全局controller")
public class AppController {
    @Resource
    private OssService ossService;

    @ApiOperation(value = "图片上传")
    @PostMapping("upload")
    public Result<String> upload(MultipartFile file,
                                 @ApiParam(value = "上传的类型状态:0 image; 1 video;2 audio ", required = true)
                                 @RequestParam("type") Integer type) {

//    public Result<String> upload(HttpServletRequest request,
//                                 @ApiParam(value = "上传的类型状态:0 image; 1 video;2 audio ", required = true)
//                                 @RequestParam("type") Integer type) {
//        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartHttpServletRequest.getFile("file");

        log.info("上传的文件：【{}】,文件类型：【{}】", file, type);
//        返回的url地址s
        String resultUrl = "";
        switch (type) {
            case 0: {
                resultUrl = ossService.uploadFile(file, ResourceEnum.IMAGE);
            }
            break;
            case 1: {
                resultUrl = ossService.uploadFile(file, ResourceEnum.VIDEO);
            }
            break;
            case 2: {
                resultUrl = ossService.uploadFile(file, ResourceEnum.AUDIO);
            }
            break;
            default: {
            }
        }

        return Result.success(resultUrl);
    }

}

