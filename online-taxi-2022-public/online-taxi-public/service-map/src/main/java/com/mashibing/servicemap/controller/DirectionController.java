package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 11:15
 **/
@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    /** 
    * @Description: 根据起点、终点的经纬度计算获得：距离、时间
    * @Param: [forecastPriceDTO]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/23
    */
    
    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();

        return directionService.driving(depLongitude,depLatitude,destLongitude,destLatitude);
    }
}
