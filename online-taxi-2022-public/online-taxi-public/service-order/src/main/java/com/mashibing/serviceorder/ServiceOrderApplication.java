package com.mashibing.serviceorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-22 11:47
 **/
@SpringBootApplication
@MapperScan("com.mashibing.serviceorder.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class,args);
    }
}
