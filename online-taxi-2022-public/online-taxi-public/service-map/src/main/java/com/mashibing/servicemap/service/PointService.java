package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;

public interface PointService {

    ResponseResult upload(PointRequest pointRequest);
}
