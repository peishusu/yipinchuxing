package com.mashibing.servicedriveruser.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-24 10:20
 **/
@Service
public class CityDriverUserServiceImpl implements CityDriverUserService {
    @Autowired
    private DriverUserMapper driverUserMapper;


    @Override
    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        int i = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        if (i > 0){
            return ResponseResult.success(true);
        }else {
            return ResponseResult.success(false);
        }
    }
}
