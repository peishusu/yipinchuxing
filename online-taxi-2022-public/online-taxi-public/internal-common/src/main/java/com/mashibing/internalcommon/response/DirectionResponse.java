package com.mashibing.internalcommon.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-17 11:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionResponse {
    private Integer distance;//距离
    private Integer duration;//时间
}
