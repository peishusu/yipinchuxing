package com.mashibing.apidriver.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-sse-push")
public interface ServiceSsePushClient {
    /**
     * @Description: 发送消息
     * @Param: [userId 用户id, identity 身份类型, content 消息内容]
     * @return: java.lang.String
     * @Author: JiLaiYa
     * @Date: 2024/3/26
     */

    @GetMapping("/push")
    public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content);
}
