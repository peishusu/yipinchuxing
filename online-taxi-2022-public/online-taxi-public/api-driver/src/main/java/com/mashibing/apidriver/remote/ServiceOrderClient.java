package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient("service-order")
public interface ServiceOrderClient {


    @PostMapping(value = "/order/grab"  )
    public ResponseResult driverGrab(@RequestBody DriverGrabRequest driverGrabRequest);


    /**
     * @Description: 司机接收到订单后，前往去接乘客
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/27
     */
    @PostMapping("/order/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest);


    /**
     * @Description: 司机到到乘客发起订单的地方
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/27
     */
    @PostMapping("/order/arrived-departure")
    public ResponseResult arriveDeparture(@RequestBody OrderRequest orderRequest);

    /**
     * @Description: 司机接到乘客
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/27
     */

    @PostMapping("/order/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest);


    /**
     * @Description: 乘客下车到达目的地，行程终止
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/27
     */

    @PostMapping("/order/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest);


    /**
     * @Description: 司机发起收款
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */

    @PostMapping("/order/push-pay")
    public ResponseResult pushPay(@RequestBody OrderRequest orderRequest);


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
