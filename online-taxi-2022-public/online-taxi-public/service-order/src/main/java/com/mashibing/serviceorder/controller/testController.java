package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-22 11:51
 **/
@RestController
@Slf4j
public class testController {

    @Autowired
    private OrderInfoService orderInfoService;


    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Value("${server.port}")
    private String port;


    @GetMapping("/test")
    public String test() {
        return "test";
    }



    /**
     * 测试
     *
     * @Description:
     * @Param: [orderId]
     * @return: java.lang.String
     * @Author: JiLaiYa
     * @Date: 2024/3/24
     */
    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatcherRealTimeOrder(@PathVariable("orderId") long orderId) {
        log.info("并发测试的值:" + orderId);
        log.info("端口号：" + port);
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfoService.dispatchRealTimeOrder(orderInfo);
        return "test-dispatcherRealTimeOrder success";
    }
}
