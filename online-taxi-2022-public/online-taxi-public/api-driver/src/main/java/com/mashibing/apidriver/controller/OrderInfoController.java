package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.OrderInfoService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.utils.JwtUtils;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: online-taxi-public
 * @description: 订单模块
 * @author: lydms
 * @create: 2024-03-28 09:27
 **/
@RestController
@RequestMapping("/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;


    /**
     * 司机抢单
     * @param order
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/grab")
    @GlobalTransactional
    public ResponseResult grab(@RequestBody OrderRequest order, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        // 从token中获取司机信息
        TokenResult tokenResult = JwtUtils.parseToken(token);
        String identity = tokenResult.getIdentity();
        String driverPhone = tokenResult.getPhone();
        Long orderId = order.getOrderId();
        String receiveOrderCarLongitude = order.getReceiveOrderCarLongitude();
        String receiveOrderCarLatitude = order.getReceiveOrderCarLatitude();

//        return ResponseResult.success();
        return orderInfoService.grap(driverPhone, orderId, receiveOrderCarLongitude, receiveOrderCarLatitude);
    }



    /**
     * @Description: 去接乘客
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */

    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.toPickUpPassenger(orderRequest);
    }

    /**
     * @Description: 司机到达乘客上车点
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */

    @PostMapping("/arrived-departure")
    public ResponseResult arriveDeparture(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.arriveDeparture(orderRequest);

    }


    /**
     * @Description: 司机接到乘客
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */

    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pickUpPassenger(orderRequest);
    }


    /**
     * @Description: 乘客下车
     * @Param: [orderRequest]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/28
     */

    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.passengerGetoff(orderRequest);

    }


    /**
    * @Description: 订单取消
    * @Param: [orderId, identity]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */

    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId) {
        return orderInfoService.cancel(orderId);
    }


    @GetMapping("/detail")
    public ResponseResult<OrderInfo> detail(Long orderId){
        return orderInfoService.detail(orderId);
    }


    @GetMapping("/current")
    public ResponseResult<OrderInfo> currentOrder(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.parseToken(authorization);
        String identity = tokenResult.getIdentity();
        if (!identity.equals(IdentityConstants.DRIVER_IDENTITY)){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        String phone = tokenResult.getPhone();

        return orderInfoService.currentOrder(phone,IdentityConstants.DRIVER_IDENTITY);
    }
}
