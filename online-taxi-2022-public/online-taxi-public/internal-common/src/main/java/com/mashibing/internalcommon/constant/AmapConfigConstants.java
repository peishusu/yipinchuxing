package com.mashibing.internalcommon.constant;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 15:30
 **/
public class AmapConfigConstants {
    /*访问高德地图接口的url的前缀*/
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving";

    /*访问高德地图区域的url的前缀*/
    public static final String DISTINCT_URL = "https://tsapi.amap.com/v1/track/terminal/add";


    /*新增服务*/
    public static final String SERVICE_ADD_URL = "https://tsapi.amap.com/v1/track/service/add";

    /*新增终端*/
    public static final String TERMINAL_ADD = "https://tsapi.amap.com/v1/track/terminal/add";

    /*新增轨迹*/
    public static final String TRACK_ADD = "https://tsapi.amap.com/v1/track/trace/add";

    /*轨迹上传*/
    public static final String POINT_UPLOAD = "https://tsapi.amap.com/v1/track/point/upload";

    /*周边查询轨迹*/
    public static final String TERMINAL_AROUNDSEARCH = "https://tsapi.amap.com/v1/track/terminal/aroundsearch";

    /*查询指定终端特定时间下的所有轨迹*/
    public static final String TERMINAL_TRSEARCH = "https://tsapi.amap.com/v1/track/terminal/trsearch";


    /*路径规划 json key 值*/
    public static final String STATUS = "status";
    public static final String ROUTE = "route";
    public static final String PATHS = "paths";
    public static final String DISTANCE = "distance";
    public static final String DURATION = "duration";


    /*地图字典 相关字段的key*/
    public static final String ADCODE = "adcode";
    public static final String NAME = "name";
    public static final String LEVEL = "level";
    public static final String DISTRICTS = "districts";


}
