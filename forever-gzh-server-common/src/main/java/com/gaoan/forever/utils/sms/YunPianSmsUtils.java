package com.gaoan.forever.utils.sms;

import com.alibaba.fastjson.JSONObject;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.SmsSingleSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by one on 2017/8/10.
 */
public class YunPianSmsUtils {

    private static final Logger logger = LoggerFactory.getLogger(YunPianSmsUtils.class);

    private static final String API_KEY = "833138f505bdbb539afcdb5fee5e9e0d";
    //初始化client,apikey作为所有请求的默认值(可以为空)
    private static final YunpianClient YUNPIAN_CLIENT = new YunpianClient(API_KEY).init();

	public static boolean sendSingle(String mobile, String content) {
		logger.info("发送手机号码:{},发送内容:{}", mobile, content);
		// 修改账户信息API
		Map<String, String> param = YUNPIAN_CLIENT.newParam(2);
		param.put(YunpianClient.MOBILE, mobile);
		param.put(YunpianClient.TEXT, content);
		Result<SmsSingleSend> result = YUNPIAN_CLIENT.sms().single_send(param);
		// YUNPIAN_CLIENT.close();

		if (0 == result.getCode()) {
			return true;
		}
		logger.info("发送短信失败，返回：{}", result == null ? "" : JSONObject.toJSONString(result));
		return false;
	}
	
	public static boolean sendBatch(String mobile, String content) {
		logger.info("发送手机号码:{},发送内容:{}", mobile, content);
		// 修改账户信息API
		Map<String, String> param = YUNPIAN_CLIENT.newParam(2);
		param.put(YunpianClient.MOBILE, mobile);
		param.put(YunpianClient.TEXT, content);
		Result<SmsBatchSend> result = YUNPIAN_CLIENT.sms().batch_send(param);
		// YUNPIAN_CLIENT.close();
		
		if (0 == result.getCode()) {
			return true;
		}
		logger.info("发送短信失败，返回：{}", result == null ? "" : JSONObject.toJSONString(result));
		return false;
	}
}
