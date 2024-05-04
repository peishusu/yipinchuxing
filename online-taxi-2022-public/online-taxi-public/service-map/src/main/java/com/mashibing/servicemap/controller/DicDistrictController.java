package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;

import com.mashibing.servicemap.remote.MapDicDistrictClient;
import com.mashibing.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: online-taxi-public
 * @description: 地区字典
 * @author: lydms
 * @create: 2024-03-18 11:59
 **/
@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-district")
    public ResponseResult initDicDistrict(@RequestParam(value = "keywords",required = false, defaultValue = "中国") String keyWord){

        ResponseResult responseResult = dicDistrictService.initDicDistrict(keyWord);


        return responseResult;
    }

}
