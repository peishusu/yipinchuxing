package com.mashibing.apidriver.config;

import com.mashibing.apidriver.interceptor.JwtInterceptor;
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
                excludePathPatterns("/noAuth").excludePathPatterns("/verification-code").excludePathPatterns("/verification-code-check");
    }
}
