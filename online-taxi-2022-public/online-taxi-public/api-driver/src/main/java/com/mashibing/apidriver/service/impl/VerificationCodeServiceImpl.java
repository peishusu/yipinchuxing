package com.mashibing.apidriver.service.impl;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.VerificationCodeClient;
import com.mashibing.apidriver.service.VerificationCodeService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.ResponseToken;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.mashibing.internalcommon.utils.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 14:53
 **/

@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private VerificationCodeClient verificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        log.info(driverPhone);
        //检查司机是否存在
        ResponseResult<DriverUserExistsResponse> userByPhone = serviceDriverUserClient.getUserByPhone(driverPhone);
        DriverUserExistsResponse data = userByPhone.getData();
        if (data.getIfExists() == DriverCarConstants.DRIVER_NOT_EXISTS){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXIST.getCode(),CommonStatusEnum.DRIVER_NOT_EXIST.getMessage());

        }
        log.info(driverPhone + "司机存在");
        //生成验证码
        ResponseResult<NumberCodeResponse> numberCodeResponseResponseResult = verificationCodeClient.numberCode(6);
        int numberCode = numberCodeResponseResponseResult.getData().getNumberCode();
        log.info("验证码："+numberCode);


        //利用第三方工具发送验证码到客户端(最后完善的时候在实现)


        //将验证码存入到redis中
        String driverVerificationCodeKey = RedisPrefixUtils.generateKeyByPhone(driverPhone, IdentityConstants.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(driverVerificationCodeKey,String.valueOf(numberCode),2, TimeUnit.MINUTES);



        return ResponseResult.success("");
    }

    @Override
    public ResponseResult checkCode(String driverPhone, String verificationCode) {
        //去redis中根据手机号查询出对应的验证码
        String key = RedisPrefixUtils.generateKeyByPhone(driverPhone,IdentityConstants.DRIVER_IDENTITY);
        String checkCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中存放的验证码是:" + checkCode);


        //校验验证码是否相等
        if(StringUtils.isBlank((checkCode)))
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage(),"");
        if(!checkCode.trim().equalsIgnoreCase(verificationCode.trim()))
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage(),"");



        //颁发token
        String accessToken = JwtUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
        //将accessToken存放到redis中
        String accessTokenKey = RedisPrefixUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY,TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        String refreshTokenKey = RedisPrefixUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);

        //返回结果
        ResponseToken token = new ResponseToken(accessToken,refreshToken);
        return ResponseResult.success(token);
    }
}
