package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.ForecastPriceDTO;
import com.mashibing.apipassenger.service.ForecastPriceService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 09:40
 **/
@RestController
public class ForecastPriceController {

    @Autowired
    private ForecastPriceService forecastPriceService;

    /**
    * @Description: 预估价格
    * @Param: [forecastPriceDTO]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/17
    */
    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@Validated @RequestBody ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        String cityCode = forecastPriceDTO.getCityCode();
        String vehicleType = forecastPriceDTO.getVehicleType();

        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude,cityCode,vehicleType);
    }
}
