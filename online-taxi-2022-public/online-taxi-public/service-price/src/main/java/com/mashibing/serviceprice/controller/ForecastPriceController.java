package com.mashibing.serviceprice.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.serviceprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 10:27
 **/
@RestController
public class ForecastPriceController {

    @Autowired
    private PriceService forecastPriceService;

    /**
    * @Description: 计算预计价格
    * @Param: [forecastPriceDTO]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/22
    */
    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        String cityCode = forecastPriceDTO.getCityCode();
        String vehicleType = forecastPriceDTO.getVehicleType();
        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude,cityCode,vehicleType);
    }


    /**
    * @Description: 计算实际的价格
    * @Param: [distance, duration, cityCode, vehicleType]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */

    @PostMapping("/calculate-price")
    public ResponseResult calculatePrice(@RequestParam Integer distance,@RequestParam Integer duration,@RequestParam String cityCode,@RequestParam String vehicleType){
        return forecastPriceService.calculatePrice(distance,duration,cityCode,vehicleType);
    }
}
