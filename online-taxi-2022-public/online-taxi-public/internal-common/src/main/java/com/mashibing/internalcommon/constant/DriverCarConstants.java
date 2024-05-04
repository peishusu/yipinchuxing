package com.mashibing.internalcommon.constant;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 10:17
 **/
public class DriverCarConstants {

    /*司机和对应的车辆的绑定关系：1->绑定  2->非绑定*/
    public static final int DRIVER_CAR_BIND = 1;
    public static final int DRIVER_CAR_UNBIND = 2;


    /*司机状态：1 有效 0 无效*/

    public static final int DRIVER_STATE_VALID = 1;

    public static final int DRIVER_STATE_INVALID = 0;

    /*司机是否存在：0 不存在 1存在*/
    public static final int DRIVER_EXISTS = 1;
    public static final int DRIVER_NOT_EXISTS = 0;

    /* 司机工作状态：收车 0 出车 1 暂停 2  服务中 3*/
    public static int DRIVER_WORK_STATUS_STOP = 0;
    public static int DRIVER_WORK_STATUS_START = 1;
    public static int DRIVER_WORK_STATUS_SUSPEND = 2;

    /**
     * 司机工作状态：服务中
     */
    public static int DRIVER_WORK_STATUS_SERVING = 3;

}
