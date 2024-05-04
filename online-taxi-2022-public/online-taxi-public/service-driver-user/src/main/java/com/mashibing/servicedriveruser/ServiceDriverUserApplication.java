package com.mashibing.servicedriveruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 09:41
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.mashibing.servicedriveruser.mapper")
public class ServiceDriverUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDriverUserApplication.class,args);
    }
}
