package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.PointService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 21:45
 **/
@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private PointService pointService;

    /** 
    * @Description: 上传车辆经纬度信息
    * @Param: [apiDriverPointRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */
    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest){

        return pointService.upload(apiDriverPointRequest);
    }

}
