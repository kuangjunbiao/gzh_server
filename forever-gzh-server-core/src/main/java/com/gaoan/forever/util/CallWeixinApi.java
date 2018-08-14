package com.gaoan.forever.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaoan.forever.config.WeixinApiConfig;
import com.gaoan.forever.constant.RedisConstant;
import com.gaoan.forever.model.weixin.AddMenuParam;
import com.gaoan.forever.utils.cache.CacheUtils;
import com.gaoan.forever.utils.http.HttpClient;
import com.google.gson.JsonObject;

@Component
public class CallWeixinApi {

	private static final Logger logger = LoggerFactory.getLogger(CallWeixinApi.class);

	private static WeixinApiConfig weiXinApiConfig;

	@Autowired
	public void setWeiXinApiConfig(WeixinApiConfig weiXinApiConfig) {
		CallWeixinApi.weiXinApiConfig = weiXinApiConfig;
	}

	/**
	 * 获取app_token
	 * 
	 * @return
	 */
	public static String getAppToken() {
		String appToken = (String) CacheUtils.get(RedisConstant.REDIS_KEY_APP_TOKEN);
		if (StringUtils.isBlank(appToken)) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("grant_type", "client_credential");
			param.put("appid", weiXinApiConfig.getAppId());
			param.put("secret", weiXinApiConfig.getAppSecret());

			try {
				String result = HttpClient.get(weiXinApiConfig.getApiGetToken(), param);
				JSONObject tokenObj = JSONObject.parseObject(result);
				if (tokenObj != null) {
					appToken = tokenObj.getString("access_token");
					Long time = tokenObj.getLong("expires_in");
					CacheUtils.put(RedisConstant.REDIS_KEY_APP_TOKEN, appToken, time);
				}
			} catch (Exception e) {
				logger.error("getAppToken error.", e);
			}
		}

		return appToken;
	}

	/**
	 * 查询自定义菜单
	 * 
	 * @return
	 */
	public static String getMenu() {
		String appToken = getAppToken();
		String result = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", appToken);
		try {
			result = HttpClient.get(weiXinApiConfig.getApiGetMenu(), param);
			logger.info("getMenu result = {}", result);
		} catch (Exception e) {
			logger.error("getMenu error.", e);
		}

		return result;
	}

	/**
	 * 新增自定义菜单
	 * 
	 * @param addMenuParam
	 * @return
	 */
	public static String insertMenu(AddMenuParam addMenuParam) {
		String appToken = getAppToken();
		String result = "";
		try {
			result = HttpClient.postJson(weiXinApiConfig.getApiCreateMenu() + "?access_token=" + appToken,
					JSON.toJSONString(addMenuParam));
			logger.info("insertMenu result = {}", result);
		} catch (Exception e) {
			logger.error("insertMenu error.", e);
		}
		return result;
	}

	/**
	 * 删除自定义菜单
	 * 
	 * @return
	 */
	public static String deleteMenu() {
		String appToken = getAppToken();
		String result = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", appToken);
		try {
			result = HttpClient.get(weiXinApiConfig.getApiDeleteMenu(), param);
			logger.info("insertMenu result = {}", result);
		} catch (Exception e) {
			logger.error("insertMenu error.", e);
		}
		return result;
	}

	/**
	 * 上传临时素材文件
	 * 
	 * @param b
	 * @return
	 */
	public static String uploadTempFile(String type, MultipartFile file) {
		String appToken = getAppToken();
		String result = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", appToken);
		param.put("type", type);

		Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
		try {
			fileMap.put(file.getOriginalFilename(), file.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			result = HttpClient.postFile(weiXinApiConfig.getApiUploadTempMaterial(), param, fileMap);
			logger.info("uploadFile result = {}", result);
		} catch (Exception e) {
			logger.error("uploadFile error.", e);
		}
		return result;
	}

	/**
	 * 获取临时素材文件
	 * 
	 * @param b
	 * @return
	 */
	public static String getTempFile(String mediaId) {
		String appToken = getAppToken();
		String result = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", appToken);
		param.put("media_id", mediaId);

		try {
			// HttpClient.executeF(weiXinApiConfig.getApiGetTempMaterial(),
			// HttpClient., "GET", "application/json;charset=UTF-8");
			result = HttpClient.get(weiXinApiConfig.getApiGetTempMaterial(), param);
			logger.info("getTempFile result = {}", result);
		} catch (Exception e) {
			logger.error("getTempFile error.", e);
		}
		return result;
	}

	/**
	 * 上传临时素材文件
	 * 
	 * @param b
	 * @return
	 */
	public static String uploadForeverMaterial(String type, MultipartFile file) {
		String appToken = getAppToken();
		String result = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", appToken);
		param.put("type", type);

		Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
		try {
			fileMap.put(file.getOriginalFilename(), file.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			result = HttpClient.postFile(weiXinApiConfig.getApiAddMaterial(), param, fileMap);
			logger.info("uploadForeverMaterial result = {}", result);
		} catch (Exception e) {
			logger.error("uploadForeverMaterial error.", e);
		}
		return result;
	}

	/**
	 * 获取永久素材文件
	 * 
	 * @return
	 */
	public static String getAllMaterial() {
		String appToken = getAppToken();
		String result = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", "image");
		param.put("offset", "0");
		param.put("count", "20");

		try {
			result = HttpClient.postJson(weiXinApiConfig.getApiBatchGetMaterial() + "?access_token=" + appToken,
					JSONObject.toJSONString(param));
			logger.info("getAllMaterial result = {}", result);
		} catch (Exception e) {
			logger.error("getAllMaterial error.", e);
		}
		return result;
	}

}
