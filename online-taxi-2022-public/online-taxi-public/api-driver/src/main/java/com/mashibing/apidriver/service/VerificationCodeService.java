package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface VerificationCodeService {
    ResponseResult checkAndSendVerificationCode(String driverPhone);


    ResponseResult checkCode(String driverPhone, String verificationCode);
}
