package com.gaoan.forever.constant;

/**
 * 通用常量
 */
public class GzhConstant {

	public interface MessageType {
		/* 消息类型 */
		public final static String TEXT = "text";
		public final static String IMAGE = "image";
		public final static String VOICE = "voice";
		public final static String VIDEO = "video";
		public final static String SHORT_VIDEO = "shortvideo";
		public final static String LOCATION = "location";
		public final static String LINK = "link";
		public final static String THUMB = "thumb";
		public final static String MUSIC = "music";
		// 图文
		public final static String NEWS = "news";

		/* 事件类型 */
		public final static String EVENT = "event";
		public final static String EVENT_CLICK = "CLICK";
		public final static String EVENT_VIEW = "VIEW";
		public final static String EVENT_SUBSCRIBE = "subscribe";
		public final static String EVENT_UNSUBSCRIBE = "unsubscribe";
		public final static String EVENT_SCANCODE_PUSH = "scancode_push";
		public final static String EVENT_SCANCODE_WAITMSG = "scancode_waitmsg";
		public final static String EVENT_PIC_SYSPHOTO = "pic_sysphoto";
		public final static String EVENT_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
		public final static String EVENT_PIC_WEIXIN = "pic_weixin";
		public final static String EVENT_LOCATION_SELECT = "location_select";

		/* 素材类型 */

	}

}
