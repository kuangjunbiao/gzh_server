package com.gaoan.forever.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * @author logshengtang
 * @ClassName: IPUtil
 * @Desc: Ip工具类
 * @date 20170423
 */
public class IPUtil {

	public final static Logger LOGGER = Logger.getLogger(IPUtil.class);

	/**
	 * 获取访问用户的客户端IP（适用于公网与局域网）.
	 */
	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();
		}
		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ip = str;
				break;
			}
		}
		return ip;
	}

	/**
	 * 描述：获取IP+[IP所属地址]
	 */
	public static Map<String, String> getIpBelongAddress(HttpServletRequest request) {
		Map<String, String> ipMap = Collections.synchronizedMap(new HashMap<String, String>());
		String ip = "未知";
		String address = "未知";
		try {
			ip = getIpAddress(request);
			address = getIPbelongAddress(ip);
		} catch (Exception e) {
			throw e;
		} finally {
			ipMap.put("ip", ip);
			ipMap.put("address", address);
			return ipMap;
		}

	}

	/**
	 * 描述：获取IP所属地址
	 *
	 * @param ip
	 * @return
	 */
	public static String getIPbelongAddress(String ip) {

		String ipAddress = "[]";
		try {
			// 淘宝提供的服务地址
			String context = call("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
			JSONObject fromObject = JSONObject.parseObject(context);
			String code = fromObject.getString("code");
			if (code.equals("0")) {
				JSONObject jsonObject = fromObject.getJSONObject("data");
				ipAddress = jsonObject.get("country") + "/" + jsonObject.get("city");
			}
		} catch (Exception e) {
			LOGGER.error("获取IP所属地址出错", e);
			e.printStackTrace();
		}
		return ipAddress;
	}

	/**
	 * 描述：获取Ip所属地址
	 *
	 * @param urlStr
	 */
	public static String call(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

			httpCon.setConnectTimeout(3000);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("GET");

			int code = httpCon.getResponseCode();

			if (code == 200) {
				return streamConvertToSting(httpCon.getInputStream());
			}
		} catch (Exception e) {
			LOGGER.error("获取IP所属地址出错", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述：将InputStream转换成String
	 *
	 * @param is
	 */
	public static String streamConvertToSting(InputStream is) {

		String tempStr = "";
		try {
			if (is == null)
				return null;
			ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
			byte[] by = new byte[1024];
			int len = 0;
			while ((len = is.read(by)) != -1) {
				arrayOut.write(by, 0, len);
			}
			tempStr = new String(arrayOut.toByteArray());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempStr;
	}

	// /**
	// * 获取ip归属地
	// *
	// * @param ip
	// * @return
	// */
	// public static BaseResp<String> getAttribution(String ip) {
	//
	// String context = call(ip);
	//
	// System.out.println("context=="+context);
	// JSONObject fromObject = JSONObject.parseObject(context);
	// System.out.println("fromObject==" + fromObject);
	// String code = fromObject.getString("code");
	// if (!"0".equals(code)) {
	// return BaseResp.buildFail("返回失败" + fromObject.get("data"));
	// }
	//
	// JSONObject jsonObject = fromObject.getJSONObject("data");
	//
	// Object country = jsonObject.get("country");
	// if (country == null) {
	// return BaseResp.buildFail("获取归属国家失败");
	// }
	//
	// if ("未分配或者内网IP".equals(country)) {
	// return BaseResp.buildFail("未分配或者内网IP");
	// }
	//
	// System.out.println(fromObject);
	// System.err.println(jsonObject.get("city"));
	//
	// IpInfo ipInfo = jsonObject.toJavaObject(IpInfo.class);
	//
	// return BaseResp.build(ipInfo.result());
	// }

	public static void main(String[] args) {
		String context = call("http://ip.taobao.com/service/getIpInfo.php?ip=120.192.182.1");
		// String context =
		// call("http://ip.taobao.com/service/getIpInfo.php?ip=183.49.85.221");
		// String context =
		// call("http://ip.taobao.com/service/getIpInfo.php?ip=0:0:0:0:0:0:0:1");
		// String context =
		// call("http://ip.taobao.com/service/getIpInfo.php?ip=192.168.0.109");

		// String url =
		// "http://ip.taobao.com/service/getIpInfo.php?ip=120.192.182.1";
		// String url =
		// "http://ip.taobao.com/service/getIpInfo.php?ip=120.192.182.1";
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=0:0:0:0:0:0:0:1";
		// String url =
		// "http://ip.taobao.com/service/getIpInfo.php?ip=183.49.85.221";
		// BaseResp<String> attribution = getAttribution(url);
		//
		// System.out.println(attribution + "==========" + (attribution.isOk() ?
		// attribution.getData() : ""));

	}
}