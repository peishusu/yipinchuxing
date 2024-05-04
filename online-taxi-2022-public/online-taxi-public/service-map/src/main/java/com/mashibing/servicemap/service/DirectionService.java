package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface DirectionService {
    ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude);
}
