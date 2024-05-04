package com.mashibing.apipassenger.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/** 
* @Description: 自定义日期校验器
* @Param: 
* @return: 
* @Author: JiLaiYa
* @Date: 2024/4/7
*/

public class DateTimeRangeValidator implements ConstraintValidator<DateTimeRange,Object> {

    private DateTimeRange dateTimeRange;

    private String judge;

    @Override
    public void initialize(DateTimeRange constraintAnnotation) {
        judge = constraintAnnotation.judge();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object paramDate, ConstraintValidatorContext constraintValidatorContext) {
        // 用户传进来的日期参数
        LocalDateTime dateValue = null;

        if (paramDate == null){
            return true;
        }

        if (paramDate instanceof LocalDateTime){
            dateValue = (LocalDateTime)paramDate;
        }

        if (paramDate instanceof String){
            dateValue = LocalDateTime.parse((String)paramDate, DateTimeFormatter.ofPattern(dateTimeRange.pattern()));
        }

        LocalDateTime now = LocalDateTime.now();

        if(dateValue.isAfter(now)){
            if(judge.equals(DateTimeRange.IS_AFTER)  && dateValue.isAfter(now)){
                return true;
            }
            if(judge.equals(DateTimeRange.IS_BEFORE)  && dateValue.isBefore(now)){
                return true;
            }
        }
        return false;
    }


}