package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface ForecastPriceService {
    ResponseResult forecastPrice(String depLongitude,String depLatitude,String destLongitude,String destLatitude,String cityCode,String vehicleType);
}
