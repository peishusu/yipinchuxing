package com.mashibing.apipassenger.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @program: online-taxi-public
 * @description: 发送验证码
 * @author: lydms
 * @create: 2024-03-14 09:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendVerificationCodeDTO {

    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$",message = "请输入正确格式的手机号")
    @NotBlank(message = "手机号不能为空")
    private String passengerPhone;
}
