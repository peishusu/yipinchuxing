package com.mashibing.apipassenger.config;

import com.mashibing.apipassenger.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-16 09:44
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**").
                excludePathPatterns("/noAuth","/verification-code","/verification-code-check","/token-refresh","/test-real-time-order/**","/error");
    }
}
