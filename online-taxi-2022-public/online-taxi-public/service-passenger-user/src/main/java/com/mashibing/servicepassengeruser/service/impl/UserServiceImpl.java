package com.mashibing.servicepassengeruser.service.impl;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-15 11:04
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PassengerUserMapper passengerUserMapper;
    @Override
    public ResponseResult loginOrRegister(String passengerPhone) {
        //根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

        //判断用户信息是否存在
        //如果不存在，插入用户==注册
        if(passengerUsers.size() == 0){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("飞滴出行默认用户");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            passengerUserMapper.insert(passengerUser);
        }

        return ResponseResult.success();
    }

    @Override
    public ResponseResult getUserByPhone(String passengerPhone) {
        //根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if(passengerUsers.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.USER_ERROR.getCode(),CommonStatusEnum.USER_ERROR.getMessage());
        }
        PassengerUser passengerUser = passengerUsers.get(0);
        return ResponseResult.success(passengerUser);
    }
}
