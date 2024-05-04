package com.mashibing.internalcommon.utils;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-16 11:04
 **/
public class RedisPrefixUtils {
    //验证码前缀
    private final static String verificationCodePrefix = "verification-code-";


    //token前缀
    private final static String tokenPrefix = "token-";

    //黑名单设备号前缀
    private final static String blackDeviceCodePrefix = "black-device-";

    /** 
    * @Description: 生成保存到redis的验证码的key
    * @Param: [passengerPhone]
    * @return: java.lang.String
    * @Author: JiLaiYa
    * @Date: 2024/3/16
    */
    
    public static String generateKeyByPhone(String phone,String identity){
        return verificationCodePrefix + identity + "-" +phone;
    }


    /** 
    * @Description: 生成保存到redis中的token的key
    * @Param: [phone, identity, tokenType]
    * @return: java.lang.String
    * @Author: JiLaiYa
    * @Date: 2024/3/16
    */
    
    public static String generateToken(String phone,String identity,String tokenType){
        return tokenPrefix + phone + "-" + identity + "-" + tokenType;
    }

    public static String generateBlackDeviceCodePrefix(String deviceCode){
        return blackDeviceCodePrefix + deviceCode;
    }
}
