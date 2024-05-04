package com.mashibing.apiboss.service.impl;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.apiboss.service.CarService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 11:58
 **/
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Override
    public ResponseResult addCar(Car car) {
        return serviceDriverUserClient.addCar(car);
    }
}
