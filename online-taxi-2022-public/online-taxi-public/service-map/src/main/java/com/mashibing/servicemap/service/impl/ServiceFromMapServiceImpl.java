package com.mashibing.servicemap.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.ServiceClient;
import com.mashibing.servicemap.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 16:28
 **/
@Service
public class ServiceFromMapServiceImpl implements ServiceFromMapService {
    @Autowired
    private ServiceClient serviceClient;


    @Override
    public ResponseResult add(String name) {
        return serviceClient.add(name);
    }
}
