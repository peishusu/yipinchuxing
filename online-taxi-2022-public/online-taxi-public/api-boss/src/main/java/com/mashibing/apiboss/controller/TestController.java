package com.mashibing.apiboss.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-04-18 23:56
 **/
@RestController
@RefreshScope
public class TestController {

    @Value("${testConfig}")
    private String config;

    @GetMapping("/testConfig")
    public String test(){
        return config;
    }
}
