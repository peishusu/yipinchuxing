package com.mashibing.apiboss.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;

import javax.xml.ws.Response;

public interface DriverUserService {
    ResponseResult addDriverUser(DriverUser driverUser);

    ResponseResult updateDriverUser(DriverUser driverUser);
}
