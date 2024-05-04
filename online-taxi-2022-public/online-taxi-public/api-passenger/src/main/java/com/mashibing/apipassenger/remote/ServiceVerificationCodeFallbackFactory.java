package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @program: online-taxi-public
 * @description: 当远程调用service-verification服务未返回响应时，返回一个默认的验证码
 * @author: lydms
 * @create: 2024-04-22 12:56
 **/

@Component
public class ServiceVerificationCodeFallbackFactory implements FallbackFactory<ServiceVerificationcodeClient> {
    @Override
    public ServiceVerificationcodeClient create(Throwable cause) {
        return new ServiceVerificationcodeClient() {
            @Override
            public ResponseResult<NumberCodeResponse> getNumberCode(int size) {
                NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
                numberCodeResponse.setNumberCode(111111);
                return ResponseResult.success(numberCodeResponse);
            }
        };
    }
}
