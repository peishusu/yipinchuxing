package com.mashibing.servicedriveruser.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("service-map")
public interface ServiceMapClient {
    /**
    * @Description: 创建终端
    * @Param: [name]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.response.TerminalResponse>
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */

    @PostMapping("/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam("name") String name,@RequestParam("desc") String desc);


    /**
    * @Description: 创建轨迹
    * @Param: [tid]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */

    @PostMapping("/track/add")
    public ResponseResult<TrackResponse> addTrack(@RequestParam String tid);
}
