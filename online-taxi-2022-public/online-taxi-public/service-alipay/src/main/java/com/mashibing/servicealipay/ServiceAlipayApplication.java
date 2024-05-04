package com.mashibing.servicealipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-28 18:42
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceAlipayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAlipayApplication.class,args);
    }
}
