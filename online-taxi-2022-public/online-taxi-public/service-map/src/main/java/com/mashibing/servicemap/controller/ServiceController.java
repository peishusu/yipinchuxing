package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 16:27
 **/
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceFromMapService serviceFromMapService;


    /**
    * @Description: 项目初始化的时候，创建服务
    * @Param: [name]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */

    @PostMapping("/add")
    public ResponseResult add(@RequestParam("name") String name){

        return serviceFromMapService.add(name);
    }
}
