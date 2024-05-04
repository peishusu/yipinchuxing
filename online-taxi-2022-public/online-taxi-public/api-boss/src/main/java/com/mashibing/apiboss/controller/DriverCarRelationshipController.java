package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.DriverCarRelationshipService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 12:20
 **/
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarRelationshipController {

    @Autowired
    private DriverCarRelationshipService driverCarRelationshipService;

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverCarRelationshipService.bind(driverCarBindingRelationship);
    }

    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverCarRelationshipService.unbind(driverCarBindingRelationship);
    }
}
