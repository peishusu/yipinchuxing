package com.mashibing.servicepassengeruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-15 10:31
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mashibing.servicepassengeruser.mapper")
public class ServicePassengerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePassengerUserApplication.class,args);
    }
}
