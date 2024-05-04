package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-14 14:47
 **/
@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    /**
    * @Description: 成功响应的方法
    * @Param: [data]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/14
    */

    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage()).setData(data);
    }

    /** 
    * @Description: 设置默认的成功响应方法
    * @Param: []
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/15
    */
    
    public static <T> ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage());
    }

    /**
    * @Description: 自定义失败：错误码、提示信息
    * @Param: [code, message]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/14
    */

    public static ResponseResult fail(int code,String message){
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
    * @Description: 自定义失败：错误码、提示信息、错误信息
    * @Param: [code, message, data]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/14
    */

    public static ResponseResult fail(int code,String message,String data){
        return new ResponseResult().setCode(code).setMessage(message).setData(data);

    }

    /**
    * @Description: 失败：统一的失败
    * @Param: [data]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/14
    */

    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setData(data);
    }
}
