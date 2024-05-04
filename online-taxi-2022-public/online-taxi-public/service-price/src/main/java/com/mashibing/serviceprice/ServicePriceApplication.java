package com.mashibing.serviceprice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 10:25
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.mashibing.serviceprice.mapper")
public class ServicePriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePriceApplication.class,args);
    }
}
