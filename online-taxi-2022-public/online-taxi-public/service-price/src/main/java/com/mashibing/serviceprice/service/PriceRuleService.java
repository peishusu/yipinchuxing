package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface PriceRuleService {
    public ResponseResult add(PriceRule priceRule);

    public ResponseResult edit(PriceRule priceRule);

    ResponseResult getNewestVersion(String fareType);

    ResponseResult<Boolean> IsNewestVersion(String fareType, Integer fareVersion);

    ResponseResult<Boolean> ifExists(PriceRule priceRule);
}
