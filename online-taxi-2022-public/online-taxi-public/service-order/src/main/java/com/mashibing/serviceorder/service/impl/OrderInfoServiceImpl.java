package com.mashibing.serviceorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.request.PriceRuleIsNewRequest;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;
import com.mashibing.internalcommon.utils.RedisPrefixUtils;
import com.mashibing.serviceorder.entity.DriverOrderStatistics;
import com.mashibing.serviceorder.mapper.DriverOrderStatisticsMapper;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.ServiceDriverUserClient;
import com.mashibing.serviceorder.remote.ServiceMapClient;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import com.mashibing.serviceorder.remote.ServiceSsePushClient;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.redisson.api.RedissonClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-22
 */
@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;


    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("redissonBootYml")
    private RedissonClient redissonClient;

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    @Autowired
    DriverOrderStatisticsMapper driverOrderStatisticsMapper;


    /**
     * 司机抢单
     *
     * @param driverGrabRequest
     * @return
     */
    @Override
    @Transactional
    public ResponseResult grab(DriverGrabRequest driverGrabRequest) {

        System.out.println("请求来了：" + driverGrabRequest.getDriverId());
        Long orderId = driverGrabRequest.getOrderId();
//        synchronized(this){
        String orderIdStr = (orderId + "").intern();
//        synchronized (orderIdStr) {

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if (orderInfo == null) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_NOT_EXISTS.getCode(), CommonStatusEnum.ORDER_NOT_EXISTS.getMessage());
        }

        int orderStatus = orderInfo.getOrderStatus();
        if (orderStatus != OrderConstants.ORDER_START) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_CAN_NOT_GRAB.getCode(), CommonStatusEnum.ORDER_CAN_NOT_GRAB.getMessage());
        }

        Long driverId = driverGrabRequest.getDriverId();
        Long carId = driverGrabRequest.getCarId();
        String licenseId = driverGrabRequest.getLicenseId();
        String vehicleNo = driverGrabRequest.getVehicleNo();
        String receiveOrderCarLatitude = driverGrabRequest.getReceiveOrderCarLatitude();
        String receiveOrderCarLongitude = driverGrabRequest.getReceiveOrderCarLongitude();
        String vehicleType = driverGrabRequest.getVehicleType();
        String driverPhone = driverGrabRequest.getDriverPhone();

        orderInfo.setDriverId(driverId);
        orderInfo.setDriverPhone(driverPhone);
        orderInfo.setCarId(carId);
        orderInfo.setReceiveOrderCarLongitude(receiveOrderCarLongitude);
        orderInfo.setReceiveOrderCarLatitude(receiveOrderCarLatitude);
        orderInfo.setReceiveOrderTime(LocalDateTime.now());
        orderInfo.setLicenseId(licenseId);
        orderInfo.setVehicleNo(vehicleNo);
        orderInfo.setVehicleType(vehicleType);
        orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);
        orderInfoMapper.updateById(orderInfo);

        // 添加司机当天抢单成功的数量
        // 先查询当天的数据
        QueryWrapper<DriverOrderStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("grab_order_date", LocalDate.now());
        queryWrapper.eq("driver_id", driverId);
        DriverOrderStatistics driverOrderStatistics = driverOrderStatisticsMapper.selectOne(queryWrapper);
        if (driverOrderStatistics == null) {
            driverOrderStatistics = new DriverOrderStatistics();
            driverOrderStatistics.setGrabOrderDate(LocalDate.now());
            driverOrderStatistics.setGrabOrderSuccessCount(1);
            driverOrderStatistics.setDriverId(driverId);
            driverOrderStatisticsMapper.insert(driverOrderStatistics);
        } else {
            driverOrderStatistics.setGrabOrderSuccessCount(driverOrderStatistics.getGrabOrderSuccessCount() + 1);
            driverOrderStatisticsMapper.updateById(driverOrderStatistics);
        }
