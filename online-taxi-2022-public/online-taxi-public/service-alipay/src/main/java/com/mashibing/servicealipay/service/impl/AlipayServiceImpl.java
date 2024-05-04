package com.mashibing.servicealipay.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.servicealipay.remote.ServiceOrderClient;
import com.mashibing.servicealipay.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-28 21:15
 **/
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;
    @Override
    public void pay(String outTradeNo) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(Long.valueOf(outTradeNo));
        serviceOrderClient.pay(orderRequest);
    }
}
