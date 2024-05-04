package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;
import com.mashibing.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 17:15
 **/
@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    /**
     * @Description:创建终端
     * @Param: [name 终端名称, desc 里面是车辆的id]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/22
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestParam("name") String name, @RequestParam("desc") String desc) {

        return terminalService.add(name, desc);
    }


    /**
     * @Description: 周边搜索轨迹
     * @Param: [center 经纬度, radius 单位是m]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/22
     */

    @PostMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearch(@RequestParam("center") String center, @RequestParam("radius") Integer radius) {
        return terminalService.aroundSearch(center, radius);
    }

    /** 
    * @Description: 查询某个终端的在某个时间段内的所有轨迹
    * @Param: [tid, starttime, endtime]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/27
    */
    
    @GetMapping("/trsearch")
    public ResponseResult<TrSearchResponse> trsearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime) {
        
        return terminalService.trsearch(tid,starttime,endtime);
    }


    /**
    * @Description: 根绝trid查询具体某个轨迹，计算得到这个轨迹内行驶的时间和距离
    * @Param: [tid, trid]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.response.TrSearchResponse>
    * @Author: JiLaiYa
    * @Date: 2024/3/27
    */

    @GetMapping("/trsearchByTrid")
    public ResponseResult<TrSearchResponse> trsearchByTrid(@RequestParam String tid, @RequestParam String trid) {
        return terminalService.trsearchByTrid(tid,trid);
    }


}
