package com.gaoan.forever.model.weixin;

public class TextMessageDO extends BaseMessageDO {

	private String Content;

	private String MsgId;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}
