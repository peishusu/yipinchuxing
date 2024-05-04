package com.mashibing.apiboss.service.impl;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.apiboss.service.DriverCarRelationshipService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 12:21
 **/
@Service
public class DriverCarRelationshipServiceImpl implements DriverCarRelationshipService {


    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;


    @Override
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        return serviceDriverUserClient.bind(driverCarBindingRelationship);
    }

    @Override
    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        return serviceDriverUserClient.unbind(driverCarBindingRelationship);
    }
}
