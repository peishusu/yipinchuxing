package com.mashibing.servicealipay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-28 19:09
 **/
@RestController
public class TestController {
    //这是因为配置内网穿透，将外网地址映射到内网地址
    @PostMapping("/test")
    public String test(){
        System.out.println("支付宝回调啦");
        return "外网穿透测试";
    }
}
