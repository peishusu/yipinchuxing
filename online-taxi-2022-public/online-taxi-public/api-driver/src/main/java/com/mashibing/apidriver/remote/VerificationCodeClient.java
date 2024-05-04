package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-verificationcode")
public interface VerificationCodeClient {

    @GetMapping("/numberCode/{size}")
    public ResponseResult<NumberCodeResponse> numberCode(@PathVariable("size") int size);
}
