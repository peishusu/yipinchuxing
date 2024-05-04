package com.mashibing.apiboss.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser);

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car);


    @PostMapping("/driver-car-binding-relationship/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

    @PostMapping("/driver-car-binding-relationship/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

}
