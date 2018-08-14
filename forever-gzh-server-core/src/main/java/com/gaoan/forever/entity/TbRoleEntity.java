package com.gaoan.forever.entity;

import java.io.Serializable;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbRoleEntity 描述: 角色实体类 类型: JAVA
 */
@ApiModel(value = "TbRoleEntity", description = "用户")
public class TbRoleEntity extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5586757977327966051L;

    /** 编号 **/
    @ApiModelProperty("编号")
    private Long id;
    /** 用户账号 **/
    @ApiModelProperty("用户账号")
    private String roleName;
    /** 创建时间 **/
    @ApiModelProperty("创建时间")
    private Date createTime;
    /** 修改时间 **/
    @ApiModelProperty("修改时间")
    private Date updateTime;

    /**
     * 无参构造
     */
    public TbRoleEntity() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getRoleName() {
	return roleName;
    }

    public void setRoleName(String roleName) {
	this.roleName = roleName;
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