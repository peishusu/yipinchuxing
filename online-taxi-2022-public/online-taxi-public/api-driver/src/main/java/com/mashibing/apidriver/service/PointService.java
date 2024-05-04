package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;

public interface PointService {
    ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest);
}
