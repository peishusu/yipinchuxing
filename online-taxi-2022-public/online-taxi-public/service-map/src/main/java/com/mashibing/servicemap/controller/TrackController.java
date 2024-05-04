package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 19:20
 **/
@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;


    /** 
    * @Description: 创建轨迹
    * @Param: [tid]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */
    @PostMapping("/add")
    public ResponseResult add(@RequestParam("tid") String tid){
        return trackService.add(tid);
    }



    


}
