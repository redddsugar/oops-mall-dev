package com.oops.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月18日 20:53
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "Hello,world~";
    }
}
