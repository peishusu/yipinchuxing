package com.mashibing.servicealipay.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface ServiceOrderClient {
    /**
     * @Description: 收款完成
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */

    @PostMapping("/order/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest);
}
