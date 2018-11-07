package com.company.project.constant;

/**
 * 常量
 */
public class SysConstant {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "currentUserId";
    /**
     * header授权信息
     */
    public static final String AUTHORIZATION = "authorization";
    /**
     * header签名信息
     */
    public static final String SIGN = "sign";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;
    /**
     * 用户token格式化字符串
     * 第一个占位符表示用户id
     * 第二个占位符表示用户token（即UUID）
     */
    public static final String USER_TOKEN_FORMAT = "%s_%s";

}
