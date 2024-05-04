package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 20:27
 **/
@Data
public class PointRequest {

    private String tid;
    private String trid;
    private PointDTO[] points;
}
