package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: online-taxi-public
 * @description: 
 * @author: lydms
 * @create: 2024-03-24 10:26
 **/
@RestController
@RequestMapping("/city-driver")
public class CityDriverUserController {

    @Autowired
    private CityDriverUserService cityDriverUserService;


    /** 
    * @Description: 查看当前城市是否有可用网约车司机
    * @Param: [cityCode]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<java.lang.Boolean>
    * @Author: JiLaiYa
    * @Date: 2024/3/24
    */
    
    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam("cityCode") String cityCode) {
        return cityDriverUserService.isAvailableDriver(cityCode);
    }
}
