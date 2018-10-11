package com.company.project.util;

import java.util.Base64;

/**
 * 定义一个专门进行密码加密的处理程序类，而且所有的密码一定要被循环加密多次
 * @author mldn 
 */
public class PasswordUtil {
	private static final int REPEAT_COUNT = 3 ;	// 加密的次数
	private static final String SALT = "onlygame" ;	// 盐值的种子数
	private PasswordUtil() {}
	/**
	 * 使用Base64的加密算法进行指定字符串的加密处理
	 * @param str 要加密的字符串
	 * @return 加密后的字符串的内容
	 */
	public static String encoderString(String str) {
		byte data [] = str.getBytes() ;
		for (int x = 0 ; x < REPEAT_COUNT ; x ++) {
			data = Base64.getEncoder().encode(data) ;
		}
		return new String(data) ;
	}
	/**
	 * 针对于encoderString()方法加密后的数据进行解密操作
	 * @param str 加密的内容
	 * @return 原始内容
	 */ 
	public static String decoderString(String str) {
		byte data [] = str.getBytes() ;
		for (int x = 0 ; x < REPEAT_COUNT ; x ++) {
			data = Base64.getDecoder().decode(data) ;
		}
		return new String(data) ;
	}
	/** 
	 * base64+盐+md5（不可逆）
	 * @param pwd 原始密码
	 * @return 加密处理后的密码
	 */
	public static String encoder(String pwd) {
		byte data [] = SALT.getBytes() ;
		for (int x = 0 ; x < REPEAT_COUNT ; x ++) {
			data = Base64.getEncoder().encode(data) ;
		}
		String saltPwd = "{" + new String(data) + "}" + pwd;
		for (int x = 0 ; x < REPEAT_COUNT ; x ++) {
			saltPwd = new MD5Code().getMD5ofStr(saltPwd) ;
		}
		return saltPwd ;
	}

	public static void main(String[] args) {
		String encoder = encoderString("1");
		System.err.println(encoder);

		String decode = decoderString(encoder);
		System.err.println(decode);

		String md5 = encoder("10000");
		System.err.println(md5);
	}
}
