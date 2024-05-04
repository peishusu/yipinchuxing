package com.mashibing.apidriver.service.impl;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceOrderClient;
import com.mashibing.apidriver.service.OrderInfoService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-28 09:27
 **/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private ServiceOrderClient serviceOrderClient;

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Override
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        return serviceOrderClient.toPickUpPassenger(orderRequest);
    }

    @Override
    public ResponseResult arriveDeparture(OrderRequest orderRequest) {
        return serviceOrderClient.arriveDeparture(orderRequest);
    }

    @Override
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        return serviceOrderClient.pickUpPassenger(orderRequest);
    }

    @Override
    public ResponseResult passengerGetoff(OrderRequest orderRequest) {
        return serviceOrderClient.passengerGetoff(orderRequest);
    }

    @Override
    public ResponseResult cancel(Long orderId) {
        return serviceOrderClient.cancel(orderId, IdentityConstants.DRIVER_IDENTITY);
    }

    @Override
    public ResponseResult<OrderInfo> detail(Long orderId){
        return serviceOrderClient.detail(orderId);
    }


    @Override
    public ResponseResult<OrderInfo> currentOrder(String phone , String identity){
        return serviceOrderClient.current(phone,identity);
    }


    /**
     * 司机抢单
     * @param driverPhone
     * @param orderId
     * @return
     */
    public   ResponseResult grap(String driverPhone , Long orderId , String receiveOrderCarLongitude, String receiveOrderCarLatitude){
        // 根据 司机的电话，查询车辆信息
        ResponseResult<DriverCarBindingRelationship> driverCarRelationShipResponseResult = serviceDriverUserClient.getDriverCarBindingRelationship(driverPhone);

        if (driverCarRelationShipResponseResult == null){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getMessage());
        }
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarRelationShipResponseResult.getData();
        Long carId = driverCarBindingRelationship.getCarId();

        ResponseResult<OrderDriverResponse> availableDriverResponseResult = serviceDriverUserClient.getAvailableDriver(carId);
        if (availableDriverResponseResult == null || availableDriverResponseResult.getData() == null){
            return ResponseResult.fail(CommonStatusEnum.CAR_NOT_EXISTS.getCode(),CommonStatusEnum.CAR_NOT_EXISTS.getMessage());
        }
        OrderDriverResponse orderDriverResponse = availableDriverResponseResult.getData();
        Long driverId = orderDriverResponse.getDriverId();
        String licenseId = orderDriverResponse.getLicenseId();
        String vehicleNo = orderDriverResponse.getVehicleNo();
        String vehicleType = orderDriverResponse.getVehicleType();

//        orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);

        // 执行抢单动作
        DriverGrabRequest driverGrabRequest = new DriverGrabRequest();
        driverGrabRequest.setOrderId(orderId);
        driverGrabRequest.setDriverId(driverId);
        driverGrabRequest.setCarId(carId);
        driverGrabRequest.setDriverPhone(driverPhone);
        driverGrabRequest.setLicenseId(licenseId);
        driverGrabRequest.setVehicleNo(vehicleNo);
        driverGrabRequest.setVehicleType(vehicleType);
        //设置强单时司机的经纬度
        driverGrabRequest.setReceiveOrderCarLatitude(receiveOrderCarLatitude);
        driverGrabRequest.setReceiveOrderCarLongitude(receiveOrderCarLongitude);

        ResponseResult responseResult = serviceOrderClient.driverGrab(driverGrabRequest);

        if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()){
            return ResponseResult.fail(CommonStatusEnum.ORDER_UPDATE_ERROR.getCode(),CommonStatusEnum.ORDER_UPDATE_ERROR.getMessage());
        }
        // 修改司机的工作状态

        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverId);
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_SERVING);

        responseResult = serviceDriverUserClient.changeWorkStatus(driverUserWorkStatus);
        if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_STATUS_UPDATE_ERROR.getCode(),CommonStatusEnum.DRIVER_STATUS_UPDATE_ERROR.getMessage());
        }

        return ResponseResult.success();
    }

}
