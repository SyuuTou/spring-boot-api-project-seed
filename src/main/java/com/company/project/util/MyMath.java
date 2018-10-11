package com.company.project.util;

import java.math.BigDecimal;

public class MyMath {
	public static double round(double num, int scale) {
		return new BigDecimal(num).divide(new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static void main(String[] args) {
		MyMath.round(3,1);

	}
}
