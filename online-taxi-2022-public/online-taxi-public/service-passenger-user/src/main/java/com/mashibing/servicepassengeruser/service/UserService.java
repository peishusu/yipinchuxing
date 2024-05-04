package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface UserService {
    ResponseResult loginOrRegister(String passengerPhone);
    
    /** 
    * @Description: 根据电话号码获取用户信息
    * @Param: [passengerPhone]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/16
    */
    
    ResponseResult getUserByPhone(String passengerPhone);
}
