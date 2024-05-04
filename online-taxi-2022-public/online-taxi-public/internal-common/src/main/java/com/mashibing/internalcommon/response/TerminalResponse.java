package com.mashibing.internalcommon.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 18:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalResponse {
    private String tid;

    private Long carId;

    private String longitude;
    private String latitude;
}
