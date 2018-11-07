package com.company.project.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.company.project.core.ServiceException;
import com.company.project.enums.ExceptionEnum;
import com.company.project.enums.ResourceEnum;
import com.company.project.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;


/**
 * @Author ：yaxuSong
 * @Description:
 * @Date: 15:28 2018/10/16
 * @Modified by:
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OSSClient ossClient;

    @Value("${oss.bucket}")
    private String bucketName;

    @Value("${oss.access}")
    private String access;

    public static final String HTTPS = "https://";

    public static final String POINT = ".";

    public static final String IMAGE_SUFFIX = ".png";

    public static final String EMPTY_STRING = "";
    public static final String LINEAE_STRING = "-";

    @Override
    public String uploadFile(MultipartFile file, ResourceEnum resourceEnum) {
        if (!file.isEmpty() && file != null) {
            String fileName = file.getOriginalFilename();
            log.info("file.getOriginalFilename:【{}】", fileName);

            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            try {
                //生成文件的随机名称
                String realFileName = UUID.randomUUID().toString().replace(LINEAE_STRING, EMPTY_STRING);
                ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
                //文件上传
                ossClient.putObject(bucketName, resourceEnum.getDir() + "/" + realFileName + POINT + suffix, bis);
                String location = HTTPS + access + "/" + resourceEnum.getDir() + "/" + realFileName + POINT + suffix;
                log.info("存储头像成功,文件访问路径为：{}", location);
                return location;
            } catch (OSSException oe) {
                log.error("OSS上传文件出错，msg = {}", oe.getMessage());
                throw new ServiceException(ExceptionEnum.FILE_UPLOAD_ERROR);
            } catch (ClientException ce) {
                log.error("OSS客户端连接错误，msg = {}", ce.getMessage());
                throw new ServiceException(ExceptionEnum.FILE_UPLOAD_ERROR);
            } catch (Exception e) {
                log.error("上传文件出错，msg = {}", e.getMessage());
                throw new ServiceException(ExceptionEnum.FILE_UPLOAD_ERROR);
            }
        } else {
            throw new ServiceException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public String uploadFile(String url, ResourceEnum resourceEnum) {
        String realFileName = UUID.randomUUID().toString().replace(LINEAE_STRING, EMPTY_STRING);
        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
            }
            InputStream inputStream = new URL(url).openStream();
            //文件上传
            ossClient.putObject(bucketName, resourceEnum.getDir() + "/" + realFileName + IMAGE_SUFFIX, inputStream);
            String location = HTTPS + access + "/" + resourceEnum.getDir() + "/" + realFileName + IMAGE_SUFFIX;
            log.info("存储头像成功,文件访问路径为：{}", location);
            return location;
        } catch (OSSException oe) {
            log.error("OSS上传文件出错，msg = {}", oe.getMessage());
            throw new ServiceException(ExceptionEnum.FILE_UPLOAD_ERROR);
        } catch (ClientException ce) {
            log.error("OSS客户端连接错误，msg = {}", ce.getMessage());
            throw new ServiceException(ExceptionEnum.FILE_UPLOAD_ERROR);
        } catch (Exception e) {
            log.error("上传文件出错，msg = {}", e.getMessage());
            throw new ServiceException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 上传网络服务器的图片并转储在oss
     *
     * @param url 头像url
     * @return
     */
    @Override
    public String getOssAvatarUrl(String url) {
        String avatarPath = UUID.randomUUID().toString().replace(LINEAE_STRING, EMPTY_STRING);
        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
            }
            InputStream inputStream = new URL(url).openStream();
            //上传图像
            ossClient.putObject(bucketName, ResourceEnum.IMAGE.getDir() + "/" + avatarPath + IMAGE_SUFFIX, inputStream);
            //获取图像地址
            String location = HTTPS + access + "/" + ResourceEnum.IMAGE.getDir() + "/" + avatarPath + IMAGE_SUFFIX;
            log.info("存储头像成功,文件访问路径为：{}", location);
            return location;
        } catch (OSSException oe) {
            log.error("OSS上传文件出错，msg = {}", oe.getMessage());
        } catch (ClientException ce) {
            log.error("OSS客户端连接错误，msg = {}", ce.getMessage());
        } catch (Exception e) {
            log.error("上传文件出错，msg = {}", e.getMessage());
        }
        return "";
    }
}
