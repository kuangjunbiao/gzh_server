package com.gaoan.forever.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 签名验签方案
 *
 * @author longshengtang
 * @Date 20170809
 */
@Component
public class DSASignUtil {

	private static final Logger logger = LoggerFactory.getLogger(DSASignUtil.class);

	private Map<String, String> publicKeyMap = new ConcurrentHashMap<>();

	{// 这些数据可以从数据库中获取
		// 安全锁公钥
		publicKeyMap.put("1011",
				"MIIBuDCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBAPmDTBNqiyK3oFXYGhucY70lEmDzXQn4fmmrzY9hU5Lf30nEaXIrcR0WiBggju69KBMKHi/wqJvXG9Akh7oZVVoWyZipky4Bg4pTvwJS82PMCi47L3V2PGLRK1Um8W/xYsVb7FTx+XYHq5BGw9zPONFnLtHwQ3isp83Bbm8W6MOI");// 存放安全中心公钥
		// 安全锁公钥
		publicKeyMap.put("2000",
				"MIIBuDCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBALSD9efn3/CYvdDyFigunw7A7mbRAlA+M9PBmmf+XMBgVmnC1+IUDUHyLKaYnQmZY8Dtxl06Dia/8Og21bAmWPWW4wge1a8PhbfQtVR/PK3gHobBZTXW+bw/kVpQjK1E+Dbaaemu3hDwezBXAoORXGqYk1EQKX1dkA7uD6dvWC3C");// 存放安全中心公钥
		// 互助公钥
		publicKeyMap.put("1004",
				"MIIBtzCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYQAAoGAaf7ucf9qS0UIZsAd2UUiMxq4s6OwGSRXJvzBHZT8qx7Cz0dBXkLeDQjzQcS2y4h7ZVa9BbSnkAiLXsVOA7z2hmP+2taafl4ySkKutzlJvRtRQNAWO4V4zQsRyye3c8Ix5zVWAMCinUcBerE1J3yg8SJuBGUMbPFB+DNBAlgOAqo=");
	}

	@Value("${verifySign:true}")
	private boolean verifySign = true;

	// 我的私钥
	private String privateKey = "MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUW2blikY76paSsmVdxwKXqofI3Bo=";

	// 安全锁私钥
	// private String privateKey =
	// "MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUfKxUYGAKgZFZK91s6IxNyb6HKOE=";
	/**
	 * 签名，参数是JavaBean，自动转换为key1=value1&key2=value2...
	 *
	 * @param dataObj
	 *            待加签对象
	 * @return 成功则返回加签后的字符串，反之返回null
	 */
	public String sign(Object dataObj) {
		String dataStr = getDataToSign(dataObj);
		System.out.println("签名前字符串：" + dataStr);
		if (StringUtils.isEmpty(dataStr)) {
			logger.error("sgin-dataStr为空dataObj=" + dataObj);
			return null;
		}

		try {
			return DSACoder.sign(dataStr, privateKey);
		} catch (Throwable e) {
			logger.error("签名异常：", e);
			return null;
		}
	}

	/**
	 * 参数fieldToBeSigned需要包含sign属性，否则验签永远返回false。
	 *
	 * @param target
	 *            验签目标对象
	 * @param systemId
	 *            系统ID
	 * @return
	 */
	public boolean verify(Object target, String systemId) {
		try {
			if (!verifySign) {
				logger.info("关闭验签模式，验签已跳过！");
				return true;
			}
			// 将验签对象字段反射到map对象中
			Map<String, Object> dataMap = convertFieldToMap(target);
			if (dataMap == null) {
				logger.error("验签失败getFieldToMap返回空map，fieldToBeSigned=" + target);
				return false;
			}
			Object signObj = dataMap.get("sign");
			if (StringUtils.isEmpty(signObj)) {
				if (dataMap.containsKey("sign")) {
					logger.error("sign值为空");
					return false;
				}
				logger.error("不存在sign属性");
				return false;
			}
			String sign = signObj.toString();
			if (null == sign) {
				logger.error("sign值为空");
				return false;
			}

			// 将属性值组成的键值对排序
			String dataStr = getSortedDataFromFieldMap(dataMap);
			// System.out.println("拼接后字符串如下：");
			System.out.println("验签前字符串" + dataStr);
			if (StringUtils.isEmpty(dataStr)) {
				logger.error("getSortedDataFromFieldMap返回空");
				return false;
			}
			// 公钥已缓存
			String publicKey = publicKeyMap.get(systemId);
			// 公钥未缓存。
			if (StringUtils.isEmpty(publicKey)) {
				publicKey = getPublicKeyBySystemId(systemId);
			}
			if (null == publicKey) {
				// 公钥不存在。
				logger.info("systemId:" + systemId + "公钥不存在！");
				return false;
			}
			// logger.info("开始验签[publicKey={}]", publicKey);
			return DSACoder.verify(dataStr, publicKey, sign);
		} catch (Throwable e) {
			logger.error("验签异常：{}", getStackTraceAsString(e));
			return false;
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 通过系统ID获取公钥(目前为空实现)
	 *
	 * @param systemId
	 * @return
	 */
	private String getPublicKeyBySystemId(String systemId) {
		return publicKeyMap.get(systemId);
	}

	/**
	 * 得到排序后的字符串
	 *
	 * @param obj
	 * @return
	 */
	public String getDataToSign(Object obj) {
		Map<String, Object> map = convertFieldToMap(obj);
		return getSortedDataFromFieldMap(map);
	}

	/**
	 * 将验签对象字段反射到map对象中
	 *
	 * @param target
	 *            待加签对象
	 * @return 包含所有字段名为key的map，及字段值为key的value；反之null
	 */
	private Map<String, Object> convertFieldToMap(Object target) {
		try {
			Map<String, Object> map = (JSONObject) JSONObject.toJSON(target);
			return map;
		} catch (Throwable e) {
			logger.error("getFieldToMap异常：" + getStackTraceAsString(e));
		}
		return null;
	}

	/**
	 * 将Map组装成待签名数据。 待签名的数据必须按照ASCII 码从小到大排序（字典序）
	 *
	 * @param params
	 * @return
	 */
	public String getSortedDataFromFieldMap(Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			return null;
		}
		StringBuffer content = new StringBuffer();
		// 按照key做排序
		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if ("sign".equals(key) || "sign_type".equals(key)) {
				continue;
			}
			Object value = params.get(key);
			if (StringUtils.isEmpty(value)) {
				continue;
			}
			content.append((content.length() == 0 ? "" : "&") + key + "=" + value);
		}
		return content.toString();
	}

	public static void main(String[] args) {
		testSV();
	}

	private static void testSV() {
		DSASignUtil dsaSignUtils = new DSASignUtil();
		Map<String, Object> map = new HashMap<>();
		map.put("nonce", RandomStringUtils.random(32, true, true));
		map.put("reqSysId", "1004");
		map.put("username", "13641177016@qeveworld.com");
		// map.put("password", "admin");
		String sign = dsaSignUtils.sign(map);
		System.out.println(sign);
		map.put("sign", sign);
		boolean verify = dsaSignUtils.verify(map, "1004");
		System.out.println(verify);
	}
}
