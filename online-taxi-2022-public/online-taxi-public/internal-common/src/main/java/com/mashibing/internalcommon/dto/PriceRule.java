package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-18 09:20
 **/
@Data
public class PriceRule {
    private String cityCode;
    private String vehicleType;
    private Double startFare;
    private Integer startMile;
    private Double unitPricePerMile;
    private Double unitPricePerMinute;
    private Integer fareVersion;
    private String fareType;
}
