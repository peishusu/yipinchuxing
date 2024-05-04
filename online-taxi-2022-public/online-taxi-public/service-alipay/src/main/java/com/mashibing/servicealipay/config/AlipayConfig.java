package com.mashibing.servicealipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @program: online-taxi-public
 * @description: 沙箱环境变量初始化配置
 * @author: lydms
 * @create: 2024-03-28 19:11
 **/
@Configuration
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayConfig {

    private String appId;
    private String appPrivateKey;

    private String publicKey;

    private String notifyUrl;

    @PostConstruct
    public void init(){
        Config config = new Config();
        //基础配置
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";
        //业务配置
        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.publicKey;
        //配置回调地址
        config.notifyUrl = this.notifyUrl;

        //
        Factory.setOptions(config);
        System.out.println("支付宝配置初始化完成");
    }


}
