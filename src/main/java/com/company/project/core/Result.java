package com.company.project.core;

import com.alibaba.fastjson.JSON;
import lombok.*;

/**
 * 统一API响应结果封装
 * 公共的接口响应
 */
@Data
@AllArgsConstructor
//@Builder
//@Setter
//@Getter
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result() {
    }

    /**
     * 公共返回结果
     * @param resultCode
     */
    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    private Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    private Result(ServiceException serviceEx) {
        this.code = serviceEx.getExceptionEnums().getCode();
        this.message = serviceEx.getExceptionEnums().getMessage();
    }

    private Result(ServiceException serviceEx, T data) {
        this.code = serviceEx.getExceptionEnums().getCode();
        this.message = serviceEx.getExceptionEnums().getMessage();

        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    //响应结果生成方法1,成功
    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    //响应结果生成方法2，成功代码&数据
    public static <T> Result<T> success(T data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    //响应结果生成方法3，失败返回业务异常代码
    public static Result failure(ServiceException ex) {
        return new Result(ex);
    }
    //响应结果生成方法3，失败返回业务异常代码以及相应的错误数据
    public static <T> Result failure(ServiceException ex, T data) {
        return new Result(ex, data);
    }
}
