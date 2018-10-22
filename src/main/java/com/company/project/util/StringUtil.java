package com.company.project.util;

import com.company.project.core.ExceptionEnums;
import com.company.project.core.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringUtil {
    /**
     * 用户msg的存储方式
     *
     * @param phone 手机号
     * @return msg存储key
     */
    public static String formatMsgKey(String phone) {
        return "user_" + phone + "_msg";
    }

    /**
     * 手机的登录次数格式限制
     * @param phone 用户手机号
     * @return
     */
    public static String formatPhoneMsgLimitKey(String phone) {
        return "phone_" + phone + "_msg_limit";
    }
    public static String formatIpMsgLimitKey(String ip) {
        return "ip_" + ip + "_msg_limit";
    }

    /**
     * 滑块随机码的key生成
     *
     * @param phone 手机号
     * @return msg存储key
     */
    public static String formatRandomKey(String phone) {
        return "user_" + phone + "_random";
    }

    /**
     * 用户token的存储方式
     *
     * @param phone 手机号
     * @return token存储key
     */
    public static String formatTokenKey(String phone) {
        return "user_" + phone + "_token";
    }

    /**
     * randomKey解析为手机号
     *
     * @param randomKey
     * @return
     */
    public static String parseRandomKey(String randomKey) {
        return randomKey.substring(randomKey.indexOf("_") + 1, randomKey.lastIndexOf("_"));
    }

    public static String parseMsgKey(String msgKey) {
        return msgKey.substring(msgKey.indexOf("_") + 1, msgKey.lastIndexOf("_"));
    }

    public static String parseTokenKey(String tokenKey) {
        return tokenKey.substring(tokenKey.indexOf("_") + 1, tokenKey.lastIndexOf("_"));
    }

    /**
     * 异常转换为json格式
     *
     * @param se
     * @return
     */
    public static String buildExceptionJSON(ServiceException se) {
        ExceptionEnums aenum = se.getExceptionEnums();
        return "{'code':" + aenum.getCode() + ",'message':'" + aenum.getMessage() + "'}";
    }

    /**
     * 字符串通过逗号拆分，取得首尾字符并通过"-"连接
     *
     * @param string      拆分的字符串
     * @param splitSignal 分隔符号
     * @return
     */
    public static String formatStringByComma(String string, String splitSignal) {
        if (StringUtils.isBlank(string)) {
            return string;
        }

        String[] split = string.split(splitSignal);
        if (split.length == 1) {
            return string;
        }
        return split[0] + "-" + split[split.length - 1];

    }

    /**
     * 空格拼接list
     *
     * @param list
     * @param regex
     * @return
     */
    public static String concatStringsWithRegex(List<String> list, String regex) {
        StringBuilder sb = new StringBuilder();
        if (list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    sb.append(list.get(i));
                } else {
                    sb.append(list.get(i) + regex);
                }

            }

        }
        return sb.toString();

    }

    /**
     * 进行首字母大写处理
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    public static String initcap(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void main(String[] args) {

        String s = formatMsgKey("124");
        String s1 = formatTokenKey("132");

        String s2 = parseMsgKey("user_124_msg");
        String s3 = parseTokenKey("user_132_token");

        System.err.println(s);
        System.err.println(s1);
        System.err.println(s2);
        System.err.println(s3);

        System.err.println((int) (Math.random() * 100));
        System.err.println(Math.floor(2.3));

        int tmp;
        int index = ((tmp = new Random().nextInt(100)) == 1 ? tmp + 1 : tmp);
        System.err.println(index);

        List<String> strings=new ArrayList<>();
        strings.add("q");
        strings.add("w");
        strings.add("e");
        String s4 = concatStringsWithRegex(strings, " ");
        System.err.println(s4);


    }
}
