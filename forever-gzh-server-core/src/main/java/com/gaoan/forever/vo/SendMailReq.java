package com.gaoan.forever.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 发送邮件请求报文
 */
public class SendMailReq {
    /**
     * 邮件地址
     */
    @ApiModelProperty("邮件地址：(当mailType值不为2登录时候，必填且有效，其它值时该属性无效)")
    private String addr;

    /**
     * 邮件类型：0为其它；1为注册；2登录；3为支付；4密码修改<br>
     */
    @ApiModelProperty("邮件类型：0为其它；1为注册；2登录；3为支付；4密码修改")
    private String mailType;
    /**
     * 邮件验证码
     */
    private String mailCode;
    /**图片验证码*/
    @ApiModelProperty("图片验证码")
    private String picCaptcha;
    /**
     * 用户名称：(当mailType值为2登录时候，必填且有效，其它值无效)会向该注册用户所绑定的邮箱发送验证码
     */
    private String username;

    private String email;

    private String code;
    /**邮件标题*/
    private String subject;
    /**请求同步返回*/
    private boolean isSynch = true;
    
    private String reqContent;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicCaptcha() {
        return picCaptcha;
    }

    public void setPicCaptcha(String picCaptcha) {
        this.picCaptcha = picCaptcha;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSynch() {
        return isSynch;
    }

    public void setSynch(boolean synch) {
        isSynch = synch;
    }
    
    public String getReqContent() {
		return reqContent;
	}

	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}

	@Override
    public String toString() {
        return "SendMailReq{" +
                "addr='" + addr + '\'' +
                ", mailType='" + mailType + '\'' +
                ", mailCode='" + mailCode + '\'' +
                ", picCaptcha='" + picCaptcha + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", subject='" + subject + '\'' +
                ", isSynch=" + isSynch +
                '}';
    }
}