package com.gaoan.forever.redis.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by dotnar on 2017/5/18.
 */

public class RedisMessageBean<T> {
	private String topic;
	private String tags;
	private T msgBody;
	private String handlingName;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public T getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(T msgBody) {
		this.msgBody = msgBody;
	}

	public RedisMessageBean() {
	}

	public RedisMessageBean(String topic, String tags, T msgBody) {
		this.topic = topic;
		this.tags = tags;
		this.msgBody = msgBody;
	}

	public RedisMessageBean(String topic, T msgBody) {
		this.topic = topic;
		this.msgBody = msgBody;
	}

	public String getHandlingName() {
		return handlingName;
	}

	public void setHandlingName(String handlingName) {
		this.handlingName = handlingName;
	}

	@Override
	public String toString(){
		return JSONObject.toJSONString(this);
	}


}
