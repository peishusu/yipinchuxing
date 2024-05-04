package com.mashibing.servicealipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.mashibing.servicealipay.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-28 19:22
 **/
@Controller
@RequestMapping("/alipay")
@ResponseBody
public class AlipayController {

    @Autowired
    private AlipayService alipayService;


    /**
    * @Description: 条往支付界面
    * @Param: [subject 收费项名称, outTradeNo 订单号, totalAmount 收费金额]
    * @return: java.lang.String
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */

    @GetMapping("/pay")
    public String pay(@RequestParam String subject, @RequestParam String outTradeNo, @RequestParam String totalAmount) {
        AlipayTradePagePayResponse response = null;
        try {
            response = Factory.Payment.Page().pay(subject, outTradeNo, totalAmount, "");
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return response.getBody();
    }

    /** 
    * @Description: 当支付宝沙箱收款后，进行服务端的回调
    * @Param: [request]
    * @return: java.lang.String
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */
    
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) throws Exception {
        String tradeStatus = request.getParameter("trade_status");
        if (tradeStatus.trim().equals("TRADE_SUCCESS")){
            Map<String,String> param = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();

            for (String name : parameterMap.keySet()){
                param.put(name,request.getParameter(name));
            }

            if (Factory.Payment.Common().verifyNotify(param)){
                System.out.println("通过支付宝的验证！！！");
                for (String name:
                     param.keySet()) {
                    System.out.println("收到并接受好的参数：");
                    System.out.println(name+","+param.get(name));
                }
                //回调服务端
                //outTradeNo就是订单号
                String outTradeNo = request.getParameter("out_trade_no");
                alipayService.pay(outTradeNo);


            }else{
                System.out.println("支付宝验证不通过！！！");
            }
        }
        return "success";
    }


}
