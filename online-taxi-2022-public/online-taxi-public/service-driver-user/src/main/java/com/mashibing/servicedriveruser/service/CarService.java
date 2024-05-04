package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface CarService {
    public ResponseResult addCar(Car car);

    ResponseResult<Car> getCarById(Long carId);
}
