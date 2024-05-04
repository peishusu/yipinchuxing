package com.mashibing.apipassenger.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @program: online-taxi-public
 * @description: 校验验证码
 * @author: lydms
 * @create: 2024-03-14 09:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckVerificationCodeDTO {

    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$",message = "请输入正确格式的手机号")
    @NotBlank(message = "手机号不能为空")
    private String passengerPhone;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$",message = "请填写6位数字的验证码")
    private String verificationCode;

}
