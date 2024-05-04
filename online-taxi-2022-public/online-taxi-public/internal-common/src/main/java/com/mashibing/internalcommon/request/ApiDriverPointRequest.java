package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 21:48
 **/
@Data
public class ApiDriverPointRequest {
    private Long carId;

    private PointDTO[] points;
}
