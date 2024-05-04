package com.mashibing.internalcommon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-22 10:46
 **/
@Data

public class OrderRequest {

    //订房id
    private Long orderId;

    /**
     * 乘客ID
     */
    private Long passengerId;

    /**
     * 乘客手机号
     */
    private String passengerPhone;

    //下单行政区城---cityCode
    private String address;
    //出发时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departTime;
    // 下单时问
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    //出发地量
    private String departure;
    //出发经度
    private String depLongitude;
    //出发括度
    private String depLatitude;
    // 目的地
    private String destination;
    //目的地经度
    private String destLongitude;
    //目的地节度
    private String destLatitude;
    // 坐标加露标识 1:gcj-02,2:wgs84,3:bd-09,4:Cgcs2000北平0
    private Integer encrypt;
    //运价类型编码
    private String fareType;
    //运价版本
    private Integer fareVersion;

    //请求设备号(唯一码)
    private String deviceCode;

    /**
     * 司机去接乘客出发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime toPickUpPassengerTime;

    /**
     * 去接乘客时，司机的经度
     */
    private String toPickUpPassengerLongitude;

    /**
     * 去接乘客时，司机的纬度
     */
    private String toPickUpPassengerLatitude;

    /**
     * 去接乘客时，司机的地点
     */
    private String toPickUpPassengerAddress;


    /**
     * 接到乘客，乘客上车时间
     */
    private LocalDateTime pickUpPassengerTime;

    /**
     * 接到乘客，乘客上车经度
     */
    private String pickUpPassengerLongitude;

    /**
     * 接到乘客，乘客上车纬度
     */
    private String pickUpPassengerLatitude;

    /**
     * 乘客下车时间
     */
    private LocalDateTime passengerGetoffTime;

    /**
     * 乘客下车经度
     */
    private String passengerGetoffLongitude;

    /**
     * 乘客下车纬度
     */
    private String passengerGetoffLatitude;


    /**
     * 接预约单时车辆经度
     */
    private String receiveOrderCarLongitude;

    /**
     * 接预约单时车辆纬度
     */
    private String receiveOrderCarLatitude;


    //车辆类型
    private String vehicleType;
}