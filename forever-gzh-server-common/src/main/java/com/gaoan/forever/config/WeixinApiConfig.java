package com.gaoan.forever.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weixinapi")
public class WeixinApiConfig {

	private String host;

	private String protocol;

	private String apiGetToken;

	private String apiGetMenu;

	private String apiCreateMenu;

	private String apiDeleteMenu;

	private String apiUploadTempMaterial;

	private String apiGetTempMaterial;

	private String apiAddMaterial;

	private String apiBatchGetMaterial;

	private String appId;

	private String appSecret;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getApiGetToken() {
		return buildUrl(apiGetToken);
	}

	public void setApiGetToken(String apiGetToken) {
		this.apiGetToken = apiGetToken;
	}

	public String getApiGetMenu() {
		return buildUrl(apiGetMenu);
	}

	public void setApiGetMenu(String apiGetMenu) {
		this.apiGetMenu = apiGetMenu;
	}

	public String getApiCreateMenu() {
		return buildUrl(apiCreateMenu);
	}

	public void setApiCreateMenu(String apiCreateMenu) {
		this.apiCreateMenu = apiCreateMenu;
	}

	private String buildUrl(String url) {
		return this.getProtocol() + "://" + this.getHost() + url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getApiDeleteMenu() {
		return buildUrl(apiDeleteMenu);
	}

	public void setApiDeleteMenu(String apiDeleteMenu) {
		this.apiDeleteMenu = apiDeleteMenu;
	}

	public String getApiUploadTempMaterial() {
		return buildUrl(apiUploadTempMaterial);
	}

	public void setApiUploadTempMaterial(String apiUploadTempMaterial) {
		this.apiUploadTempMaterial = apiUploadTempMaterial;
	}

	public String getApiGetTempMaterial() {
		return buildUrl(apiGetTempMaterial);
	}

	public void setApiGetTempMaterial(String apiGetTempMaterial) {
		this.apiGetTempMaterial = apiGetTempMaterial;
	}

	public String getApiBatchGetMaterial() {
		return buildUrl(apiBatchGetMaterial);
	}

	public void setApiBatchGetMaterial(String apiBatchGetMaterial) {
		this.apiBatchGetMaterial = apiBatchGetMaterial;
	}

	public String getApiAddMaterial() {
		return buildUrl(apiAddMaterial);
	}

	public void setApiAddMaterial(String apiAddMaterial) {
		this.apiAddMaterial = apiAddMaterial;
	}

}
