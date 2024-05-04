package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-map")
public interface ServiceMapClient {
    /** 
    * @Description: 上传轨迹
    * @Param: [pointRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */
    @PostMapping("/point/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest);
}