//        }
        return ResponseResult.success();
    }


    @Override
    public ResponseResult add(OrderRequest orderRequest) {

        //需要判断当前计价规则是否为最新的
        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.IsNewestVersion(priceRuleIsNewRequest);


        //当前不是最新的
        if (booleanResponseResult.getData() == false) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getMessage());
        }

        //判断下单的设备是否为黑名单设备
        if (isBlackDevice(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getMessage());
        }

        //判断：下单的城市和计价规则是否正常
        if (!isPriceRuleExists(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(), CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getMessage());
        }

        //判断：当前城市是否存在网约车司机
        if (!isAvailableDriver(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(), CommonStatusEnum.CITY_DRIVER_EMPTY.getMessage());
        }


        //判断当前用户是否存在正在进行的订单
        if (isPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getMessage());
        }


        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);
        //这边进行乘客下单信息的补全
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);
        orderInfo.setVehicleType(orderRequest.getFareType().substring(orderRequest.getAddress().length() + 1));
        //插入数据库
        orderInfoMapper.insert(orderInfo);
        //定时任务的处理
        for (int i = 0; i < 6; i++) {
            //派单 dispatcherRealTimeOrder
            int result = dispatchRealTimeOrder(orderInfo);
            if (result == 1) {
                break;
            }
            if (i == 5) {
                //订单无效
                orderInfo.setOrderStatus(OrderConstants.ORDER_INVALID);
                orderInfoMapper.updateById(orderInfo);
            } else {
                //等待20s
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        return ResponseResult.success("");
    }


    /**
     * 新建预约订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult book(OrderRequest orderRequest) {

        // 测试当前城市是否有可用的司机
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        log.info("测试城市是否有司机结果：" + availableDriver.getData());
        if (!availableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(), CommonStatusEnum.CITY_DRIVER_EMPTY.getMessage());
        }

        // 需要判断计价规则的版本是否为最新
        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        ResponseResult<Boolean> aNew = servicePriceClient.IsNewestVersion(priceRuleIsNewRequest);
        if (!(aNew.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getMessage());
        }

//         需要判断 下单的设备是否是 黑名单设备
        if (isBlackDevice(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getMessage());
        }

        // 判断：下单的城市和计价规则是否正常
        if (!isPriceRuleExists(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(), CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getMessage());
        }


        // 判断乘客 是否有进行中的订单
        if (isPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getMessage());
        }

        // 创建订单
        OrderInfo orderInfo = new OrderInfo();

        BeanUtils.copyProperties(orderRequest, orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);

        // 定时任务的处理
        for (int i = 0; i < 6; i++) {
            // 派单 dispatchRealTimeOrder
            int result = dispatchBookOrder(orderInfo);
            if (result == 1) {
                break;
            }

            // 等待20s
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return ResponseResult.success();
    }


    /**
     * 预约订单-分发订单
     * 如果返回1：说明该预约订单的消息已经被推送给了乘客
     *
     * @param orderInfo
     */
    public int dispatchBookOrder(OrderInfo orderInfo) {
//        log.info("循环一次");
        log.info("循环一次 book");
        int result = 0;


        //2km
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();

        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);
        // 搜索结果
        ResponseResult<List<TerminalResponse>> listResponseResult = null;
        // goto是为了测试。
        radius:
        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.aroundSearch(center, radius);

            log.info("在半径为" + radius + "的范围内，寻找车辆,结果：" + JSONArray.fromObject(listResponseResult.getData()).toString());

            // 获得终端  [{"carId":1578641048288702465,"tid":"584169988"}]

            // 解析终端
            List<TerminalResponse> data = listResponseResult.getData();

            // 为了测试是否从地图上获取到司机
//            List<TerminalResponse> data = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(j);
                Long carId = terminalResponse.getCarId();

                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();

                // 查询是否有对应的可派单司机
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()) {
                    log.info("没有车辆ID：" + carId + ",对于的司机");
                    continue;
                } else {
                    log.info("车辆ID：" + carId + "找到了正在出车的司机");

                    OrderDriverResponse orderDriverResponse = availableDriver.getData();
                    Long driverId = orderDriverResponse.getDriverId();
                    String driverPhone = orderDriverResponse.getDriverPhone();
                    String licenseId = orderDriverResponse.getLicenseId();
                    String vehicleNo = orderDriverResponse.getVehicleNo();
                    String vehicleTypeFromCar = orderDriverResponse.getVehicleType();

                    // 判断车辆的车型是否符合？
                    String vehicleType = orderInfo.getVehicleType();
                    if (!vehicleType.trim().equals(vehicleTypeFromCar.trim())) {
                        System.out.println("车型不符合");
                        continue;
                    }

                    // 通知司机
                    JSONObject driverContent = new JSONObject();

                    try {
                        driverContent.put("orderId", orderInfo.getId());
                        driverContent.put("passengerId", orderInfo.getPassengerId());
                        driverContent.put("passengerPhone", orderInfo.getPassengerPhone());
                        driverContent.put("departure", orderInfo.getDeparture());
                        driverContent.put("depLongitude", orderInfo.getDepLongitude());
                        driverContent.put("depLatitude", orderInfo.getDepLatitude());

                        driverContent.put("destination", orderInfo.getDestination());
                        driverContent.put("destLongitude", orderInfo.getDestLongitude());
                        driverContent.put("destLatitude", orderInfo.getDestLatitude());

                        serviceSsePushClient.push(driverId, IdentityConstants.DRIVER_IDENTITY, driverContent.toString());
                        result = 1;
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

//                    serviceSsePushClient.push(pushRequest);

                    // 退出，不在进行 司机的查找.如果派单成功，则退出循环
                    break radius;
                }

            }

        }

        return result;

    }


    /**
     * @Description: 实时派发订单业务逻辑
     * @Param: [orderInfo]
     * @return: int 返回1代表派单成功
     * @Author: JiLaiYa
     * @Date: 2024/3/24
     */
    @Override
    public int dispatchRealTimeOrder(OrderInfo orderInfo) {
        log.info("循环一次");
        int result = 0;//0代表未派单成功
        //2km
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();
        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusList = Arrays.asList(2000, 4000, 5000);
        ResponseResult<List<TerminalResponse>> listResponseResult = null;
        radius:
        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.aroundSearch(center, radius);
            log.info("在半径为" + radius + "的范围内查询车辆");
            //获得当前范围内的所有终端
            List<TerminalResponse> data = listResponseResult.getData();
//            List<TerminalResponse> data = new ArrayList<>();

            //解析终端

            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(j);
                Long carId = terminalResponse.getCarId();
                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();

                //查询是否有对应carId的司机
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.CAR_NOT_OWN_DRIVER.getCode() || availableDriver.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()) {
                    log.info("没有车辆id" + carId + ",对应的司机");
                    continue;
                } else {
                    log.info("找到了正在出车的司机，他的车辆id：" + carId);
                    //判断当前找到的司机是否有正在进行的订单
                    OrderDriverResponse orderDriverResponse = availableDriver.getData();
                    Long driverId = orderDriverResponse.getDriverId();
                    String driverPhone = orderDriverResponse.getDriverPhone();
                    String licenseId = orderDriverResponse.getLicenseId();
                    String vehicleNo = orderDriverResponse.getVehicleNo();
                    String vehicleTypeFromCar = orderDriverResponse.getVehicleType();
                    //判断车辆的车型是否符合
                    if (!orderInfo.getVehicleType().trim().equals(vehicleTypeFromCar.trim())) {
                        log.info("车型不符合");
                        continue;
                    }

                    String driverIdLock = (driverId + "").intern();
                    RLock lock = redissonClient.getLock(driverIdLock);
                    lock.lock();

                    //查看当前司机时候有正在进行的订单
                    if (isDriverOrderGoingOn(driverId) > 0) {
//                        return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),CommonStatusEnum.ORDER_GOING_ON.getMessage());
                        lock.unlock();
                        continue;
                    }
                    //订单直接匹配司机
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(driverPhone);
                    orderInfo.setCarId(carId);
                    //这两个从地图服务中获取，
                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);
                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setLicenseId(licenseId);
                    orderInfo.setVehicleNo(vehicleNo);
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);//司机接单了

                    try {
                        //通知司机
                        JSONObject driverContent = new JSONObject();
                        driverContent.put("orderId", orderInfo.getId());
                        driverContent.put("passengerId", orderInfo.getPassengerId());
                        driverContent.put("passengerPhone", orderInfo.getPassengerPhone());
                        driverContent.put("departure", orderInfo.getDeparture());
                        driverContent.put("depLongitude", orderInfo.getDepLongitude());
                        driverContent.put("destination", orderInfo.getDestination());
                        driverContent.put("destLongitude", orderInfo.getDestLongitude());
                        driverContent.put("destLatitude", orderInfo.getDestLatitude());
                        serviceSsePushClient.push(driverId, IdentityConstants.DRIVER_IDENTITY, driverContent.toString());
                        //通知乘客
                        JSONObject passengerContent = new JSONObject();
                        passengerContent.put("orderId", orderInfo.getId());
                        passengerContent.put("driverId", orderInfo.getDriverId());
                        passengerContent.put("driverPhone", orderInfo.getDriverPhone());
                        passengerContent.put("vehicleNo", orderInfo.getVehicleNo());
                        //获取司机所绑定车的信息
                        ResponseResult<Car> carById = serviceDriverUserClient.getCarById(carId);
                        Car carRemote = carById.getData();
                        passengerContent.put("brand", carRemote.getBrand());
                        passengerContent.put("model", carRemote.getModel());
                        passengerContent.put("vehicleColor", carRemote.getVehicleColor());

                        passengerContent.put("receiveOrderCarLongitude", orderInfo.getReceiveOrderCarLongitude());
                        passengerContent.put("receiveOrderCarLatitude", orderInfo.getReceiveOrderCarLatitude());

                        serviceSsePushClient.push(orderInfo.getPassengerId(), IdentityConstants.PASSENGER_IDENTITY, passengerContent.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //接单以后再次更新订单的相关信息
                    orderInfoMapper.updateById(orderInfo);
                    result = 1;
                    lock.unlock();
                    //退出，不再进行 司机的查找
                    break radius;
                }
            }
        }
        return result;

    }

    @Override
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {

        Long orderId = orderRequest.getOrderId();
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        //根据orderId查询到对应的订单
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);

        //进行相应的属性更新
        orderInfo.setToPickUpPassengerAddress(toPickUpPassengerAddress);
        orderInfo.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        orderInfo.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        orderInfo.setToPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstants.DRIVER_TO_PICK_UP_PASSENGER);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("司机正在前往接乘客");


    }

    @Override
    public ResponseResult arriveDeparture(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        //查询对应的订单号
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);
        //跟新订单信息
        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstants.DRIVER_ARRIVED_DEPARTURE);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        //查询对应的订单号
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);
        //跟新订单信息
        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setPickUpPassengerLongitude(orderRequest.getPickUpPassengerLongitude());
        orderInfo.setPickUpPassengerLatitude(orderRequest.getPickUpPassengerLatitude());
        orderInfo.setOrderStatus(OrderConstants.PICK_UP_PASSENGER);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("成功接到乘客!!!");
    }

    @Override
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setPassengerGetoffTime(LocalDateTime.now());
        orderInfo.setPassengerGetoffLongitude(orderRequest.getPassengerGetoffLongitude());
        orderInfo.setPassengerGetoffLatitude(orderRequest.getPassengerGetoffLatitude());

        orderInfo.setOrderStatus(OrderConstants.PASSENGER_GETOFF);
        // 订单行驶的路程和时间,调用 service-map
        ResponseResult<Car> carById = serviceDriverUserClient.getCarById(orderInfo.getCarId());
        Long starttime = orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long endtime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("开始时间：" + starttime);
        System.out.println("结束时间：" + endtime);
        // 1668078028000l,测试的时候不要跨天
        ResponseResult<TrSearchResponse> trsearch = serviceMapClient.trsearch(carById.getData().getTid(), starttime, endtime);
        TrSearchResponse data = trsearch.getData();
        Long driveMile = data.getDriveMile();
        Long driveTime = data.getDriveTime();

        orderInfo.setDriveMile(driveMile);
        orderInfo.setDriveTime(driveTime);

        // 获取价格
        String address = orderInfo.getAddress();
        String vehicleType = orderInfo.getVehicleType();
        ResponseResult<Double> doubleResponseResult = servicePriceClient.calculatePrice(driveMile.intValue(), driveTime.intValue(), address, vehicleType);
        Double price = doubleResponseResult.getData();
        orderInfo.setPrice(price);

        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult pushPay(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);
        orderInfo.setOrderStatus(OrderConstants.TO_START_PAY);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("乘客成功收到付款请求");
    }

    @Override
    public ResponseResult pay(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);
        orderInfo.setOrderStatus(OrderConstants.SUCCESS_PAY);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("支付完成");
    }

    @Override
    public ResponseResult cancel(Long orderId, String identity) {
        //查询当前订单状态
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        Integer orderStatus = orderInfo.getOrderStatus();

        LocalDateTime cancelTime = LocalDateTime.now();
        Integer cancelOperator = null;
        Integer cancelTypeCode = null;

        int cancelFlag = 1;//1代表取消成功，0代表取消失败

        //更新订单的取消状态
        //乘客取消订单
        if (identity.trim().equals(IdentityConstants.PASSENGER_IDENTITY)) {
            switch (orderStatus) {
                //订单开始
                case OrderConstants.ORDER_START:
                    cancelTypeCode = OrderConstants.CANCEL_PASSENGER_BEFORE;
                    break;
                //司机接到订单
                case OrderConstants.DRIVER_RECEIVE_ORDER:
                    LocalDateTime receiveOrderTime = orderInfo.getReceiveOrderTime();
                    long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1) {
                        cancelTypeCode = OrderConstants.CANCEL_PASSENGER_ILLEGAL;
                    } else {
                        cancelTypeCode = OrderConstants.CANCEL_PASSENGER_BEFORE;
                    }
                    break;
                //司机去接乘客
                case OrderConstants.DRIVER_TO_PICK_UP_PASSENGER:
                    cancelTypeCode = OrderConstants.CANCEL_PASSENGER_ILLEGAL;
                    break;
                //司机到到乘客起点
                case OrderConstants.DRIVER_ARRIVED_DEPARTURE:
                    cancelTypeCode = OrderConstants.CANCEL_PASSENGER_ILLEGAL;
                    break;
                default:
                    log.info("乘客取消订单失败");
                    cancelFlag = 0;
                    break;
            }
        }

        //司机取消订单
        if (identity.trim().equals(IdentityConstants.DRIVER_IDENTITY)) {
            switch (orderStatus) {
                case OrderConstants.DRIVER_RECEIVE_ORDER:
                case OrderConstants.DRIVER_TO_PICK_UP_PASSENGER:
                case OrderConstants.DRIVER_ARRIVED_DEPARTURE:
                    LocalDateTime receiveOrderTime = orderInfo.getReceiveOrderTime();
                    long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1) {
                        cancelTypeCode = OrderConstants.CANCEL_DRIVER_ILLEGAL;
                    } else {
                        cancelTypeCode = OrderConstants.CANCEL_DRIVER_BEFORE;
                    }
                    break;
                default:
                    log.info("司机取消订单失败");
                    cancelFlag = 0;
                    break;
            }
        }

        if (cancelFlag == 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_CANCEL_ERROR.getCode(), CommonStatusEnum.ORDER_CANCEL_ERROR.getMessage());
        }
        orderInfo.setCancelTypeCode(cancelTypeCode);
        orderInfo.setCancelTime(cancelTime);
        orderInfo.setCancelOperator(Integer.parseInt(identity));
        orderInfo.setOrderStatus(OrderConstants.ORDER_CANCEL);
        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("取消订单成功");


    }


    /**
     * @Description: 判断当前城市是否存在网约车司机
     * @Param: [orderRequest]
     * @return: java.lang.Boolean
     * @Author: JiLaiYa
     * @Date: 2024/3/24
     */

    private Boolean isAvailableDriver(OrderRequest orderRequest) {
        String cityCode = orderRequest.getAddress();
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(cityCode);
        return availableDriver.getData();
    }


    /**
     * @Description: 根据城市编码和车辆类型判断是否存在对应的计价规则
     * @Param: [orderRequest]
     * @return: java.lang.Boolean
     * @Author: JiLaiYa
     * @Date: 2024/3/23
     */

    private Boolean isPriceRuleExists(OrderRequest orderRequest) {
        String cityCode = orderRequest.getAddress();
        String fareType = orderRequest.getFareType();
        String vehicleType = fareType.substring(cityCode.length() + 1);
        //调用sercvice-price服务判断是否存在对应的计价规则
//        PriceRule priceRule = new PriceRule();
//        priceRule.setCityCode(cityCode);
//        priceRule.setVehicleType(vehicleType);
        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.ifExists(cityCode,vehicleType);
        return booleanResponseResult.getData();
    }


    /**
     * @Description: 判断当前Driver是否存在正在进行的的订单
     * @Param: [driverId]
     * @return: java.lang.Integer 大于0，代表存在正在进行的的订单，代表无法创建新的订单
     * @Author: JiLaiYa
     * @Date: 2024/3/23
     */

    private Integer isDriverOrderGoingOn(Long driverId) {
        //判断有正在进行的订单不允许下单
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        //根据passenger_id找到其下面存在的阿订单
        orderInfoQueryWrapper.eq("driver_id", driverId);
        //如果该paasenger存在订单的话，要判断订单的状态
        orderInfoQueryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
        );
        Integer validOrderNumber = orderInfoMapper.selectCount(orderInfoQueryWrapper);
        log.info("司机id:" + driverId + ",正在进行的订单的数量：" + validOrderNumber);
        return validOrderNumber;
    }


    /**
     * @Description: 判断当前passenger是否存在正在进行的的订单
     * @Param: [passengerId]
     * @return: java.lang.Integer 大于0，代表存在正在进行的的订单，代表无法创建新的订单
     * @Author: JiLaiYa
     * @Date: 2024/3/23
     */

    private Integer isPassengerOrderGoingOn(Long passengerId) {
        //判断有正在进行的订单不允许下单
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        //根据passenger_id找到其下面存在的阿订单
        orderInfoQueryWrapper.eq("passenger_id", passengerId);
        //如果该paasenger存在订单的话，要判断订单的状态
        orderInfoQueryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START)
                .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status", OrderConstants.TO_START_PAY)
        );
        Integer validOrderNumber = orderInfoMapper.selectCount(orderInfoQueryWrapper);
        return validOrderNumber;
    }


    /**
     * @Description: 判断当前设备是否为黑名单，也就是在一小时内连续进行了2次下单
     * @Param: [deviceCodeKey]
     * @return: java.lang.Boolean
     * @Author: JiLaiYa
     * @Date: 2024/3/23
     */

    private Boolean isBlackDevice(OrderRequest orderRequest) {
        String deviceCode = orderRequest.getDeviceCode();
        String deviceCodeKey = RedisPrefixUtils.generateBlackDeviceCodePrefix(deviceCode);
        Boolean aBoolean = stringRedisTemplate.hasKey(deviceCodeKey);
        if (aBoolean) {
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int i = Integer.parseInt(s);
            //说明当前设备为黑名单
            if (i >= 2) {
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        } else {
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey, "1", 1, TimeUnit.HOURS);
        }
        return false;
    }


    public ResponseResult<OrderInfo> detail(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        return ResponseResult.success(orderInfo);
    }


    public ResponseResult<OrderInfo> current(String phone, String identity) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();

        if (identity.equals(IdentityConstants.DRIVER_IDENTITY)) {
            queryWrapper.eq("driver_phone", phone);

            queryWrapper.and(wrapper -> wrapper
                    .eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                    .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                    .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                    .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)

            );
        }
        if (identity.equals(IdentityConstants.PASSENGER_IDENTITY)) {
            queryWrapper.eq("passenger_phone", phone);
            queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START)
                    .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                    .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                    .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                    .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
                    .or().eq("order_status", OrderConstants.PASSENGER_GETOFF)
                    .or().eq("order_status", OrderConstants.TO_START_PAY)
            );
        }

        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);
        return ResponseResult.success(orderInfo);
    }


}
