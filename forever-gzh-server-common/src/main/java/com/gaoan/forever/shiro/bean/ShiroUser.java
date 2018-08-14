package com.gaoan.forever.shiro.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser<T> implements Serializable {

    private Long id;
    private String loginName;

    private String ipAddress;
    /**
     * 用户对象
     */
    private T user;

    /**
     * 加入更多的自定义参数
     */
    private Map<String, Object> attribute = new HashMap<String, Object>();

    public ShiroUser() {

    }

    public ShiroUser(String loginName) {
        this.loginName = loginName;
    }

    public ShiroUser(Long id, String loginName) {
        this.id = id;
        this.loginName = loginName;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public T getUser() {
        return user;
    }


    public void setUser(T user) {
        this.user = user;
    }

    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public Object removeAttribute(String name) {
        return attribute.remove(name);
    }

    public Map<String, Object> getAttributes() {
        return this.attribute;
    }

    @Override
    public String toString() {
        return loginName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}