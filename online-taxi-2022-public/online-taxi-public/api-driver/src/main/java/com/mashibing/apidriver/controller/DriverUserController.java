package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.DriverUserService;
import com.mashibing.internalcommon.dto.*;
import com.mashibing.internalcommon.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 19:27
 **/
@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
    * @Description: 更新司机信息
    * @Param: [driverUser]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */

    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateDriverUser(driverUser);
    }


    /**
    * @Description: 改变司机状态（出车、暂停）
    * @Param: [driverUserWorkStatus]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/29
    */

    @PostMapping("/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus){
        return driverUserService.changeWorkStatus(driverUserWorkStatus);
    }

    /** 
    * @Description: 根据司机的手机号(根据token拆卸你出对应的手机号)查询司机、车辆的绑定对应关系
    * @Param: [request]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/29
    */
    
    @GetMapping("/driver-car-binding-relationship")
    public ResponseResult<DriverCarBindingRelationship> driverCarBindingRelationship(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.checkToken(authorization);
        String driverPhone = tokenResult.getPhone();
        return driverUserService.getDriverCarBindingRelationship(driverPhone);
    }


    @GetMapping("/work-status")
    public ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId){
        return driverUserService.getWorkStatus(driverId);
    }

}
