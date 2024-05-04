package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface PriceService {
    ResponseResult forecastPrice(String depLongitude,String depLatitude,String destLongitude,String destLatitude,String cityCode,String vehicleType);

    ResponseResult calculatePrice(Integer distance, Integer duration, String cityCode, String vehicleType);
}
