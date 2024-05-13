package com.mi_repair.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:30
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {


    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket1() {
        log.info("准备生成用户端接口文档");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("小米售后维修服务工单履行系统项目接口文档")
                .version("1.0")
                .description("小米售后维修服务工单履行系统项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mi_repair.controller.user"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket docket2(){
        log.info("准备生成工程师端接口文档");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("小米售后维修服务工单履行系统项目接口文档")
                .version("1.0")
                .description("小米售后维修服务工单履行系统项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("工程师端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mi_repair.controller.worker"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
