package com.mashibing.servicemap.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.servicemap.remote.MapDirectionClient;
import com.mashibing.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 11:19
 **/
@Service
public class DirectionServiceImpl implements DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    @Override
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //根据上面四个参数计算得到距离、时间
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);

        //返回结果
        return ResponseResult.success(direction);
    }
}
