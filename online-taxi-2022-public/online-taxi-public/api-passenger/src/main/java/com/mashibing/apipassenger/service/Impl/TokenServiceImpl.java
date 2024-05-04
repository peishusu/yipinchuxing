package com.mashibing.apipassenger.service.Impl;

import com.mashibing.apipassenger.service.TokenService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.ResponseToken;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.mashibing.internalcommon.utils.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.concurrent.TimeUnit;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-16 16:05
 **/
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult refreshToken(String refreshTokenSrc) {
        //解析传送过来的refreshToken，得到其对应的TokenResult
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        String type = tokenResult.getType();

        //生成对应的refreshTokenKey,去redis查询对应的refreshToken
        String refreshTokenKey = RedisPrefixUtils.generateToken(phone, identity, type);
        String refreshTokenFromRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        //校验refreshToken
        if (StringUtils.isBlank(refreshTokenFromRedis) || !refreshTokenSrc.trim().equals(refreshTokenFromRedis.trim())) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());

        }
        //生成双token
        String refreshToken = JwtUtils.generateToken(phone,identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String accessToken = JwtUtils.generateToken(phone,identity,TokenConstants.ACCESS_TOKEN_TYPE);
        //将生成的双token保存到redis中去
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(RedisPrefixUtils.generateToken(phone,identity,TokenConstants.ACCESS_TOKEN_TYPE),accessToken,30,TimeUnit.DAYS);


        ResponseToken tokenResult1 = new ResponseToken(accessToken,refreshToken);

        return ResponseResult.success(tokenResult1);
    }
}
