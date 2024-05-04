package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 09:30
 **/
@RestController
@Slf4j
public class testController {
    @GetMapping("/test")
    public String test() {
        return "test api";
    }

    @GetMapping("/auth")
    public ResponseResult testAuth() {
        return ResponseResult.success("auth test");
    }

    @GetMapping("/noauth")
    public ResponseResult testNoAuth() {
        return ResponseResult.success(" no auth test");
    }


    @Autowired
    private ServiceOrderClient serviceOrderClient;

    /**
     * @Description: 测试派单逻辑
     * @Param:
     * @return:
     * @Author: JiLaiYa
     * @Date: 2024/3/26
     */

    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatcherRealTimeOrder(@PathVariable("orderId") long orderId) {
        log.info("并发测试的值:" + orderId);
        serviceOrderClient.dispatcherRealTimeOrder(orderId);
        return "test-dispatcherRealTimeOrder success";
    }


}
