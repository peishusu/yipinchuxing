package com.mashibing.servicedriveruser.service.impl;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import com.mashibing.servicedriveruser.remote.ServiceMapClient;
import com.mashibing.servicedriveruser.service.CarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-20
 */
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Override
    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);

        //为车辆创建其对应的终端，获得此车辆对应的终端id
        ResponseResult<TerminalResponse> add = serviceMapClient.addTerminal(car.getVehicleNo(), String.valueOf(car.getId()));
        String tid = add.getData().getTid();
        car.setTid(tid);
        //获得此车辆的轨迹id
        ResponseResult<TrackResponse> responseResult = serviceMapClient.addTrack(tid);
        String trid = responseResult.getData().getTrid();
        String trname = responseResult.getData().getTrname();
        car.setTrid(trid);
        car.setTrname(trname);
        //更新car的信息
        carMapper.updateById(car);

        return ResponseResult.success("");
    }

    @Override
    public ResponseResult<Car> getCarById(Long carId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", carId);
        List<Car> cars = carMapper.selectByMap(map);
        Car car = cars.get(0);
        return ResponseResult.success(car);
    }
}
