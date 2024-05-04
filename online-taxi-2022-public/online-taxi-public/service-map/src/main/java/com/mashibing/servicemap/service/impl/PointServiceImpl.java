package com.mashibing.servicemap.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import com.mashibing.servicemap.remote.PointClient;
import com.mashibing.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 20:29
 **/
@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointClient pointClient;

    @Override
    public ResponseResult upload(PointRequest pointRequest) {
        return pointClient.upload(pointRequest);
    }
}
