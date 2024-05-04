package com.mashibing.internalcommon.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 09:49
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastPriceResponse {
    private double price;
    private String cityCode;
    private String vehicleType;
    private String fareType;
    private Integer fareVersion;
}
