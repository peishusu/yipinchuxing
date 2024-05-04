package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PriceRuleIsNewRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-price")
public interface ServicePriceClient {
    @PostMapping("/price-rule/is-new")
    public ResponseResult<Boolean> IsNewestVersion(@RequestBody PriceRuleIsNewRequest priceRuleIsNewRequest);


    @GetMapping("/if-exists/{cityCode}/{vehicleType}")
    public ResponseResult<Boolean> ifExists(@PathVariable("cityCode") String cityCode, @PathVariable("vehicleType") String vehicleType);


    /**
     * @Description: 计算实际的价格
     * @Param: [distance, duration, cityCode, vehicleType]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */
    @PostMapping("/calculate-price")
    public ResponseResult calculatePrice(@RequestParam Integer distance,@RequestParam Integer duration,@RequestParam String cityCode,@RequestParam String vehicleType);
}
