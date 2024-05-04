package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-16 21:04
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseResult getUser(HttpServletRequest request){
        //获取accessToken
        String accessToken = request.getHeader("Authorization");
        //
        return userService.getUserByToken(accessToken);
    }
}
