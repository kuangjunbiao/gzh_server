package com.gaoan.forever.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.UnavailableSecurityManagerException;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.utils.character.StringUtils;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 8633328680035195765L;

	private String ret;
	private String msg;
	private Exception exception;
	private String code;
	private boolean iscustommsg;

	public AppException(String code, String msg) {
		this.ret = code;
		this.msg = msg;
	}

	public AppException(String ret, String msg, Exception exception) {
		this.ret = ret;
		this.msg = msg;
		this.exception = exception;
	}

	public AppException(MessageInfoConstant errorCode, String message) {
		this(String.valueOf(errorCode.getCode()), message, null);
	}

	public AppException(MessageInfoConstant errorCode, Exception ex) {
		this(String.valueOf(errorCode.getCode()), errorCode.getMsgZh(), ex);
	}

	public AppException(MessageInfoConstant error) {
		boolean bool = false;
		try{
			this.ret = error.getCode() + "";
            this.msg = error.getMsgZh();
		}catch(UnavailableSecurityManagerException e){
			bool = true;
//			e.printStackTrace();
		}finally {
			if(bool){
				throw new AppException(error,(Object)"en");
			}
		}
	}
	
	public AppException(MessageInfoConstant error,String msg, Boolean iscustommsg) {
		boolean bool = false;
		try{
			this.ret = error.getCode() + "";
			this.code = error.getCode() + "";
            this.msg = msg;
            if(!StringUtils.isEmpty(msg))
            this.iscustommsg = iscustommsg;
		}catch(UnavailableSecurityManagerException e){
			bool = true;
		}finally {
			if(bool){
				throw new AppException(error,(Object)"en");
			}
		}
	}
	
	public AppException(MessageInfoConstant error, Boolean isCode) {
		boolean bool = false;
		try{
			this.ret = error.getCode() + "";
			this.code = error.getCode() + "";
            this.msg = error.getMsgZh();
		}catch(UnavailableSecurityManagerException e){
			bool = true;
//			e.printStackTrace();
		}finally {
			if(bool){
				throw new AppException(error,(Object)"en");
			}
		}
	}

	public AppException(MessageInfoConstant error, Object lang) {

		this.ret = error.getCode() + "";
		if(null != lang && lang.toString().equalsIgnoreCase("zh")){
			this.msg = error.getMsgZh();
		}else{
			this.msg = error.getMsgEn();
		}
	}

	public String getMessage() {
		return this.msg;
	}

	public String getRet() {
		return this.ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getExceptionMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ret", this.ret);
		map.put("msg", this.msg);
		map.put("iscustommsg", this.iscustommsg);
		return map;
	}

	public String getJsonString() {
		return JSON.toJSONString(getExceptionMap());
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public boolean isIscustommsg() {
		return iscustommsg;
	}

	public void setIscustommsg(boolean iscustommsg) {
		this.iscustommsg = iscustommsg;
	}
	
	
}
