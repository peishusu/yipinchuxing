package com.mashibing.apiboss.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;

public interface DriverCarRelationshipService {
    ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship);

    ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship);
}
