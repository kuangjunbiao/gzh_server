package com.gaoan.forever.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统参数配置 Created by NO.9527 on 2017年7月20日
 */
@Component
@ConfigurationProperties(prefix = "forever")
public class ForeverConfig {

	private String defaultPassword;

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

}
