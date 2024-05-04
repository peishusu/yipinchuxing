package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.CheckVerificationCodeDTO;
import com.mashibing.apipassenger.request.SendVerificationCodeDTO;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 09:44
 **/
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /** 
    * @Description: 获取验证码
    * @Param: [verificationCodeDTO]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/15
    */
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@Validated @RequestBody SendVerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        ResponseResult generatorCode = verificationCodeService.generatorCode(verificationCodeDTO.getPassengerPhone());
        return generatorCode;
//        return ResponseResult.success("发送验证码");
    }

    /**
    * @Description: 进行验证码校验
    * @Param: [verificationCodeDTO]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/15
    */

    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@Validated @RequestBody CheckVerificationCodeDTO checkVerificationCodeDTO){
        String passengerPhone = checkVerificationCodeDTO.getPassengerPhone();
        String verificationCode = checkVerificationCodeDTO.getVerificationCode();

        ResponseResult checkCode = verificationCodeService.checkCode(passengerPhone, verificationCode);
        return checkCode;
//        return ResponseResult.success("校验验证码");
    }

}
