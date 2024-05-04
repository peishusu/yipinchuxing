package com.mashibing.apidriver.service.impl;

import com.mashibing.apidriver.remote.ServiceOrderClient;
import com.mashibing.apidriver.remote.ServiceSsePushClient;
import com.mashibing.apidriver.service.PayService;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-28 16:39
 **/
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    @Override
    public ResponseResult pushPayInfo(String orderId, String price,Long passengerId) {
        //需要根据orderID查询到对应order信息，跟新订单状态为7(视频中未考虑到)
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(Long.valueOf(orderId));
        serviceOrderClient.pushPay(orderRequest);

        //封装消息
        JSONObject message = new JSONObject();
        message.put("price",price);
        message.put("orderId",orderId);
        //推送消息
        serviceSsePushClient.push(passengerId, IdentityConstants.PASSENGER_IDENTITY, message.toString());

        return ResponseResult.success("");
    }
}
