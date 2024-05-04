package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-verificationcode",fallbackFactory = ServiceVerificationCodeFallbackFactory.class)
public interface ServiceVerificationcodeClient {

    @GetMapping(value = "/numberCode/{size}")
    public ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);
}
