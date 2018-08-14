package com.gaoan.forever.model.weixin;

import java.io.Serializable;

public class BaseMenuDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2691975169168934470L;

	private String type;
	private String name;
	private String key;
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
