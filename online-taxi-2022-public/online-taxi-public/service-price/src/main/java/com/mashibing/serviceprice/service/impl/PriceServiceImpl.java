package com.mashibing.serviceprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import com.mashibing.serviceprice.remote.ServiceMapClient;
import com.mashibing.serviceprice.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mashibing.internalcommon.utils.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.List;


/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 10:31
 **/
@Service
@Slf4j
public class PriceServiceImpl implements PriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    @Override
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,String cityCode,String vehicleType) {
        log.info("出发第纬度:" + depLongitude);
        log.info("出发第经度:" + depLatitude);
        log.info("目的第纬度:" + destLongitude);
        log.info("目的第经度:" + destLatitude);
        log.info("cityCode:" + cityCode);
        log.info("vehicleType:" + vehicleType);
        //调用地图服务来获取距离、时间
        log.info("调用service-map地图服务");
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        //获得距离、时间
        ResponseResult<DirectionResponse> responseResult = serviceMapClient.driving(forecastPriceDTO);
        if (responseResult != null) {
            //计算的素材
            Integer distance = responseResult.getData().getDistance();
            Integer duration = responseResult.getData().getDuration();
            log.info("计算结果信息");
            //根据最新版本的计价规则来预估价格
            QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("city_code",cityCode).eq("vehicle_type",vehicleType).orderByDesc("fare_version");
            List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
            //当前计价规则不存在的话，返回fail
            if (priceRules.size() == 0) {
                return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
            }
            //计价的规则
            PriceRule priceRule = priceRules.get(0);
            log.info("当前采用的计价规则:" + priceRule.toString());

            //计算最终预估打车的价格
            Double price = getPrice(distance, duration, priceRule);


            ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse(price,cityCode,vehicleType,priceRule.getFareType(),priceRule.getFareVersion());
            //返回结果
            return ResponseResult.success(forecastPriceResponse);
        } else {
            return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), CommonStatusEnum.FAIL.getMessage());
        }


    }

    @Override
    public ResponseResult calculatePrice(Integer distance, Integer duration, String cityCode, String vehicleType) {
        //查询最新的计价规则
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode).eq("vehicle_type",vehicleType).orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        //当前计价规则不存在的话，返回fail
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
        }
        PriceRule priceRule = priceRules.get(0);
        log.info("当前采用的计价规则:" + priceRule.toString());
        Double price = getPrice(distance, duration, priceRule);



        return ResponseResult.success(price);
    }

    /**
     * @Description: 根据距离、时长、计价规则得出最终打车的费用,这个方法有待优化，妈的懒得优化了
     * * @Param: [distance, duration, priceRule]
     * @return: java.lang.Double
     * @Author: JiLaiYa
     * @Date: 2024/3/18
     */

    public Double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        double price = 0;

        // 起步价
        double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price,startFare);

        // 里程费
        // 总里程 m
        double distanceMile = BigDecimalUtils.divide(distance,1000);
        // 起步里程
        double startMile = (double)priceRule.getStartMile();
        double distanceSubtract = BigDecimalUtils.substract(distanceMile,startMile);
        // 最终收费的里程数 km
        double mile = distanceSubtract<0?0:distanceSubtract;
        // 计程单价 元/km
        double unitPricePerMile = priceRule.getUnitPricePerMile();
        // 里程价格
        double mileFare = BigDecimalUtils.multiply(mile,unitPricePerMile);
        price = BigDecimalUtils.add(price,mileFare);

        // 时长费
        // 时长的分钟数
        double timeMinute = BigDecimalUtils.divide(duration,60);
        // 计时单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();

        // 时长费用
        double timeFare = BigDecimalUtils.multiply(timeMinute,unitPricePerMinute);
        price = BigDecimalUtils.add(price,timeFare);

        BigDecimal priceBigDecimal = new BigDecimal(price);
        priceBigDecimal = priceBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);

        return priceBigDecimal.doubleValue();

    }
}
