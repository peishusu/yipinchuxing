package com.mashibing.apiboss.service.impl;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.apiboss.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 14:51
 **/
@Service
public class DriverUserServiceImpl implements DriverUserService {


    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Override
    public ResponseResult addDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.addUser(driverUser);
    }

    @Override
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.updateUser(driverUser);
    }
}
