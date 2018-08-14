package com.gaoan.forever.entity;

import java.io.Serializable;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbRolePermissionEntity 描述: 角色权限 类型: JAVA
 */
@ApiModel(value = "TbRolePermissionEntity", description = "角色权限")
public class TbRolePermissionEntity extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1748760773530644894L;

    /** 编号 **/
    @ApiModelProperty("编号")
    private Long id;
    /** 角色编号 **/
    @ApiModelProperty("角色编号")
    private Long roleId;
    /** 资源编号 **/
    @ApiModelProperty("资源编号")
    private Long resourcesId;
    @ApiModelProperty("创建时间")
    private Date createTime;
    /** 修改时间 **/
    @ApiModelProperty("修改时间")
    private Date updateTime;

    /**
     * 无参构造
     */
    public TbRolePermissionEntity() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getRoleId() {
	return roleId;
    }

    public void setRoleId(Long roleId) {
	this.roleId = roleId;
    }

    public Long getResourcesId() {
	return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
	this.resourcesId = resourcesId;
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