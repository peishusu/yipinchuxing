package com.mashibing.apipassenger.service.Impl;

import com.mashibing.apipassenger.remote.ServicePriceClient;
import com.mashibing.apipassenger.service.ForecastPriceService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 09:46
 **/
@Service
@Slf4j
public class ForecastPriceServiceImpl implements ForecastPriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;


    @Override
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,String cityCode,String vehicleType) {
        log.info("出发第纬度:"+depLongitude);
        log.info("出发第经度:"+depLatitude);
        log.info("目的第纬度:"+destLongitude);
        log.info("目的第经度:"+destLatitude);

        log.info("调用service-price计价服务");
        ResponseResult responseResult = servicePriceClient.forecastPrice(new ForecastPriceDTO(depLongitude, depLatitude, destLongitude, destLatitude,cityCode,vehicleType));
        return responseResult;

    }
}
