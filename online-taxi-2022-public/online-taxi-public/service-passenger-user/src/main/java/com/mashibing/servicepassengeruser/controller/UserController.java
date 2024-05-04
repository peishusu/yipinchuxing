package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.servicepassengeruser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-15 10:54
 **/
@RestController
@Slf4j
public class UserController {
    
    @Autowired
    UserService userService;
    
    /** 
    * @Description: 乘客用户注册,只需要用户的手机号
    * @Param: [verificationCodeDTO]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/15
    */
    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){
        ResponseResult responseResult = userService.loginOrRegister(verificationCodeDTO.getPassengerPhone());
        System.out.println("loginOrRegister");
        return responseResult;
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone){
        //获取用户的电话
        ResponseResult passengerByPassengerPhone = userService.getUserByPhone(passengerPhone);
//        System.out.println("getUser");
        log.info("getUser");
        return passengerByPassengerPhone;

    }
}
