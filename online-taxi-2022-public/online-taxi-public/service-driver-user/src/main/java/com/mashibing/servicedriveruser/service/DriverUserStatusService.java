package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DriverUserStatusService {
    ResponseResult changeWorkStatus(Long driverId, Integer workStatus);

    ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId);
}
