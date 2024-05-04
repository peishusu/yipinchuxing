package com.mashibing.apipassenger.service.Impl;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-16 21:06
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;
    @Override
    public ResponseResult getUserByToken(String accessToken) {
        //根据accessToken获取用户的手机信息
        TokenResult tokenResult = JwtUtils.parseToken(accessToken);
        String phone = tokenResult.getPhone();
        //根据手机号去数据库中查询客户信息
        ResponseResult<PassengerUser> user = servicePassengerUserClient.getUser(phone);
        return user;

    }
}
