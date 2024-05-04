package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Driver;

public interface DriverUserService {
    ResponseResult testGetDriverUsers();

    ResponseResult addUser(DriverUser driverUser);

    ResponseResult updateUser(DriverUser driverUser);

    ResponseResult<DriverUser> getUserByPhone(String driverPhone);


    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId);


}
