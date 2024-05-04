package com.mashibing.serviceprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashibing.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JiLaiYa
 * @since 2024-03-22
 */
@Service
public class PriceRuleServiceImpl implements PriceRuleService {
    @Autowired
    private PriceRuleMapper priceRuleMapper;

    @Override
    public ResponseResult add(PriceRule priceRule) {
        //拼接fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;
        priceRule.setFareType(fareType);
        //添加版本号
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode).eq("vehicle_type", vehicleType).orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion = 0;
        if (priceRules.size() > 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_EXISTS.getMessage());

        }
        priceRule.setFareVersion(++fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("");

    }

    @Override
    public ResponseResult edit(PriceRule priceRule) {
        //拼接fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;
        priceRule.setFareType(fareType);
        //添加版本号
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode).eq("vehicle_type", vehicleType).orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion = 0;
        //判断计价规则是否存在变化
        if (priceRules.size() > 0) {
            PriceRule latestPriceRule = priceRules.get(0);
            fareVersion = latestPriceRule.getFareVersion();
            Double startFare = latestPriceRule.getStartFare();
            Integer startMile = latestPriceRule.getStartMile();
            Double unitPricePerMile = latestPriceRule.getUnitPricePerMile();
            Double unitPricePerMinute = latestPriceRule.getUnitPricePerMinute();
            if (startFare.equals(priceRule.getStartFare()) && startMile.equals(priceRule.getStartMile())
                    && unitPricePerMile.equals(priceRule.getUnitPricePerMile()) && unitPricePerMinute.equals(priceRule.getUnitPricePerMinute())) {
                return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EDIT.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EDIT.getMessage());

            }

        }
        priceRule.setFareVersion(++fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("");
    }

    @Override
    public ResponseResult<PriceRule> getNewestVersion(String fareType) {
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fare_type", fareType).orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if (priceRules.size() != 0) {
            return ResponseResult.success(priceRules.get(0));
        } else {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
        }
    }

    @Override
    public ResponseResult<Boolean> IsNewestVersion(String fareType, Integer fareVersion) {
        ResponseResult<PriceRule> newestVersion = getNewestVersion(fareType);
        if (newestVersion.getCode() == CommonStatusEnum.PRICE_RULE_EMPTY.getCode()) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
//            return ResponseResult.success(false);//说明当前传送过来的计价规则是不存在的

        } else {
            PriceRule priceRule = newestVersion.getData();
            Integer fareVersionDB = priceRule.getFareVersion();
            if (fareVersionDB > fareVersion){
                return ResponseResult.success(false);//说明当前传送过来的计价规则不是最新的
            }
            return ResponseResult.success(true);
        }

    }

    @Override
    public ResponseResult<Boolean> ifExists(PriceRule priceRule) {
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",priceRule.getCityCode()).eq("vehicle_type",priceRule.getVehicleType()).orderByDesc("fare_version");
        Integer count = priceRuleMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseResult.success(true);
        }else {
            return ResponseResult.success(false);
        }

    }
}
