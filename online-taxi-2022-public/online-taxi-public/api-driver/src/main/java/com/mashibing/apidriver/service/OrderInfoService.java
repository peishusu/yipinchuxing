package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderInfoService {
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest);

    ResponseResult arriveDeparture(OrderRequest orderRequest);

    ResponseResult pickUpPassenger(OrderRequest orderRequest);

    ResponseResult passengerGetoff(OrderRequest orderRequest);

    ResponseResult cancel(Long orderId);

    public ResponseResult<OrderInfo> detail(Long orderId);

    ResponseResult<OrderInfo> currentOrder(String phone, String driverIdentity);

    ResponseResult grap(String driverPhone, Long orderId, String receiveOrderCarLongitude, String receiveOrderCarLatitude);
}
