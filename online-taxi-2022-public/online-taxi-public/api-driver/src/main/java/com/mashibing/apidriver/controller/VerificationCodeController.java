package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 14:50
 **/
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;


    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return verificationCodeService.checkAndSendVerificationCode(verificationCodeDTO.getDriverPhone());
    }

    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();

        ResponseResult checkCode = verificationCodeService.checkCode(driverPhone, verificationCode);
        return checkCode;
    }
}
