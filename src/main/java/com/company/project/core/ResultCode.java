package com.company.project.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(200, "SUCCESS"),//成功
    FAIL(400, "FAILED"),//失败
    UNAUTHORIZED(401, "签名认证失败"),//未认证（签名错误）
    NOT_FOUND(404, "接口不存在"),//接口不存在
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");//服务器内部错误

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
