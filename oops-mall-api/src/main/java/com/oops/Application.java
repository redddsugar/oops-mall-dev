package com.oops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月18日 20:51
 */
@SpringBootApplication
@MapperScan(basePackages = "com.oops.mapper")
@ComponentScan(basePackages = {"com.oops","org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
