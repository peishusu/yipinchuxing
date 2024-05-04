package com.mashibing.apipassenger.service.Impl;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.apipassenger.request.OrderRequest;
import com.mashibing.apipassenger.service.OrderService;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-22 12:05
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    @Override
    public ResponseResult add(OrderRequest orderRequest) {
        return serviceOrderClient.add(orderRequest);
    }

    @Override
    public ResponseResult cancel(Long orderId) {
        return serviceOrderClient.cancel(orderId, IdentityConstants.PASSENGER_IDENTITY);
    }

    public ResponseResult<OrderInfo> detail(Long orderId){
        return serviceOrderClient.detail(orderId);
    }


    public ResponseResult<OrderInfo> currentOrder(String phone , String identity){
        return serviceOrderClient.current(phone,identity);
    }

    @Override
    public ResponseResult book(OrderRequest orderRequest) {
        return serviceOrderClient.book(orderRequest);
    }


}
