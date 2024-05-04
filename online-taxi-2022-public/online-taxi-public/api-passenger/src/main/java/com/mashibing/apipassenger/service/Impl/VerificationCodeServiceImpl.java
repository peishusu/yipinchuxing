package com.mashibing.apipassenger.service.Impl;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.remote.ServiceVerificationcodeClient;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.ResponseToken;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.mashibing.internalcommon.utils.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 09:53
 **/

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    
    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;




    /**
    * @Description: 获取验证码、并且存入到redis中
    * @Param: [passengerPhone]
    * @return: java.lang.String
    * @Author: JiLaiYa
    * @Date: 2024/3/15
    */
    
    @Override
    public ResponseResult generatorCode(String passengerPhone) {
        //嗲用验证码服务
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        //存入redis
        //key,value,ttl
        String key = RedisPrefixUtils.generateKeyByPhone(passengerPhone,IdentityConstants.PASSENGER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key,String.valueOf(numberCode),2, TimeUnit.MINUTES);

        //通过短信服务，将对应的验证码发送到手机上，阿里短信服务、等
        System.out.println("api-apssenger收到的验证码：" + numberCode);

        //返回值
        return ResponseResult.success();
    }


    /**
    * @Description: 校验验证码
    * @Param: [passengerPhone, verificationCode]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/15
    */
    @Override
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        //去redis中根据手机号查询出对应的验证码
        String key = RedisPrefixUtils.generateKeyByPhone(passengerPhone,IdentityConstants.PASSENGER_IDENTITY);
        String checkCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中存放的验证码是:" + checkCode);


        //校验验证码是否相等
        if(StringUtils.isBlank((checkCode)))
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage(),"");
        if(!checkCode.trim().equalsIgnoreCase(verificationCode.trim()))
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage(),"");

        //接下来，就是校验码通过的状态了
        //判断用户是否已经存在,进行相应的处理,进行远程服务之间的调用
        //存在，直接返回token；不存在，注册完再返回token
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        verificationCodeDTO.setVerificationCode(verificationCode);
//        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);
        try {
            servicePassengerUserClient.loginOrRegister(verificationCodeDTO);
        }catch (RuntimeException e){
            return ResponseResult.fail(CommonStatusEnum.CALL_USER_ADD_ERROR.getCode(),CommonStatusEnum.CALL_USER_ADD_ERROR.getMessage());
        }


        //颁发token
        String accessToken = JwtUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);

        // 开启redis事务 支持
        stringRedisTemplate.setEnableTransactionSupport(true);
        SessionCallback<Boolean> callback = new SessionCallback<Boolean>() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                // 事务开始
                stringRedisTemplate.multi();
                try {

                    // 将token存到redis当中
                    String accessTokenKey = RedisPrefixUtils.generateToken(passengerPhone , IdentityConstants.PASSENGER_IDENTITY , TokenConstants.ACCESS_TOKEN_TYPE);
                    operations.opsForValue().set(accessTokenKey , accessToken , 30, TimeUnit.DAYS);
//                    int i = 1/0;
                    String refreshTokenKey = RedisPrefixUtils.generateToken(passengerPhone , IdentityConstants.PASSENGER_IDENTITY , TokenConstants.REFRESH_TOKEN_TYPE);
                    operations.opsForValue().set(refreshTokenKey , refreshToken , 31, TimeUnit.DAYS);
                    operations.exec();
                    return true;
                }catch (Exception e){
                    operations.discard();
                    return false;
                }

            }
        };
        Boolean execute = stringRedisTemplate.execute(callback);
        System.out.println("事务提交or回滚："+execute);
        if (execute){
            // 响应
            ResponseToken tokenResponse = new ResponseToken();
            tokenResponse.setAccessToken(accessToken);
            tokenResponse.setRefreshToken(refreshToken);
            return ResponseResult.success(tokenResponse);
        }else {
            return ResponseResult.fail(CommonStatusEnum.CHECK_CODE_ERROR.getCode(),CommonStatusEnum.CHECK_CODE_ERROR.getMessage());
        }

        //将accessToken存放到redis中
//        String accessTokenKey = RedisPrefixUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY,TokenConstants.ACCESS_TOKEN_TYPE);
//        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
//        String refreshTokenKey = RedisPrefixUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
//        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);
//
//        //返回结果
//        ResponseToken token = new ResponseToken(accessToken,refreshToken);
//        return ResponseResult.success(token);
    }



}
