package com.mashibing.apipassenger.remote;

import com.mashibing.apipassenger.request.OrderRequest;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-order")
public interface ServiceOrderClient {
    /**
    * @Description: 创建订单
    * @Param: [orderRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/22
    */

    @PostMapping("/order/add")
    public ResponseResult add(@Validated @RequestBody OrderRequest orderRequest);


    @RequestMapping(method = RequestMethod.POST, value = "/order/book")
    public ResponseResult book(@RequestBody OrderRequest orderRequest);

    /**
     * 测试
     *
     * @Description:
     * @Param: [orderId]
     * @return: java.lang.String
     * @Author: JiLaiYa
     * @Date: 2024/3/24
     */
    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatcherRealTimeOrder(@PathVariable("orderId") long orderId);


    /**
     * @Description: 订单取消
     * @Param: [orderId, identity]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */
    @PostMapping("/order/cancel")
    public ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity);


    @RequestMapping(method = RequestMethod.GET, value = "/order/detail")
    public ResponseResult<OrderInfo> detail(@RequestParam Long orderId);


    @RequestMapping(method = RequestMethod.GET, value = "/order/current")
    public ResponseResult current(@RequestParam String phone ,@RequestParam String identity);


}
