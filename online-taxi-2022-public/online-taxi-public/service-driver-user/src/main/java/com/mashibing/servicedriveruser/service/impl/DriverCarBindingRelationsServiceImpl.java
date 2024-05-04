package com.mashibing.servicedriveruser.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationsMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.service.DriverCarBindingRelationshipService;
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
public class DriverCarBindingRelationsServiceImpl implements DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationsMapper driverCarBindingRelationsMapper;

    @Autowired
    private DriverUserMapper driverUserMapper;


    @SentinelResource("getDriverCarBindingRelationships")
    public List<DriverCarBindingRelationship> getDriverCarBindingRelationships(DriverCarBindingRelationship driverCarBindingRelationship) {
        Map<String,Object> map = new HashMap<>();
        map.put("driver_id", driverCarBindingRelationship.getDriverId());
        map.put("car_id", driverCarBindingRelationship.getCarId());
        map.put("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationsMapper.selectByMap(map);
        return driverCarBindingRelationships;
    }


    @Override
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        //再绑定之前，需要进行判断是否绑定关系已经在数据库中存在了
        QueryWrapper<DriverCarBindingRelationship> queryWrapper;
        // 司机被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        Integer integer = driverCarBindingRelationsMapper.selectCount(queryWrapper);
        if ((integer.intValue() > 0)){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_BIND_EXISTS.getMessage());
        }

        // 车辆被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        integer = driverCarBindingRelationsMapper.selectCount(queryWrapper);
        if ((integer.intValue() > 0)){
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(),CommonStatusEnum.CAR_BIND_EXISTS.getMessage());
        }

        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);

        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);

        driverCarBindingRelationsMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("");
    }

    @Override
    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND);
        driverCarBindingRelationship.setUnBindingTime(now);
        driverCarBindingRelationsMapper.updateById(driverCarBindingRelationship);
        return ResponseResult.success("");
    }

    @Override
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(String driverPhone) {
        //根据司机手机号查询出对应的司机id
        QueryWrapper<DriverUser> driverUserQueryWrapper = new QueryWrapper<>();
        driverUserQueryWrapper.eq("driver_phone",driverPhone);
        DriverUser driverUser = driverUserMapper.selectOne(driverUserQueryWrapper);
        Long driverId = driverUser.getId();
        
        //根据司机id查询对应的车辆司机绑定关系
        QueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipQuery = new QueryWrapper<>();
        driverCarBindingRelationshipQuery.eq("driver_id",driverId).eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationsMapper.selectOne(driverCarBindingRelationshipQuery);
        return ResponseResult.success(driverCarBindingRelationship);
    }
}
