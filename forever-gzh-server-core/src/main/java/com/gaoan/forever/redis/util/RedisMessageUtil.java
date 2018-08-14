package com.gaoan.forever.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by dotnar on 2017/5/17.
 */
@Component
public class RedisMessageUtil {

    @Autowired
    StringRedisTemplate template;

    public boolean sendMsg(String topic, Object o) {
	template.convertAndSend(topic, o);
	return true;
    }

}
