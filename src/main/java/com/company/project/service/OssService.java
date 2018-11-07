package com.company.project.service;

import com.company.project.enums.ResourceEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ：yaxuSong
 * @Description:
 * @Date: 15:26 2018/10/16
 * @Modified by:
 */
public interface OssService {

    /**
     * 可通过上传文件的方式 上传资源
     * @param file 各种文件资源
     * @param resourceEnum 资源类型
     * @return
     */
    String uploadFile(MultipartFile file, ResourceEnum resourceEnum);

    /**
     * 可通过url的方式转存文件
     * @param url 文件链接
     * @param resourceEnum 资源类型
     * @return
     */
    String uploadFile(String url, ResourceEnum resourceEnum);

    /**
     * 通过url获取头像文件
     * @param url 头像url
     * @return
     */
    String getOssAvatarUrl(String url);
}
