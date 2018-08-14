package com.gaoan.forever.constant;

/**
 * 通用常量
 */
public class ForeverConstant {
	/**
	 * 安全中心动态码交易类型 1：登录
	 */
	public static final int SAFECENTER_API_VERIFICATIONTYPE_LOGIN = 1;

	/**
	 * 安全中心动态码交易类型 2：支付
	 */
	public static final int SAFECENTER_API_VERIFICATIONTYPE_PAY = 2;

	/**
	 * 保留2位小数
	 */
	public static final int VIRTUAL_CURRENCY_DECIMALCOUNT = 2;

	/**
	 * 资源类型
	 * 
	 * @author Administrator
	 *
	 */
	public interface ResourcesStatus {
		public static final Integer MENU = 1;
		public static final Integer OPERATER = 2;
	}

	/**
	 * 用户状态
	 * 
	 * @author Administrator
	 *
	 */
	public interface UserStatus {
		public static final Integer INVALID = 0;
		public static final Integer VALID = 1;
	}

}
