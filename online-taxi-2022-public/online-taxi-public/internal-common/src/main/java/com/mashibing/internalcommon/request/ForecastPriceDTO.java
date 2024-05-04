package com.mashibing.internalcommon.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 09:41
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastPriceDTO {
    private String depLongitude;
    private String depLatitude;
    private String destLongitude;
    private String destLatitude;
    private String cityCode;
    private String vehicleType;

}
