package com.company.project.configurer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.company.project.core.ProjectConstant.CONTROLLER_PACKAGE;

/**
 * @author xiebq
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径，指的是我们在哪些类中使用swagger2来测试
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_PACKAGE))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot Api Seed 中使用Swagger2构建RESTful APIs")
                //描述
                .description("Spring Boot Api Seed")
                .termsOfServiceUrl("https://www.baidu.com")
                //创建者
                .contact(new Contact("syuutou", "https://www.baidu.com", ""))
                //版本号
                .version("1.0")
                .build();
    }

}
