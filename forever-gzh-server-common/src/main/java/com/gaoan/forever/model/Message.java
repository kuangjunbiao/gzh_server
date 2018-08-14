package com.gaoan.forever.model;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Message {

	private String ret;

	private String msg;

	private String code;

	private Boolean isEntity;

	private Boolean isList;

	private Object data;

	public Message() {
	}

	public Message(String ret, String msg, Object data) {
		this.ret = ret;
		this.msg = msg;
		this.data = data;
	}

	private Message(Builder builder) {

		this.ret = builder.ret;
		this.msg = builder.msg;
		this.code = builder.code;
		if (!builder.data.isEmpty()) {
			this.data = builder.data;
		}
	}

	public static Builder newBuilder() {
		return new Builder("0", "ok", null);
	}

	public String getRet() {
		return ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsEntity() {
		return isEntity;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getIsList() {
		return isList;
	}

	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public static class Builder {

		private String ret;

		private String code;

		private String msg;

		@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
		private Map<String, Object> data = Maps.newLinkedHashMap();

		private Builder() {

		}

		private Builder(String ret, String msg, String code) {
			this.ret = ret;
			this.msg = msg;
			this.code = code;
		}

		public Builder setRet(String ret) {
			this.ret = ret;
			return this;
		}

		public Builder setMsg(String msg) {
			this.msg = msg;
			return this;
		}

		public Builder setCode(String code) {
			this.code = code;
			return this;
		}

		public Builder setStatusCode(StatusCode statusCode) {
			this.ret = statusCode.getCode() + "";
			this.code = statusCode.getCode() + "";
			this.msg = statusCode.getMsgCn();
			return this;
		}

		public Builder put(String key, Object value) {
			data.put(key, value);
			return this;
		}

		public Builder setData(Map<String, Object> data) {
			this.data = data;
			return this;
		}

		public Message build() {
			return new Message(this);
		}

		public String builldJson() {
			return JSON.toJSONString(new Message(this));
		}

		public String builldJsonWithDateFormat(String dateFormat) {
			return JSON.toJSONStringWithDateFormat(new Message(this), dateFormat);
		}
	}

	public static void main(String[] args) {

		Message.Builder builder = Message.newBuilder();

		// builder.setStatusCode(StatusCode.COMMON_ERROR_CODE);
		Map<String, Object> md = new HashMap<String, Object>();
		md.put("123", "eer12we");
		md.put("12e", "23332");
		md.put("1323", "3232");
		md.put("dfee23", "2332");

		builder.setData(md);

		String content = JSON.toJSONString(builder.build());
		System.out.println(content);
	}

}
