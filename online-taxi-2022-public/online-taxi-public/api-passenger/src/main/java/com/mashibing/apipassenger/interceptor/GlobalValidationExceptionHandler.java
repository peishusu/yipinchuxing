package com.mashibing.apipassenger.interceptor;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @program: online-taxi-public
 * @description: 全局异常处理器
 * @author: lydms
 * @create: 2024-04-05 15:25
 **/
@RestControllerAdvice
@Order(1)
public class GlobalValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult validationExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseResult.fail(CommonStatusEnum.VALIDATION_EXCEPTION.getCode(),e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult ConstraintValidationExceptionHandler(ConstraintViolationException e) {
        System.out.println(e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String message = "";
        for (ConstraintViolation c: constraintViolations ) {
            message = c.getMessage();
        }
        return ResponseResult.fail(CommonStatusEnum.VALIDATION_EXCEPTION.getCode(),message);
    }


}
