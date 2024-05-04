package com.mashibing.serviceverificationcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 09:30
 **/
@RestController
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "test service-verificationcode";
    }
}
