package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface CityDriverUserService {

    public ResponseResult<Boolean> isAvailableDriver(String cityCode);
}
