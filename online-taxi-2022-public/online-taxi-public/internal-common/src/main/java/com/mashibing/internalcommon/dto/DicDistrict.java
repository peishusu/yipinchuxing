package com.mashibing.internalcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-18 11:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DicDistrict {
    private String addressCode;
    private String addressName;
    private String parentAddressCode;
    private Integer level;
}
