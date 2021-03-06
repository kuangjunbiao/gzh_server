package com.gaoan.forever.shiro.bean;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
    /**
     * 描述
     */
    private static final long serialVersionUID = -3178260335127476542L;

    private String captcha;

    public String getCaptcha() {
	return captcha;
    }

    public void setCaptcha(String captcha) {
	this.captcha = captcha;
    }

    public CaptchaUsernamePasswordToken() {
	super();
    }

    public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe, String captcha,
	    String host) {
	super(username, password, rememberMe, host);
	this.captcha = captcha;
    }

    public CaptchaUsernamePasswordToken(String username, String password, String captcha, String host) {
	super(username, password, host);
	this.captcha = captcha;
    }

    public CaptchaUsernamePasswordToken(String username, String password, String captcha) {
	super(username, password, false);
	this.captcha = captcha;
    }

}