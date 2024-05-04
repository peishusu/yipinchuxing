package com.mashibing.serviceorder.service;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.internalcommon.request.OrderRequest;

import javax.xml.ws.Response;

public interface OrderInfoService {
    //创建订单
    ResponseResult add(OrderRequest orderRequest);

    //实时派发订单业务逻辑
    int dispatchRealTimeOrder(OrderInfo orderInfo);

    ResponseResult toPickUpPassenger(OrderRequest orderRequest);

    ResponseResult arriveDeparture(OrderRequest orderRequest);

    ResponseResult pickUpPassenger(OrderRequest orderRequest);

    ResponseResult passengerGetoff(OrderRequest orderRequest);

    ResponseResult pushPay(OrderRequest orderRequest);

    ResponseResult pay(OrderRequest orderRequest);

    ResponseResult cancel(Long orderId, String identity);

    ResponseResult<OrderInfo> detail(Long orderId);

    ResponseResult current(String phone, String identity);

    ResponseResult book(OrderRequest orderRequest);

    ResponseResult grab(DriverGrabRequest driverGrabRequest);
}
