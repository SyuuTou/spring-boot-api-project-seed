package com.company.project.enums;

import com.company.project.core.ExceptionEnums;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 异常枚举
 */
@AllArgsConstructor
public enum ExceptionEnum implements ExceptionEnums {

    //用户相关
    USER_NOT_EXITS(1000,"用户不存在"),
    USER_REGISTOR_FAIL(1001,"用户注册失败"),
    USER_UNLOGIN(1002, "用户未登录"),

    //用户授权
    AUTHORIZATION_ERROR(2001,"授权信息格式不正确"),
    AUTHORIZATION_EXPIRE(2002,"token已经过期"),
    AUTHORIZATION_INVALIDATION(2003,"token无效"),

    FILE_UPLOAD_ERROR(8003,"文件上传失败"),
    ;

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 根据code查询枚举对象
     * @param code 状态码
     * @return
     */
    public static ExceptionEnum valueOfCode(Integer code){
        if (StringUtils.isBlank(String.valueOf(code))) {
            return null;
        }
        for (ExceptionEnum enumObj : values()) {
            if (code.equals(enumObj.getCode())) {
                return enumObj;
            }
        }
        return null;
    }

}
