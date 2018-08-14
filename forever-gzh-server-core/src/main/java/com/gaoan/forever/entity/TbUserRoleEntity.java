package com.gaoan.forever.entity;

import java.io.Serializable;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbUserRoleEntity 描述: 用户和角色 类型: JAVA
 */
@ApiModel(value = "TbUserRoleEntity", description = "用户和角色")
public class TbUserRoleEntity extends BaseEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5148599265584464916L;

    /** 编号 **/
    @ApiModelProperty("编号")
    private Long id;
    /** 用户编号 **/
    @ApiModelProperty("用户编号")
    private Long userId;
    /** 角色编号 **/
    @ApiModelProperty("角色编号")
    private Long roleId;
    /** 创建时间 **/
    @ApiModelProperty("创建时间")
    private Date createTime;
    /** 修改时间 **/
    @ApiModelProperty("修改时间")
    private Date updateTime;

    /**
     * 无参构造
     */
    public TbUserRoleEntity() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Long getRoleId() {
	return roleId;
    }

    public void setRoleId(Long roleId) {
	this.roleId = roleId;
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