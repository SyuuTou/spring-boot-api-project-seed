package com.company.project.core;

import com.alibaba.fastjson.JSON;
import lombok.*;

/**
 * 统一API响应结果封装
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
@Setter
@Getter
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    private Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    //响应结果生成方法
    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result genFailResult() {
        return new Result(ResultCode.FAIL);
    }
}
