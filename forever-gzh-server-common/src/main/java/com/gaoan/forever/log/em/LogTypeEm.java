package com.gaoan.forever.log.em;

/**
 * 日志类型枚举
 * Created by longshengtang on 2017/4/17.
 */
public enum LogTypeEm {
    /**
     * 未知类型
     */
    UNKNOWN((short)0, "未知类型"),
    /**
     * 登录
     */
    LOGIN((short)1, "登录"),
    /**
     * 退出
     */
    LOOUT((short)2, "退出"),
    /**
     * 添加会员
     */
    ADD_USER((short)3, "添加会员")
    /**
     * 编辑会员
     */
    , EDIT_USER((short)4, "编辑会员")
    /**
     * 编辑会员
     */
    , EDIT_PASSOWRD((short)5, "修改密码")
    /**
     * 编辑会员
     */
    , RESET_PASSOWRD((short)6, "重置密码")
    /**
     * 添加后台管理员
     */
    , ADD_ADMIN((short)7, "添加后台管理员")
    /**
     * 编辑后台管理员
     */
    , EDIT_ADMIN((short)8, "编辑后台管理员")
    /**
     *交易审核
     */
    , TRANS_AUDIT((short)9, "交易审核")
    /**
     *营销管理
     */
    , MARKETING_MGR((short)10, "营销管理")
    /**
     *修改数据调控
     */
    , DATA_REGULATING_MGR((short)11, "修改数据调控");

    LogTypeEm(short code, String value) {
        this.code = code;
        this.value = value;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private short code;
    private String value;
}
