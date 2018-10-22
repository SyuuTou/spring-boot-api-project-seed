package com.company.project.enums.exceptions;

import com.company.project.core.ExceptionEnums;
import org.apache.commons.lang3.StringUtils;

public enum LoginExceptionEnum implements ExceptionEnums {

    //手机格式错误，isBlank(phone)为true或者手机格式错误
    PHONE_FPRMAT_ERROR(100, "手机号码格式错误"),

    //user
    USER_NOT_FOUND(201, "用户不存在"),

    //msg error
    MSG_EXPIRE(300, "短信验证码过期"),
    MSG_ILLEGAL(301, "短信验证码错误"),
    MSG_LIMIT(302, "短信发送频繁"),
    CAPTCHA_ERROR(303, "图片验证码错误"),

    //token error
    TOKEN_NO_CACHE(400, "本地无缓存"),
    TOKEN_EXPIRE(401, "token过期"),
    TOKEN_ILLEGAL(402, "token非法"),

    //token error
    DEVICE_REGISTED(501, "设备已注册");

    private Integer code;
    private String message;

    LoginExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code查询枚举对象
     * @param code code
     * @return LoginExceptionEnum
     */
    public static LoginExceptionEnum valueOfCode(Integer code){
        if (StringUtils.isBlank(String.valueOf(code))) {
            return null;
        }
        for (LoginExceptionEnum enumObj : values()) {
            if (code.equals(enumObj.getCode())) {
                return enumObj;
            }
        }
        return null;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
