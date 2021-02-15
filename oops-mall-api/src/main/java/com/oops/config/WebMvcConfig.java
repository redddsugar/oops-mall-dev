package com.oops.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月30日 21:24
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //实现静态资源的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:E:\\home\\face\\")      //这个就是映射的路径，下面的文件就可以通过localhost:8088/xxx.png来访问
                .addResourceLocations("classpath:/META-INF/resources/"); //映射swagger2
    }
}
