package com.company.project.core;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
public class ServiceException extends RuntimeException {

    /**
     * 关联异常枚举
     */
    private ExceptionEnums exceptionEnums;
    /**
     * 产生异常时候的提示数据
     */
    private Object data;

    public ServiceException() {
    }

    public ServiceException(ExceptionEnums exceptionEnums, Object data) {
        this.exceptionEnums = exceptionEnums;
        this.data = data;
    }

    public ServiceException(ExceptionEnums exceptionEnums) {
        this.exceptionEnums = exceptionEnums;
    }

    public ExceptionEnums getExceptionEnums() {
        return exceptionEnums;
    }


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
