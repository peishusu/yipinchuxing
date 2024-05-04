package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.request.OrderRequest;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderService {

    ResponseResult add(OrderRequest orderRequest);

    ResponseResult cancel(Long orderId);

    ResponseResult detail(Long orderId);

    ResponseResult<OrderInfo> currentOrder(String phone, String passengerIdentity);

    ResponseResult book(OrderRequest orderRequest);
}
