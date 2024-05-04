package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.*;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> getUserByPhone(@PathVariable("driverPhone") String driverPhone);

    /** 
    * @Description: 
    * @Param: [carId]
    * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.dto.Car>
    * @Author: JiLaiYa
    * @Date: 2024/3/22
    */
    
    @GetMapping("/car")
    public ResponseResult<Car> getCarById(@RequestParam("carId") Long carId);



    /** 
    * @Description: 司机出车
    * @Param: [driverUserWorkStatus]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/29
    */
    
    @PostMapping("/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus);



    /**
     * @Description: 根据driverPhone查询司机和车辆的绑定关系
     * @Param: [driverPhone]
     * @return: com.mashibing.internalcommon.dto.ResponseResult<com.mashibing.internalcommon.dto.DriverCarBindingRelationship>
     * @Author: JiLaiYa
     * @Date: 2024/3/29
     */

    @GetMapping("/driver-car-binding-relationship/searchByDriverPhone")
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(@RequestParam String driverPhone);


    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId);

    @GetMapping("/work-status")
    public ResponseResult<DriverUserWorkStatus> getWorkStatus(@RequestParam Long driverId);



}
