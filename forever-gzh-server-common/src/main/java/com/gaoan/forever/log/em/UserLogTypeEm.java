package com.gaoan.forever.log.em;

/**
 * 用户类型枚举
 * Created by longshengtang on 2017/4/19.
 */
public enum UserLogTypeEm {
    /**
     * 后端
     */
    BACK_END(1, "后端")
    /**
     * 前端
     */
    , FRONT_END(2, "前端")
    /**
     * 全部
     */
    , ALL_END(3, "全部");

    UserLogTypeEm(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;
    private String value;
}
