package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-20
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationsController {

    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    /**
    * @Description: 司机和车辆绑定
    * @Param: [driverCarBindingRelationship]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/29
    */

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){

        List<DriverCarBindingRelationship> driverCarBindingRelationships =
                driverCarBindingRelationshipService.getDriverCarBindingRelationships(driverCarBindingRelationship);
        if (driverCarBindingRelationships!=null && driverCarBindingRelationships.size() > 0){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getMessage());
        }
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    /**
    * @Description: 司机和车辆解绑
    * @Param: [driverCarBindingRelationship]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/29
    */

    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        List<DriverCarBindingRelationship> driverCarBindingRelationships =
                driverCarBindingRelationshipService.getDriverCarBindingRelationships(driverCarBindingRelationship);
        if (driverCarBindingRelationships.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());
        }

        DriverCarBindingRelationship relationship = driverCarBindingRelationships.get(0);
        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
    }


    /**
    * @Description: 根据driverPhone查询司机和车辆的绑定关系
    * @Param: [driverPhone]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.dto.DriverCarBindingRelationship>
    * @Author: JiLaiYa
    * @Date: 2024/3/29
    */

    @GetMapping("/searchByDriverPhone")
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(@RequestParam String driverPhone){
        return driverCarBindingRelationshipService.getDriverCarBindingRelationship(driverPhone);
    }



}
