package com.mashibing.internalcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-15 23:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResult {
    private String phone;
    private String identity;
    private String type;
    private String time;
}
