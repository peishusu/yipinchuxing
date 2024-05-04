package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {
    @PostMapping(value = "/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @GetMapping(value = "/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone);
}
