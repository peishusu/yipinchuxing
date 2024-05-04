package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.PayService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-28 16:37
 **/
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;


    /** 
    * @Description: 司机发起收款
    * @Param: [orderId, price]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */
    

    @PostMapping("/push-pay-info")
    public ResponseResult pushPayInfo(@RequestParam String orderId,@RequestParam String price, @RequestParam Long passengerId){
        return payService.pushPayInfo(orderId,price,passengerId);
    }
}
