package com.mashibing.internalcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-15 09:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseToken {
    private String accessToken;
    private String refreshToken;
}
