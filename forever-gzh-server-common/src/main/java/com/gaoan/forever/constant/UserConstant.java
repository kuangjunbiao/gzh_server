package com.gaoan.forever.constant;

/**
 * 用户常量 Created by three on 2017/4/13.
 */
public interface UserConstant {

    /** SESSION 中保存用户Id的key值 */
    public static final String SESSION_LOGIN_USER_KEY = "sessionLoginUserKey";

    public static final String FORM_SUBMIT = "formSubmit";

    public static final String STATIC_CODE = "static_code";

    public static final String SESSION_LOGIN_TOKEN = "sessionLoginToken";

    /**
     * 积分登录sessionkey
     */
    public static final String SESSION_SCORE_LOGIN_USER_KEY = "ScoreSessionKey";

    /**
     * 后台登录sessionkey
     */
    public static final String SESSION_BACKSTAGE_LOGIN_USER_KEY = "BackStageSessionLoginUserKey";

    public static final String SESSION_LOGIN_USER_SAFE_UUID = "SESSION_LOGIN_USER_SAFE_UUID";

    // 1为正常用户，2为内部用户
    public static final Integer NORMAL_USER = 1;
    public static final Integer INTERNAL_USER = 2;
    public static final String UNDER_LINE = "_";
}
