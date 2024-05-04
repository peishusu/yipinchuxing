package com.mashibing.apidriver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 21:34
 **/
@RestController
public class TestTokenController {

    @GetMapping("/auth")
    public String auth(){
        return "auth";
    }


    @GetMapping("/noAuth")
    public String noauth(){
        return "noauth";
    }


}
