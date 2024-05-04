package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 14:50
 **/
@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    /** 
    * @Description: 添加司机信息
    * @Param: [driverUser]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */
    
    @PostMapping("/driver-user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.addDriverUser(driverUser);
    }

    /** 
    * @Description: 更新司机信息
    * @Param: [driverUser]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */
    
    @PutMapping("/driver-user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateDriverUser(driverUser);
    }


}
