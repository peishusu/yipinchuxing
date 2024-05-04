package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/terminal/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearch(@RequestParam("center") String center, @RequestParam("radius") Integer radius);

    /**
     * @Description: 查询某个终端的在某个时间段内的所有轨迹
     * @Param: [tid, starttime, endtime]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/27
     */

    @GetMapping("/terminal/trsearch")
    public ResponseResult<TrSearchResponse> trsearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime);


    /**
     * @Description: 根绝trid查询具体某个轨迹，计算得到这个轨迹内行驶的时间和距离
     * @Param: [tid, trid]
     * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.response.TrSearchResponse>
     * @Author: JiLaiYa
     * @Date: 2024/3/27
     */

    @GetMapping("/terminal/trsearchByTrid")
    public ResponseResult<TrSearchResponse> trsearchByTrid(@RequestParam String tid, @RequestParam String trid);

}
