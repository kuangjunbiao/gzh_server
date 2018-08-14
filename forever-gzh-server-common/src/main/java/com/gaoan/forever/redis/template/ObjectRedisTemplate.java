package com.gaoan.forever.redis.template;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.gaoan.forever.redis.bean.RedisMessageBean;

/**
 * 重写redis模板。以便适应object类型消息发送
 */
public class ObjectRedisTemplate extends StringRedisTemplate {

	public ObjectRedisTemplate(){
		super();
	}
	public ObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
		super(connectionFactory);
	}

	/**
	 * 不带topic的消息发送，topic 存放在messagebean中
	 * @param messageBean
	 */
	public void convertAndSend(RedisMessageBean messageBean){
		String channel = messageBean.getTopic();
		String message = messageBean.toString();
		super.convertAndSend(channel, message);
	}

	/**
	 * 指定topic发送消息
	 * @param channel
	 * @param messageBean
	 */
	public void convertAndSend(String channel,RedisMessageBean messageBean){
		String message = messageBean.toString();
		super.convertAndSend(channel,message);
	}
}
