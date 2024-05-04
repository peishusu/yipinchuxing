package com.mashibing.servicedriveruser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Driver;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 10:18
 **/
@RestController
public class UserController {
    @Autowired
    private DriverUserService driverUserService;

    /**
    * @Description: 测试
    * @Param: []
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/19
    */

//    @GetMapping("/test")
//    public ResponseResult testGetDriverUsers(){
//        ResponseResult responseResult = driverUserService.testGetDriverUsers();
//        System.out.println(responseResult);
//        return responseResult;
//    }


    /** 
    * @Description: 插入司机
    * @Param: [driverUser]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser){
        ResponseResult responseResult = driverUserService.addUser(driverUser);
        return responseResult;
    }
    /** 
    * @Description: 更新司机信息
    * @Param: [driverUser]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateUser(driverUser);
    }


    /** 
    * @Description: 根据手机号查询司机
    * @Param: [driverPhone]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult getUserByPhone(@PathVariable("driverPhone") String driverPhone){
        ResponseResult<DriverUser> responseResult = driverUserService.getUserByPhone(driverPhone);
        DriverUser driverUser = responseResult.getData();
        DriverUserExistsResponse driverUserExistsResponse = new DriverUserExistsResponse();
        int ifExists = 1;
        if (driverUser == null){
            ifExists = 0;
            driverUserExistsResponse.setIfExists(ifExists);
            driverUserExistsResponse.setDriverPhone(driverPhone);
        }else{
            driverUserExistsResponse.setIfExists(ifExists);
            driverUserExistsResponse.setDriverPhone(driverUser.getDriverPhone());
        }
        return ResponseResult.success(driverUserExistsResponse);
    }


    /** 
    * @Description: 根据carId查询订单需要的司机信息
    * @Param: [carId]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.response.OrderDriverResponse>
    * @Author: JiLaiYa
    * @Date: 2024/3/24
    */
    
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId){

        return driverUserService.getAvailableDriver(carId);
    }




}
