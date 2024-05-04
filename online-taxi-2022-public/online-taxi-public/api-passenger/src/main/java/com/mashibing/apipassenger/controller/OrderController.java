package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.OrderRequest;
import com.mashibing.apipassenger.service.OrderService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @program: online-taxi-public
 * @description: 订单模块
 * @author: lydms
 * @create: 2024-03-22 10:44
 **/
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
    * @Description: 创建订单
    * @Param: []
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/22
    */
    @PostMapping("/add")
    public ResponseResult add(@Validated @RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest);
        return orderService.add(orderRequest);
    }

    /**
     * 乘客创建预约单
     * @return
     */
    @PostMapping("/book")
    public ResponseResult book(@Validated @RequestBody OrderRequest orderRequest){
        return orderService.book(orderRequest);
    }


    @PostMapping("/cancel")
    public ResponseResult cancel(@NotNull(message = "订单id不能为空") @Positive(message = "订单id格式不正确") @RequestParam Long orderId){
        return orderService.cancel(orderId);
    }


    @GetMapping("/detail")
    public ResponseResult<OrderInfo> detail(@NotNull(message = "订单id不能为空") @Positive(message = "订单id格式不正确") Long orderId){
        return orderService.detail(orderId);
    }


    @GetMapping("/current")
    public ResponseResult<OrderInfo> currentOrder(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.parseToken(authorization);
        String identity = tokenResult.getIdentity();
        if (!identity.equals(IdentityConstants.PASSENGER_IDENTITY)){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        String phone = tokenResult.getPhone();

        return orderService.currentOrder(phone,IdentityConstants.PASSENGER_IDENTITY);
    }


}
