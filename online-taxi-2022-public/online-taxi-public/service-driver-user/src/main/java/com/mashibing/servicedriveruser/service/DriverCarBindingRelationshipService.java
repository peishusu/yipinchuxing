package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;

import java.util.List;

public interface DriverCarBindingRelationshipService {
    ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship);

    ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship);

    ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(String driverPhone);

    List<DriverCarBindingRelationship> getDriverCarBindingRelationships(DriverCarBindingRelationship driverCarBindingRelationship);
}
