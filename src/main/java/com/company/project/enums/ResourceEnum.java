package com.company.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * oss资源上传枚举
 */
@Getter
@AllArgsConstructor
public enum ResourceEnum {

    IMAGE(0,"image","图片"),
    VIDEO(1,"video","视频"),
    AUDIO(2,"audio","音频")
    ;

    /**
     * 编号
     */
    private int code;
    /**
     * 存放路径
     */
    private String dir;
    /**
     * 描述
     */
    private String msg;

    /**
     * 根据code查询枚举对象
     * @param code 状态码
     * @return
     */
    public static ResourceEnum valueOfCode(Integer code){
        if (StringUtils.isBlank(String.valueOf(code))) {
            return null;
        }
        for (ResourceEnum enumObj : values()) {
            if (code.equals(enumObj.getCode())) {
                return enumObj;
            }
        }
        return null;
    }
}
