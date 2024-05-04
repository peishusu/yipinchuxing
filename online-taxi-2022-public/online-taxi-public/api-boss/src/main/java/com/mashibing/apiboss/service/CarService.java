package com.mashibing.apiboss.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface CarService {
    ResponseResult addCar(Car car);
}
