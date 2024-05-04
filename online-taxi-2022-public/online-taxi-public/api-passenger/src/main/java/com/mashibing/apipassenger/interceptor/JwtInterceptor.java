package com.mashibing.apipassenger.interceptor;

import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.mashibing.internalcommon.utils.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: online-taxi-public
 * @description: token拦截器
 * @author: lydms
 * @create: 2024-03-16 09:28
 **/
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String resultString = "";
        boolean result = true;

        //客户端传送过来的token保存在headers里面的authouriation
        //解析客户端传送过来的token
        String accessToken = request.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);

        //到这里代表执行没有错误,
        if (tokenResult == null) {
            resultString = "token invalid";
            result = false;
        } else {
            //判断客户端传送过来的token在服务器端的redis中是否存在
            //生成对应的redis-tokenKey
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
            //去redis中查询是否存在的对应token
            String tokenFromRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenFromRedis) || !accessToken.trim().equals(tokenFromRedis.trim())) {
                resultString = "token invalid";
                result = false;
            }
        }

        if (!result) {
            response.getWriter().print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;

    }
}
