package com.company.project.core;

/**
 * 响应结果生成工具
 */
@Deprecated
public class ResultGenerator {

    public static Result genSuccessResult() {
        return new Result(ResultCode.SUCCESS);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result(ResultCode.SUCCESS,data);
    }

//    public static Result genFailResult(String message) {
//        return new Result()
//                .setCode(ResultCode.FAIL)
//                .setMessage(message);
//    }
}
