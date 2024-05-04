package com.mashibing.apidriver.service.impl;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 19:30
 **/

@Service
public class DriverUserServiceImpl implements DriverUserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Override
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        return serviceDriverUserClient.updateUser(driverUser);
    }

    @Override
    public ResponseResult changeWorkStatus(DriverUserWorkStatus driverUserWorkStatus) {
        return serviceDriverUserClient.changeWorkStatus(driverUserWorkStatus);
    }

    @Override
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(String driverPhone) {
        //根据driverphone查询司机信息
        ResponseResult<DriverCarBindingRelationship> driverCarBindingRelationship = serviceDriverUserClient.getDriverCarBindingRelationship(driverPhone);

        return driverCarBindingRelationship;
    }


    public ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId){
        return serviceDriverUserClient.getWorkStatus(driverId);
    }


}
