package com.mashibing.servicedriveruser.controller;

import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-24 09:50
 **/
@RestController
public class testController {
    @Autowired
    private DriverUserMapper driverUserMapper;


    @GetMapping("/test-xml")
    public String testXml(String args){
        return String.valueOf(driverUserMapper.selectDriverUserCountByCityCode(args));
    }

    @GetMapping("/test")
    public String test(String args){
        return "service-driver-user";
    }
}
