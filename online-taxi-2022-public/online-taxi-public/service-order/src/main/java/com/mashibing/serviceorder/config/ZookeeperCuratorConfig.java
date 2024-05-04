package com.mashibing.serviceorder.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperCuratorConfig {

    @Value("${zookeeper.address}")
    String zookeeperAddress;

    @Bean
    public CuratorFramework curatorFramework(){

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeperAddress,retryPolicy);
        curatorFramework.start();
        return  curatorFramework;

    }
}