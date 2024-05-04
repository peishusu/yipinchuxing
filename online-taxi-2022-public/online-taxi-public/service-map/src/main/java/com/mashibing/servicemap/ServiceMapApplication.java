package com.mashibing.servicemap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 11:02
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mashibing.servicemap.mapper")
public class ServiceMapApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMapApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
