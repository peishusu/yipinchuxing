package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-20
 */
@RestController
public class DriverUserWorkStatusController {
    @Autowired
    private DriverUserStatusService driverUserStatusService;

    @PostMapping("/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus){
        return driverUserStatusService.changeWorkStatus(driverUserWorkStatus.getDriverId(),driverUserWorkStatus.getWorkStatus());
    }

    @GetMapping("/work-status")
    public ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId){
        return driverUserStatusService.getWorkStatus(driverId);
    }
}
