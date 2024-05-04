package com.mashibing.servicedriveruser.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashibing.servicedriveruser.service.DriverUserService;
import com.mashibing.servicedriveruser.service.DriverUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-20
 */
@Service
public class DriverUserWorkStatusServiceImpl  implements DriverUserStatusService {
    
    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Override
    @Transactional
    public ResponseResult changeWorkStatus(Long driverId, Integer workStatus) {
        //从数据库中查询出司机
        Map<String,Object> map = new HashMap<>();
        map.put("driver_id",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(map);
        //因为当司机登录的时候，就已经提前设置好该司机对应的工作状态了，所以一定是能查询到对应的司机
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);
        //修改该司机的工作状态
        driverUserWorkStatus.setWorkStatus(workStatus);
        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
        //返回结果
        return ResponseResult.success("");
    }


    @Override
    public ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId) {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driver_id",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);
        return ResponseResult.success(driverUserWorkStatus);
    }



}
