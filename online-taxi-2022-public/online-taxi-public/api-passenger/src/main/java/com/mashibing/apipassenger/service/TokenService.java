package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface TokenService {
    public ResponseResult refreshToken(String refreshTokenSrc);
}
