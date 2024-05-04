package com.mashibing.servicepassengeruser.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-15 10:31
 **/
@RestController
@RefreshScope
public class TestController {
    @Value("${application-ceshi}")
    private String ceshi;


    @GetMapping("/test")
    public String test(){
        return ceshi;
    }
}
