package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 * 默认地址：http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Swagger框架api配置
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)

                //api信息
                .apiInfo(apiInfo())

                //API选择构造器
                .select()

                //控制器包名
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))

                //路径选择器
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api详细信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("牙医管家客户端Api接口")

                //详细描述
                .description("牙医管家客户端Api接口")

                //创建人
                .contact(new Contact("牙医管家客户端",
                        "42.192.116.184",
                        "2759100807@qq.com"))

                //版本号
                .version("1.0.0")

                //服务条款地址
                .termsOfServiceUrl("42.192.116.184")

                .build();
    }
}