package com.gaoan.forever.model;

import java.util.HashMap;

/**
 * 用户接口错误代码，oauth错误代码，其他通用错误代码
 * 
 * @author Kevin Jiang
 *
 */
public enum StatusCode {

	/* 通用错误 */
	COMMON_ERROR_CODE("common_code", -1, "FAIL", "操作失败"),

	;

	private String belong;
	private int code;
	private String msgEn;
	private String msgCn;

	// 构造枚举值，比如RED(255,0,0)
	private StatusCode(String own, int code, String msgEn, String msgCn) {
		this.belong = own;
		this.code = code;
		this.msgEn = msgEn;
		this.msgCn = msgCn;
		Constants.mapEn.put(this.belong + "_" + code, msgEn);
		Constants.mapCn.put(this.belong + "_" + code, msgCn);
	}

	public String toString() {// 自定义的public方法
		return super.toString() + "(" + code + "," + msgEn + "," + msgCn + ")";
	}

	public static String getMsgCn(String belong, String code) {
		return Constants.mapCn.get(belong + "_" + code);
	}

	public static String getMsgEn(String belong, String code) {
		return Constants.mapEn.get(belong + "_" + code);
	}

	public String getBelong() {
		return belong;
	}

	public int getCode() {
		return code;
	}

	public String getMsgEn() {
		return msgEn;
	}

	public String getMsgCn() {
		return msgCn;
	}

	public static class Constants {

		public static HashMap<String, String> mapCn = new HashMap<String, String>();
		public static HashMap<String, String> mapEn = new HashMap<String, String>();

	}

}
