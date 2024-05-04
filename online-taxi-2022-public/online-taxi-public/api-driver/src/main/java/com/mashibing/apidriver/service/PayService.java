package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.ResponseResult;

public interface PayService {
    ResponseResult pushPayInfo(String orderId, String price,Long passengerId);
}
