package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface VerificationCodeService {
    public ResponseResult generatorCode(String passengerPhone);

    ResponseResult checkCode(String passengerPhone,String verificationCode);
}
