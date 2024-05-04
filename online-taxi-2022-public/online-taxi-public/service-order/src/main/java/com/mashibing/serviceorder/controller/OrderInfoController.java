package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.constant.HeaderParamConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.GrabService;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-22 12:01
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    @Qualifier("grabByRedissonBasicService")
//    @Qualifier("grabByMultiRedisService")
//    @Qualifier("grabByRedissonBasicYamlService")
//    @Qualifier("grabByRedissonMasterSlaveYamlService")
//    @Qualifier("grabByRedissonSentinelYamlService")
//    @Qualifier("grabByRedissonRedLockService")
//    @Qualifier("grabByZookeeperDiyService")
//    @Qualifier("grabByZookeeperCuratorService")
    private GrabService grabService;

    /**
     * @Description: 创建订单
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/22
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
        //当我们实现网关的时候，在启用这个
//        String deviceCode = httpServletRequest.getHeader(HeaderParamConstants.DEVICE_CODE);
//        orderRequest.setDeviceCode(deviceCode);

//        log.info("" + orderRequest.getAddress());
        return orderInfoService.add(orderRequest);
    }


    /**
     * 创建预约单
     * @param orderRequest
     * @return
     */
    @PostMapping("/book")
    public ResponseResult book(@RequestBody OrderRequest orderRequest , HttpServletRequest httpServletRequest){

        log.info("service-order"+orderRequest.getAddress());
        return orderInfoService.book(orderRequest);
    }

    /** 
    * @Description: 司机接收到订单后，前往去接乘客
    * @Param: [orderRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/27
    */
    
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.toPickUpPassenger(orderRequest);
    }


    /**
    * @Description: 司机到到乘客发起订单的地方
    * @Param: [orderRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/27
    */
    @PostMapping("/arrived-departure")
    public ResponseResult arriveDeparture(@RequestBody OrderRequest orderRequest){
        return orderInfoService.arriveDeparture(orderRequest);
    }

    /**
    * @Description: 司机接到乘客
    * @Param: [orderRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/27
    */

    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pickUpPassenger(orderRequest);
    }

    /** 
    * @Description: 乘客下车到达目的地，行程终止
    * @Param: [orderRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/27
    */
    
    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.passengerGetoff(orderRequest);
    }

    /**
    * @Description: 司机发起收款
    * @Param: [orderRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */

    @PostMapping("/push-pay")
    public ResponseResult pushPay(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pushPay(orderRequest);
    }

    /**
    * @Description: 收款完成
    * @Param: [orderRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */

    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest){
        return orderInfoService.pay(orderRequest);
    }


    /** 
    * @Description: 订单取消
    * @Param: [orderId, identity]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */
    
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId,@RequestParam String identity){


        return orderInfoService.cancel(orderId,identity);
    }


    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResponseResult<OrderInfo> detail(Long orderId){
        return orderInfoService.detail(orderId);
    }


    @GetMapping("/current")
    public ResponseResult current(String phone , String identity){
        return orderInfoService.current(phone , identity);
    }

    /**
     * 司机抢预约订单
     * @param driverGrabRequest
     * @return
     */
    @PostMapping("/grab")
    public ResponseResult driverGrab(@RequestBody DriverGrabRequest driverGrabRequest){

        return grabService.grab(driverGrabRequest);

    }
}
