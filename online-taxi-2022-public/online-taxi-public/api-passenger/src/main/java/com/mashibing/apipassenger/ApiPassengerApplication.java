package com.mashibing.apipassenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 09:30
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiPassengerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiPassengerApplication.class,args);
    }
}
