package com.mashibing.internalcommon.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 09:47
 **/
// todo 以后这个类不在作为公共类了
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeDTO {
    private String passengerPhone;

    private String verificationCode;

    private String driverPhone;
}
