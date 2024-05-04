package com.mashibing.serviceprice.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PriceRuleIsNewRequest;
import com.mashibing.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-22
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    private PriceRuleService priceRuleService;


    /**
     * @Description: 添加计价规则
     * @Param: [priceRule]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/22
     */

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule) {
        return priceRuleService.add(priceRule);
    }

    /**
     * @Description: 编辑计价规则
     * @Param: [priceRule]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/23
     */

    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule) {
        return priceRuleService.edit(priceRule);
    }

    /**
     * @Description:查询最新的计价规则版本
     * @Param: [fareType] 计价规则
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/23
     */

    @GetMapping("/get-newest-version")
    public ResponseResult getNewestVersion(@RequestParam String fareType) {
        return priceRuleService.getNewestVersion(fareType);
    }


    /**
     * @Description: 判断是否为最新的计价规则版本
     * @Param: [fareType, fareVersion]
     * @return: com.mashibing.internalcommon.dto.ResponseResult
     * @Author: JiLaiYa
     * @Date: 2024/3/23
     */

    @PostMapping("/is-new")
    public ResponseResult<Boolean> IsNewestVersion(@RequestBody PriceRuleIsNewRequest priceRuleIsNewRequest) {
        return priceRuleService.IsNewestVersion(priceRuleIsNewRequest.getFareType(), priceRuleIsNewRequest.getFareVersion());
    }


    /**
     * @Description: 根据城市编码、车辆类型判断是否存在对应的计价规则
     * @Param: [cityCode, vehicleType]
     * @return: com.mashibing.internalcommon.dto.ResponseResult<java.lang.Boolean>
     * @Author: JiLaiYa
     * @Date: 2024/4/22
     */
    @GetMapping("/if-exists/{cityCode}/{vehicleType}")
    @SentinelResource("QueryPriceRule")
    public ResponseResult<Boolean> ifExists(@PathVariable("cityCode") String cityCode, @PathVariable("vehicleType") String vehicleType) {
        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);
        return priceRuleService.ifExists(priceRule);
    }

}
