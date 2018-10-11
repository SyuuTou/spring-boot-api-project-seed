package com.company.project.util;

import java.text.NumberFormat;

/**
 * @author wds
 * 使用API格式化输出百分比
 */
public class PrintPercent {

    public static void print(double num1, double num2) {
        if (Double.compare(num2, 0) == 0)
            throw new RuntimeException("输入有误");

        double ratio = num1 / num2;
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);//设置保留几位小数
        System.out.println("百分比为：" + format.format(0.0100));
    }

    public static void main(String[] args) {
        int num1 = 3;
        int num2 = 11;
        PrintPercent.print(num1, num2);
    }


}