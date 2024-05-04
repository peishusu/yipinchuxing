package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import com.mashibing.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 20:24
 **/
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    /**
    * @Description: 轨迹上传
    * @Param: [pointRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */
    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest){
        return pointService.upload(pointRequest);
    }
}
