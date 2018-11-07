package com.company.project.configurer;

import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ：yaxuSong
 * @Description:
 * @Date: 17:31 2018/10/30
 * @Modified by:
 */
@Slf4j
@Configuration
public class OSSConfig {

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Bean
    public OSSClient ossClient(){
        try {
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            log.info("------start instantiaze oss client !------->");
            return ossClient;
        }catch (Exception e){
            log.error("------- oss client instantiaze failure.---------");
        }
        return null;
    }
}
