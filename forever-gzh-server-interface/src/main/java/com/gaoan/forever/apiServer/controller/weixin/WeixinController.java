package com.gaoan.forever.apiServer.controller.weixin;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.base.AppException;
import com.gaoan.forever.constant.GzhConstant;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.weixin.AddMenuParam;
import com.gaoan.forever.util.CallWeixinApi;
import com.gaoan.forever.util.MessageUtil;
import com.gaoan.forever.util.SHA1Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by NO.9527 on 2017年7月15日
 */
@RestController
@RequestMapping(value = "/api/weixin")
@Api(value = "WeixinController", description = "weixin控制器")
public class WeixinController {

	private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

	@ApiOperation(value = "查询自定义菜单")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/getMenu", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getMenu(HttpServletRequest request) throws Exception {
		String result = CallWeixinApi.getMenu();
		return result;
	}

	@ApiOperation(value = "新增自定义菜单")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/insertMenu", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object insertMenu(HttpServletRequest request, @RequestBody AddMenuParam addMenuParam) throws Exception {
		String result = CallWeixinApi.insertMenu(addMenuParam);

		return result;
	}

	@ApiOperation(value = "删除自定义菜单")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/deleteMenu", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object deleteMenu(HttpServletRequest request) throws Exception {
		String result = CallWeixinApi.deleteMenu();

		return result;
	}

	@ApiOperation(value = "上传永久素材")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "类型", paramType = "form", dataType = "String", required = true),
			@ApiImplicitParam(name = "file", value = "永久素材", paramType = "form", dataType = "File", required = true) })
	@RequestMapping(value = "/uploadForeverMaterial", produces = "multipart/form-data;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object uploadForeverMaterial(HttpServletRequest request, @RequestParam(required = true) String type,
			@RequestParam(required = true) MultipartFile file) throws Exception {
		if (file == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		String result = CallWeixinApi.uploadForeverMaterial(type, file);

		return result;
	}

	@ApiOperation(value = "上传临时素材")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "类型", paramType = "form", dataType = "String", required = true),
			@ApiImplicitParam(name = "file", value = "临时素材", paramType = "form", dataType = "File", required = true) })
	@RequestMapping(value = "/uploadTempMaterial", produces = "multipart/form-data;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object uploadTempMaterial(HttpServletRequest request, @RequestParam(required = true) String type)
			throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");

		String result = CallWeixinApi.uploadTempFile(type, file);

		return result;
	}

	@ApiOperation(value = "获取临时素材")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "mediaId", value = "临时素材", paramType = "query", dataType = "String", required = true) })
	@RequestMapping(value = "/getTempNews", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getTempNews(HttpServletRequest request, @RequestParam(required = true) String mediaId)
			throws Exception {

		String result = CallWeixinApi.getTempFile(mediaId);

		return result;
	}

	@ApiOperation(value = "获取所有素材")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/getAllMaterial", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getAllMaterial(HttpServletRequest request) throws Exception {

		String result = CallWeixinApi.getAllMaterial();

		return result;
	}

	@ApiOperation(value = "公众号对接")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object testGet(HttpServletRequest request) throws Exception {
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		List<String> params = new ArrayList<String>();
		params.add("forever2");
		params.add(timestamp);
		params.add(nonce);

		Collections.sort(params);

		StringBuffer content = new StringBuffer();
		for (int i = 0; i < params.size(); i++) {
			content.append(params.get(i));
		}

		logger.info("str = " + content);

		String result = "";
		if (SHA1Util.encode(content.toString()).equals(signature)) {
			logger.info("校验成功");
			result = echostr;
		}

		return result;
	}

	@ApiOperation(value = "公众号消息处理")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object testPost(HttpServletRequest request) throws Exception {
		String str = null;
		try {
			// 将request请求，传到Message工具类的转换方法中，返回接收到的Map对象
			Map<String, String> map = MessageUtil.xmlToMap(request);
			logger.info("xml = {}", map == null ? "" : JSON.toJSONString(map));
			// 从集合中，获取XML各个节点的内容
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			// String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			// String msgId = map.get("MsgId");
			String event = map.get("Event");

			// 判断消息类型是否是文本消息(text)
			switch (msgType) {
			case GzhConstant.MessageType.TEXT:
				if ("1".equals(content)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, MessageUtil.buildAddressMsg());
				} else if ("2".equals(content)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, MessageUtil.buildSellDetail());
				} else if ("3".equals(content)) {
					// 图文消息
					str = MessageUtil.buildNewsMessage(toUserName, fromUserName, content);
				} else if ("4".equals(content)) {
					// 图片消息
					str = MessageUtil.buildImageMessage(toUserName, fromUserName);
				} else if ("5".equals(content)) {
					// 音乐消息
					str = MessageUtil.buildMusicMessage(toUserName, fromUserName);
				} else if ("?".equals(content) || "？".equals(content)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, MessageUtil.buildMenuMsg());
				}
				break;
			case GzhConstant.MessageType.IMAGE:
				str = MessageUtil.buildTextMessage(toUserName, fromUserName,
						MessageFormat.format("MediaId = {0}\nPicUrl = {1}", map.get("MediaId"), map.get("PicUrl")));
				break;
			case GzhConstant.MessageType.LOCATION:
				str = MessageUtil.buildTextMessage(toUserName, fromUserName,
						MessageFormat.format("您现在的地理位置:\n{0}", map.get("Label")));
				break;
			case GzhConstant.MessageType.VOICE:
				str = MessageUtil.buildTextMessage(toUserName, fromUserName, MessageFormat
						.format("MediaID = {0}\nRecognition = {1}", map.get("MediaId"), map.get("Recognition")));
				break;
			case GzhConstant.MessageType.VIDEO:
				str = MessageUtil.buildTextMessage(toUserName, fromUserName, MessageFormat
						.format("MediaId = {0}\nThumbMediaId = {1}", map.get("MediaId"), map.get("ThumbMediaId")));
				break;
			case GzhConstant.MessageType.LINK:
				str = MessageUtil.buildTextMessage(toUserName, fromUserName,
						MessageFormat.format("Title = {0}\nDescription = {1}\nUrl={2}", map.get("Title"),
								map.get("Description"), map.get("Url")));
				break;
			case GzhConstant.MessageType.EVENT:
				if (GzhConstant.MessageType.EVENT_CLICK.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName,
							MessageFormat.format("点击click按钮, EventKey = {0}", map.get("EventKey")));
				} else if (GzhConstant.MessageType.EVENT_VIEW.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName,
							MessageFormat.format("点击view按钮, EventKey = {0}", map.get("EventKey")));
				} else if (GzhConstant.MessageType.EVENT_SUBSCRIBE.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, MessageUtil.buildMenuMsg());
				} else if (GzhConstant.MessageType.EVENT_SCANCODE_PUSH.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, "点击扫一扫推事件按钮");
				} else if (GzhConstant.MessageType.EVENT_SCANCODE_WAITMSG.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, "点击扫一扫按钮");
				} else if (GzhConstant.MessageType.EVENT_PIC_SYSPHOTO.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, "点击选择系统照片按钮");
				} else if (GzhConstant.MessageType.EVENT_PIC_PHOTO_OR_ALBUM.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, "点击选择系统或现拍照片按钮");
				} else if (GzhConstant.MessageType.EVENT_PIC_WEIXIN.equals(event)) {
					str = MessageUtil.buildTextMessage(toUserName, fromUserName, "点击上传微信图片按钮");
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("testPost error.", e);
		}
		return str;
	}
}
