package com.mashibing.servicessepush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-26 16:19
 **/
@SpringBootApplication
@EnableDiscoveryClient

public class SseDriverClientWeb {

    public static void main(String[] args) {
        SpringApplication.run(SseDriverClientWeb.class,args);
    }

}
