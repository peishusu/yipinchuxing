package com.mashibing.internalcommon.constant;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-22 15:24
 **/
public class OrderConstants {
    //0：订单超时无效
    public static final int ORDER_INVALID = 0;
    //1: 订单开始
    public static final int ORDER_START = 1;
    // 2：司机接单
    public static final int DRIVER_RECEIVE_ORDER = 2;
    //3：去接乘客
    public static final int DRIVER_TO_PICK_UP_PASSENGER = 3;
    // 4：司机到达乘客起点
    public static final int DRIVER_ARRIVED_DEPARTURE = 4;
    //5：乘客上车，司机开始行程
    public static final int PICK_UP_PASSENGER = 5;
    // 6：到达目的地，行程结束，未支付
    public static final int PASSENGER_GETOFF = 6;
    //7：发起收款
    public static final int TO_START_PAY = 7;
    //8：支付完成
    public static final int SUCCESS_PAY = 8;
    //9．订单取消
    public static final int ORDER_CANCEL = 9;



    /*
    *   撤销类型代码
        1:乘客提前撤销
        2:驾驶员提前撤销
        3:平台公司撤销
        4;乘客违约撤销
        5:驾驶员违约撤销
    *
    * */
    public static final int CANCEL_PASSENGER_BEFORE = 1;
    public static final int CANCEL_DRIVER_BEFORE = 2;
    public static final int CANCEL_PLATFORM_BEFORE = 3;
    public static final int CANCEL_PASSENGER_ILLEGAL = 4;
    public static final int CANCEL_DRIVER_ILLEGAL = 5;



}
