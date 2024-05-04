package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-20 15:16
 **/
@Data
public class DriverUserExistsResponse {
    private String driverPhone;

    private Integer ifExists;
}
