package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 11:10
 **/
@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){
        System.out.println("size:" + size);
        //生成验证码
        double mathRandom = (Math.random() * 9 + 1) * (Math.pow(10,size - 1));
        System.out.println(mathRandom);
        int captcher = (int)mathRandom;

        //定义返回值
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse(captcher);
//        data.put("numberCode",captcher);

        ResponseResult success = ResponseResult.success(numberCodeResponse);

        //测试：用于测试当api-passenger调用service-verification的获取验证码服务是，长时间未获得
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return success;
    }
}
