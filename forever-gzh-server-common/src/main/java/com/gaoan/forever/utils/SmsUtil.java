package com.gaoan.forever.utils;

import java.util.Random;

/**
 * 短信验证码生成工具
 * 
 * @author longshengtang
 * @date 20170407
 */
public class SmsUtil {

	/**
	 * 短信验证码长度
	 */
	private static int fixedLen = 6;

	/**
	 * 获取6位足够随机的短信验证码<br>
	 * 为了提高效率，会分成两次生成：第一次生成，第二次补全（即，如果第一次生成不足6位，就补足6位）。
	 * 
	 * @return 返回6位足够随机的验证码
	 */
	public static synchronized String getSmsCode() {
		Random rd = new Random();
		return getSmsCode((rd.nextInt(1000000) + ""), rd, fixedLen);
	}

	/**
	 * 生成固定长度fixedLen的字符串
	 * 
	 * @param source
	 *            已经拥有的验证码
	 * @param rd
	 *            产生随机数对象
	 * @param fixedLen
	 *            需要生成的长度
	 * @return
	 */
	public static String getSmsCode(String source, Random rd, int fixedLen) {
		int remain = fixedLen;

		if (source != null) {
			remain = remain - source.length();
			if (source.length() >= fixedLen) {
				return source;
			}
		}
		String target = source;
		for (int i = 0; i < remain; i++) {
			target = target + rd.nextInt(10);
		}

		if (target.length() != fixedLen) {
			throw new RuntimeException("生成6位验证码错误，位数不是6位！");
		}

		return target;
	}

//	public static void main(String[] args) {
//		for (int i = 0; i < 1000000; i++)
//			System.out.println(getSmsCode());
//	}

}