package com.mashibing.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.TokenResult;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.print.DocFlavor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-15 21:05
 **/
public class JwtUtils {

    //设置服务器端的 盐 ->加密码
    private final static String SIGN = "peishusu@JiLaiYa";

    private final static String JWT_KEY_PHONE = "phone";

    //1：乘客 2：司机
    private final static String JWT_KEY_IDENTITY = "identity";

    private final static String JWT_TOKEN_TYPE = "tokenType";

    private final static String JWT_TOKEN_TIME = "tokenTime";

    /**
     * @Description: 生成token
     * @Param: []
     * @return: java.lang.String
     * @Author: JiLaiYa
     * @Date: 2024/3/15
     */

    public static String generateToken(String phone, String identity,String tokenType) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, phone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        map.put(JWT_TOKEN_TIME,Calendar.getInstance().getTime().toString());
        //将要设置的字段放入Jwt中
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        //设置token的过期时间，token的有效期交给服务器端进行控制
//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.DATE, 1);
//        Date time = instance.getTime();
//        builder.withExpiresAt(time);
        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }


    /**
     * @Description: 解析token
     * @Param: [token]
     * @return: java.lang.String
     * @Author: JiLaiYa
     * @Date: 2024/3/15
     */
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Claim phone = verify.getClaim(JWT_KEY_PHONE);
        Claim identity = verify.getClaim(JWT_KEY_IDENTITY);
        Claim type = verify.getClaim(JWT_TOKEN_TYPE);
        Claim time = verify.getClaim(JWT_TOKEN_TIME);

        TokenResult tokenResult = new TokenResult(phone.asString(), identity.asString(), type.asString(),time.asString());
        return tokenResult;
    }


    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try {
//            System.out.println(JWT);
            tokenResult = JwtUtils.parseToken(token);
        } catch (Exception e) {
           //不用提示
        }
        return tokenResult;
    }


    public static void main(String[] args) {
        //测试
        String s = generateToken("19826333069","0", TokenConstants.ACCESS_TOKEN_TYPE);
        System.out.println(s);

        System.out.println("----------------解析后");
        TokenResult tokenResult = parseToken(s);
        System.out.println(tokenResult.getPhone());
        System.out.println(tokenResult.getIdentity());
    }
}
