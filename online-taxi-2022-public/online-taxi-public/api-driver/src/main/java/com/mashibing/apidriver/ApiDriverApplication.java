package com.mashibing.apidriver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 19:07
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiDriverApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiDriverApplication.class,args);
    }
}
