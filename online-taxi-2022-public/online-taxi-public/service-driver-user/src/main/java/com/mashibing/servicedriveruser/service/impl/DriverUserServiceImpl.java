package com.mashibing.servicedriveruser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.*;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationsMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 10:19
 **/
@Service
public class DriverUserServiceImpl implements DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    private DriverCarBindingRelationsMapper driverCarBindingRelationsMapper;

    @Autowired
    private CarMapper carMapper;

    /**
    * @Description: 测试方法
    * @Param: []
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */

    @Override
    public ResponseResult testGetDriverUsers() {
        DriverUser driverUser = driverUserMapper.selectById(1);
        System.out.println(driverUser);
        return ResponseResult.success(driverUser);

    }

    @Override
    public ResponseResult addUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        int insert = driverUserMapper.insert(driverUser);
        if (insert == 1) {
            //接着，初始化司机状态信息表
            DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
            driverUserWorkStatus.setDriverId(driverUser.getId());
            driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
            driverUserWorkStatus.setGmtCreate(now);
            driverUserWorkStatus.setGmtModified(now);
            driverUserWorkStatusMapper.insert(driverUserWorkStatus);
            return ResponseResult.success("插入成功");

        } else {
            return ResponseResult.fail("插入失败");
        }
    }

    @Override
    public ResponseResult updateUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        int res = driverUserMapper.updateById(driverUser);
        if (res == 1) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.fail("更新失败");
        }

    }

    /**
    * @Description: 根据司机手机号查询司机
    * @Param: [driverPhone]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.dto.DriverUser>
    * @Author: JiLaiYa
    * @Date: 2024/3/20
    */
    @Override
    public ResponseResult<DriverUser> getUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers.isEmpty()) {
           return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXIST.getCode(),CommonStatusEnum.DRIVER_NOT_EXIST.getMessage(),null);
        }
        return ResponseResult.success(driverUsers.get(0));
    }


    @Override
    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId) {
        //先根据carId找到对应的司机
        QueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipqueryWrapper = new QueryWrapper<>();
        driverCarBindingRelationshipqueryWrapper.eq("car_id",carId).eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationsMapper.selectList(driverCarBindingRelationshipqueryWrapper);
        if (driverCarBindingRelationships.size() ==  0){
            return ResponseResult.fail(CommonStatusEnum.CAR_NOT_OWN_DRIVER.getCode(),CommonStatusEnum.CAR_NOT_OWN_DRIVER.getMessage());
        }
        //查询出与carId绑定的对应的driverId
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationships.get(0);
        Long driverId = driverCarBindingRelationship.getDriverId();
        //要判断当前司机的状态是否满足出行的条件
        QueryWrapper<DriverUserWorkStatus> driverUserWorkStatusqueryWrapper = new QueryWrapper<>();
        driverUserWorkStatusqueryWrapper.eq("driver_id",driverId).eq("work_status",DriverCarConstants.DRIVER_WORK_STATUS_START);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectList(driverUserWorkStatusqueryWrapper);
        if (driverUserWorkStatuses.size() ==  0){
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(),CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getMessage());
        }
        //到这里代表司机满足出行的状态,查询司机对应的电话号码
        QueryWrapper<DriverUser> driverUserqueryWrapper = new QueryWrapper<>();
        driverUserqueryWrapper.eq("id",driverId);//根据id查询出来的司机一定是唯一的 or 不存在的
        List<DriverUser> driverUsers = driverUserMapper.selectList(driverUserqueryWrapper);
        DriverUser driverUser = driverUsers.get(0);
        //查询车辆信息
        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
        carQueryWrapper.eq("id",carId);
        Car car = carMapper.selectOne(carQueryWrapper);
        

        //设置返回状态
        OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
        orderDriverResponse.setDriverId(driverId);
        orderDriverResponse.setCarId(carId);
        orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());
        orderDriverResponse.setLicenseId(driverUser.getLicenseId());
        orderDriverResponse.setVehicleNo(car.getVehicleNo());
        orderDriverResponse.setVehicleType(car.getVehicleType());

        return ResponseResult.success(orderDriverResponse);
    }
}
