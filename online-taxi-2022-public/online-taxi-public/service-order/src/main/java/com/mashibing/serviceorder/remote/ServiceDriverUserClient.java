package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {


    @GetMapping("/city-driver/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam("cityCode") String cityCode);


    /**
     * @Description: 根据carId查询订单需要的司机信息
     * @Param: [carId]
     * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.response.OrderDriverResponse>
     * @Author: JiLaiYa
     * @Date: 2024/3/24
     */

    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId);


    /**
     * @Description: 根据carId查询car信息
     * @Param: [carId]
     * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.dto.Car>
     * @Author: JiLaiYa
     * @Date: 2024/3/21
     */
    @GetMapping("/car")
    public ResponseResult<Car> getCarById(@RequestParam("carId") Long carId);
}
