package com.mashibing.internalcommon.constant;

import lombok.Data;
import lombok.Getter;


public enum CommonStatusEnum {
    /*校验码不正确：1000 -1099*/
    VERIFICATION_CODE_ERROR(1099,"校验码不正确"),

    /*token提示错误：1100-1199*/
    CALL_USER_ADD_ERROR(1000,"调用新增用户异常"),

    CHECK_CODE_ERROR(1001,"验证手机号和验证码 异常"),
    TOKEN_ERROR(1199,"token错误"),

    /*用户查询失败：1200*/
    USER_ERROR(1200,"用户查询失败"),

    /*计价规则错误码：1300-1399*/
    PRICE_RULE_EMPTY(1300,"计价规则不存在"),

    PRICE_RULE_EXISTS(1301,"计价规则已存在，不允许添加"),

    PRICE_RULE_NOT_EDIT(1302,"计价规则没有变化"),

    PRICE_RULE_CHANGED(1303,"计价规则发生变化"),


    /*地图区域信息查询错误：1400-1499*/
    MAP_DISTRICT_ERROR(1400,"请求地图错误"),


    /*司机和车辆:1500-1599*/
    DRIVER_CAR_BIND_NOT_EXISTS(1500,"司机和车辆绑定关系不存在"),
    DRIVER_NOT_EXIST(1501,"司机不存在"),
    DRIVER_CAR_BIND_EXISTS(1502,"司机和车辆绑定关系已经存在，请勿重复绑定"),
    DRIVER_BIND_EXISTS(1503,"司机已经被绑定了，请勿重复绑定"),
    CAR_BIND_EXISTS(1504,"车辆已经绑定了，请勿重复绑定"),
    CITY_DRIVER_EMPTY(1505,"当前城市没有可用的司机"),


    AVAILABLE_DRIVER_EMPTY(1506,"可用的司机为空"),
    CAR_NOT_EXISTS(1507,"车辆不存在"),
    DRIVER_STATUS_UPDATE_ERROR(1508,"司机工作状态修改失败"),

    CAR_NOT_OWN_DRIVER(1599,"当前car没有相应的司机与之绑定"),


    /*订单：1600-1699*/
    ORDER_GOING_ON(1600,"有正在进行的订单"),


    ORDER_NOT_EXISTS(1604,"订单不存在"),

    ORDER_CAN_NOT_GRAB(1605 , "订单不能被抢"),


    DEVICE_IS_BLACK(1601,"该设备超过下单次数"),

    CITY_SERVICE_NOT_SERVICE(1602,"当前城市不提供叫车服务"),

    ORDER_CANCEL_ERROR(1603,"订单取消失败"),
    ORDER_GRABING(1606,"订单正在被抢"),
    ORDER_UPDATE_ERROR(1607,"订单修改失败"),

    /*统一验证提示 1700 - 1799*/
    VALIDATION_EXCEPTION(1700,"统一验证框架的错误提示"),





    SUCCESS(1,"success"),
    FAIL(0,"统一异常信息fail");


    @Getter
    private int code;
    @Getter
    private String message;

    CommonStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
