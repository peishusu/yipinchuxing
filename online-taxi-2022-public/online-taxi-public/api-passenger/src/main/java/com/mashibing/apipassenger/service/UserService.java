package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface UserService {
    /** 
    * @Description: 根据accessToken获取用户信息
    * @Param: [accessToken]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/16
    */
    
    ResponseResult getUserByToken(String accessToken);
}
