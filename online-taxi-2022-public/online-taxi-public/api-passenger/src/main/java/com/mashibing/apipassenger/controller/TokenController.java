package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.TokenService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.ResponseToken;
import com.mashibing.internalcommon.dto.TokenResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-16 16:01
 **/
@RestController
public class TokenController {
    
    @Autowired
    private TokenService tokenService;
    
    /** 
    * @Description: 刷新token
    * @Param: [responseToken]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/16
    */
    
    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody ResponseToken responseToken){
        String refreshToken = responseToken.getRefreshToken();
        ResponseResult responseResult = tokenService.refreshToken(refreshToken);
        return responseResult;
    }
}
