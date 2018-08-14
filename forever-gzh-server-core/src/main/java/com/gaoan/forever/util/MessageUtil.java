package com.gaoan.forever.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.constant.GzhConstant;
import com.gaoan.forever.model.weixin.ImageMessageDO;
import com.gaoan.forever.model.weixin.MediaDO;
import com.gaoan.forever.model.weixin.MusicDO;
import com.gaoan.forever.model.weixin.MusicMessageDO;
import com.gaoan.forever.model.weixin.NewsDO;
import com.gaoan.forever.model.weixin.PictureDO;
import com.gaoan.forever.model.weixin.TextMessageDO;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	private static Logger logger = LoggerFactory.getLogger(MessageUtil.class);

	/**
	 * 图文消息-构建XML信息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String buildNewsMessage(String toUserName, String fromUserName, String content) {
		NewsDO newsDO = new NewsDO();
		newsDO.setFromUserName(toUserName);
		newsDO.setToUserName(fromUserName);
		newsDO.setCreateTime(String.valueOf(new Date().getTime()));
		newsDO.setMsgType(GzhConstant.MessageType.NEWS);
		newsDO.setArticleCount("3");

		PictureDO p1 = new PictureDO();
		p1.setTitle("test1");
		p1.setDescription("test1test1test1test1test1test1test1test1");
		p1.setPicUrl(
				"http://mmbiz.qpic.cn/mmbiz_jpg/dicGq4hKd2xtdWpcKUoB4icNhib7oBJzYeF6MkHUG7hLhTQUROZibBonvILfia1zxIMDhNZmjSibA1U8ibyUhSLmZ5WFw/0");
		PictureDO p2 = new PictureDO();
		p2.setTitle("test2");
		p2.setDescription("test2test2test2test2test2test2test2test2test2");
		p2.setPicUrl("C:\\Users\\Administrator\\Downloads\\qrcode_for_gh_cf3edae90dc5_344.jpg");
		PictureDO p3 = new PictureDO();
		p3.setTitle("test3");
		p3.setDescription("test3test3test3test3test3test3test3test3");
		p3.setPicUrl(
				"http://mmbiz.qpic.cn/mmbiz_jpg/dicGq4hKd2xtdWpcKUoB4icNhib7oBJzYeF6MkHUG7hLhTQUROZibBonvILfia1zxIMDhNZmjSibA1U8ibyUhSLmZ5WFw/0");

		List<PictureDO> picts = new ArrayList<PictureDO>();
		picts.add(p1);
		picts.add(p2);
		picts.add(p3);

		newsDO.setArticles(picts);

		String result = MessageUtil.objectToXml(newsDO);
		return result;
	}

	/**
	 * 图片消息-构建XML信息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String buildImageMessage(String toUserName, String fromUserName) {
		ImageMessageDO message = new ImageMessageDO();
		message.setFromUserName(toUserName);
		message.setToUserName(fromUserName);
		message.setCreateTime(String.valueOf(new Date().getTime()));
		message.setMsgType(GzhConstant.MessageType.IMAGE);

		MediaDO mediaDO = new MediaDO();
		mediaDO.setMediaId("DHWYZdfjFXmxqfyccbz7qCVPVpbkzPjCpMRgZwSCTbAaBDsTRHd1XNosEZ1s_WgN");

		message.setImage(mediaDO);

		String result = MessageUtil.objectToXml(message);
		return result;
	}

	/**
	 * 音乐消息-构建XML信息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String buildMusicMessage(String toUserName, String fromUserName) {
		MusicMessageDO message = new MusicMessageDO();
		message.setFromUserName(toUserName);
		message.setToUserName(fromUserName);
		message.setCreateTime(String.valueOf(new Date().getTime()));
		message.setMsgType(GzhConstant.MessageType.MUSIC);

		MusicDO musicDO = new MusicDO();
		musicDO.setTitle("最美的期待");
		musicDO.setDescription("周笔畅");
		musicDO.setMusicUrl(
				"http://sc1.111ttt.cn:8282/2018/1/03m/13/396131229550.m4a?tflag=1519095601&pin=6cd414115fdb9a950d827487b16b5f97#.mp3");
		musicDO.setHQMusicUrl("");
		musicDO.setThumbMediaId("rV9LIs9XtG1MSHU6-lNI0b7TDRhXeIdj0SIfbf2QoM7uUeragnNf0Wa9JlA2ulxS");

		message.setMusic(musicDO);

		String result = MessageUtil.objectToXml(message);
		return result;
	}

	/**
	 * 普通消息-构建XML信息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String buildTextMessage(String toUserName, String fromUserName, String content) {
		TextMessageDO message = new TextMessageDO();
		message.setFromUserName(toUserName);// 原来【接收消息用户】变为回复时【发送消息用户】
		message.setToUserName(fromUserName);
		message.setCreateTime(String.valueOf(new Date().getTime()));
		message.setMsgType(GzhConstant.MessageType.TEXT);
		// 创建当前时间为消息时间
		message.setContent(content);
		String result = MessageUtil.objectToXml(message); // 调用Message工具类，将对象转为XML字符串

		return result;
	}

	/**
	 * 构建菜单信息
	 * 
	 * @return
	 */
	public static String buildMenuMsg() {
		StringBuffer sb = new StringBuffer();
		sb.append("感谢您关注Forever2店铺.\n");
		sb.append("回复1: 店铺地址\n");
		sb.append("回复2: 店铺上新\n");
		sb.append("回复3: 获取图文消息\n");
		sb.append("回复4: 获取图片消息\n");
		sb.append("回复5: 收听一首音乐\n\n");
		sb.append("回复?: 重新获取此信息");
		return sb.toString();
	}

	/**
	 * 获取店铺地址
	 * 
	 * @return
	 */
	public static String buildAddressMsg() {
		StringBuffer sb = new StringBuffer();
		sb.append("江西省高安市凤凰小区29号Forever2店铺");
		return sb.toString();
	}

	/**
	 * 获取销售详情
	 * 
	 * @return
	 */
	public static String buildSellDetail() {
		StringBuffer sb = new StringBuffer();
		sb.append("店铺正在夏七上新中,更多详情请移驾店铺了解...");
		return sb.toString();
	}

	/**
	 * xml转换成Map对象
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		// 从dom4j的jar包中，拿到SAXReader对象。
		SAXReader reader = new SAXReader();
		InputStream is = request.getInputStream();// 从request中，获取输入流
		Document doc = reader.read(is);// 从reader对象中,读取输入流
		Element root = doc.getRootElement();// 获取XML文档的根元素
		List<Element> list = root.elements();// 获得根元素下的所有子节点
		for (Element e : list) {
			map.put(e.getName(), e.getText());// 遍历list对象，并将结果保存到集合中
		}
		is.close();
		return map;
	}

	/**
	 * 将文本消息对象转化成XML格式
	 * 
	 * @param message
	 *            文本消息对象
	 * @return 返回转换后的XML格式
	 * 
	 */
	public static String objectToXml(TextMessageDO message) {
		XStream xs = new XStream();
		// 由于转换后xml根节点默认为class类，需转化为<xml>
		xs.alias("xml", message.getClass());
		String result = xs.toXML(message);
		logger.info("TextMessageDO objectToXml, xml = {}", result);
		return result;
	}

	/**
	 * 将图文消息转换成xml
	 * 
	 * @param newsDO
	 * @return
	 */
	public static String objectToXml(NewsDO newsDO) {
		XStream xs = new XStream();
		// 由于转换后xml根节点默认为class类，需转化为<xml>
		xs.alias("xml", newsDO.getClass());
		xs.alias("item", PictureDO.class);
		String result = xs.toXML(newsDO);
		logger.info("NewsDO objectToXml, xml = {}", result);
		return result;
	}

	/**
	 * 将文本消息对象转化成XML格式
	 * 
	 * @param message
	 *            文本消息对象
	 * @return 返回转换后的XML格式
	 * 
	 */
	public static String objectToXml(ImageMessageDO message) {
		XStream xs = new XStream();
		// 由于转换后xml根节点默认为class类，需转化为<xml>
		xs.alias("xml", message.getClass());

		String result = xs.toXML(message);
		logger.info("ImageMessageDO objectToXml, xml = {}", result);
		return result;
	}

	/**
	 * 将音乐消息对象转化成XML格式
	 * 
	 * @param message
	 *            文本消息对象
	 * @return 返回转换后的XML格式
	 * 
	 */
	public static String objectToXml(MusicMessageDO message) {
		XStream xs = new XStream();
		// 由于转换后xml根节点默认为class类，需转化为<xml>
		xs.alias("xml", message.getClass());

		String result = xs.toXML(message);
		logger.info("MusicMessageDO objectToXml, xml = {}", result);
		return result;
	}
}
