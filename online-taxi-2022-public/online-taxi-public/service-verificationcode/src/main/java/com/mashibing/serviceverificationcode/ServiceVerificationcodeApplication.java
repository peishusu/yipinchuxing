package com.mashibing.serviceverificationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 10:55
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVerificationcodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVerificationcodeApplication.class,args);
    }
}
