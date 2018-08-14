package com.gaoan.forever.utils.encryption;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

/**
 * 接口调用签名、验签
 * Created by one on 2017/4/25.
 */
public class InterfaceSignUtils {

	private static final Logger logger = Logger.getLogger(InterfaceSignUtils.class);

	/**私钥*/
//	private static final String PRIVATE_KEY = "123456";

	/**
	 * 以传输参数加私钥生成签名，返回含签名的url串
	 * @param map 传输参数
	 * @return 签名
	 */
	public static String getSignUrl(Map<String, String> map, boolean onlySign, String privateKey) {
		String stringA = InterfaceSignUtils.getSignData(map);
		logger.info("待签名参数stringA: " + stringA);

		String stringSignTemp = stringA + "&key=" + privateKey;
		String sign = EncryptUtils.encryptMD5(stringSignTemp).toUpperCase();
		logger.info("签名sign: " + sign);
		if(onlySign) {
			return sign;
		}
		return "sign=" + sign + "&" + stringA;
	}

	/**
	 * 验签
	 * @param url 参数URL
	 * @return 验签结果
	 */
	public static boolean checkSign(String url, String privateKey) {
		//获取各个参数
		String[] params = url.split("&");
		Map<String, String> map = new HashMap<>();
		//存放每个参数键与值
		String[] param;
		//url中的签名
		String sign = null;
		for(int i=0; i<params.length; i++) {
			param = params[i].split("=");
			//签名不参与运算
			if(param[0].equals("sign")) {
				sign = param[1];
				continue;
			}
			//空值参数不参与运算
			if(param.length < 2) {
				continue;
			}

			map.put(param[0], param[1]);
		}

		//根据参数与私钥生成签名
		String checkSgin = InterfaceSignUtils.getSignUrl(map, true, privateKey);
		//比较签名是否一致
		return checkSgin.equals(sign);
	}

	/**
	 * 将Map组装成待签名数据。
	 * 待签名的数据必须按照ASCII 码从小到大排序（字典序）
	 * @param params
	 * @return
	 */
	public static String getSignData(Map<String, String> params) {
		StringBuffer content = new StringBuffer();
		// 按照key做排序
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if ("sign".equals(key)||"sign_type".equals(key) ) {
				continue;
			}
			String value = params.get(key);
			if (Strings.isNullOrEmpty(value)) {
				continue;
			}
			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}
		return content.toString();
	}

}
