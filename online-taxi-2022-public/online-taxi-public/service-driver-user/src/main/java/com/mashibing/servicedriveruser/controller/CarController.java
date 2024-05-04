package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-20
 */
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    /** 
    * @Description: 添加车辆,同时还要设计车辆的终端id，以及该终端id所对应的轨迹id
    * @Param: [car]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */
    
    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car){
        return carService.addCar(car);
    }

    /**
    * @Description: 根据carId查询car信息
    * @Param: [carId]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.dto.Car>
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */

    @GetMapping("/car")
    public ResponseResult<Car> getCarById(@RequestParam("carId") Long carId){
        return carService.getCarById(carId);
    }
}
