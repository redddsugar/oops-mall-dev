package com.oops.config;

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
 * @author L-N
 * @Description    访问: http://localhost:8088/doc.html
 * @createTime 2021年01月19日 16:47
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    //配置swagger2核心配置docket
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)      //指定API类型为swagger2
                .apiInfo(apiInfo())                         //定义api文档汇总信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.oops.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Oops-Mall平台接口API")
                                    .contact(new Contact("Oops", "https://www.oops.com", "oops@email.com"))
                                    .description("为oops提供的文档")
                                    .version("1.0.1")
                                    .termsOfServiceUrl("https://www.oops.com")
                                    .build();
    }
}
