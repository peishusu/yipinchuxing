package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.*;

public interface DriverUserService {
    public ResponseResult updateDriverUser(DriverUser driverUser);

    ResponseResult changeWorkStatus(DriverUserWorkStatus driverUserWorkStatus);

    ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(String driverPhone);


    ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId);
}
