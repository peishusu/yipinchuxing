package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @program: online-taxi-public
 * @description:根据carId帅选出的可以进行派单的司机的相关信息
 * @author: lydms
 * @create: 2024-03-24 16:15
 **/
@Data
public class OrderDriverResponse {
    private Long carId;
    private Long driverId;
    private String driverPhone;

    /**
     * 机动车驾驶证号
     */
    private String licenseId;

    /**
     * 车辆号牌
     */
    private String vehicleNo;

    //车型
    private String vehicleType;

}
