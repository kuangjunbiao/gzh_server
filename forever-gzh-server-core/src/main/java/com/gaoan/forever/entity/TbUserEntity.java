package com.gaoan.forever.entity;

import java.io.Serializable;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbUserEntity 描述: 用户 类型: JAVA
 */
@ApiModel(value = "TbUserEntity", description = "用户")
public class TbUserEntity extends BaseEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5148599265584464916L;

    /** 编号 **/
    @ApiModelProperty("编号")
    private Long id;
    /** 用户账号 **/
    @ApiModelProperty("用户账号")
    private String userName;
    /** 密码 **/
    @ApiModelProperty("密码")
    private String password;
    /** 用户真实姓名 **/
    @ApiModelProperty("用户真实姓名")
    private String realName;
    /** 状态**/
    @ApiModelProperty("状态, 0:无效, 1:有效")
    private Integer status;
    /** 创建时间 **/
    @ApiModelProperty("创建时间")
    private Date createTime;
    /** 修改时间 **/
    @ApiModelProperty("修改时间")
    private Date updateTime;

    /**
     * 无参构造
     */
    public TbUserEntity() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getRealName() {
	return realName;
    }

    public void setRealName(String realName) {
	this.realName = realName;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    public Date getUpdateTime() {
	return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
    }

}