package com.mashibing.apidriver.service.impl;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceMapClient;
import com.mashibing.apidriver.service.PointService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import com.mashibing.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 21:51
 **/
@Service
public class PointServiceImpl implements PointService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Override
    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {
        //获取carID
        Long carId = apiDriverPointRequest.getCarId();
        //获取tid,trid
        ResponseResult<Car> carById = serviceDriverUserClient.getCarById(carId);
        String tid = carById.getData().getTid();
        String trid = carById.getData().getTrid();
        //调用地图服务去上传轨迹
        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());
        ResponseResult upload = serviceMapClient.upload(pointRequest);

        return upload;

    }
}
