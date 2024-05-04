package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-25 14:29
 **/
@Data
public class PriceRuleIsNewRequest {
    private String fareType;
    private Integer fareVersion;
}
