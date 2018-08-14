package com.gaoan.forever.entity;

import java.io.Serializable;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbResourcesEntity 描述: 资源 类型: JAVA
 */
@ApiModel(value = "TbResourcesEntity", description = "资源")
public class TbResourcesEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5840497349276161356L;

	/** 编号 **/
	@ApiModelProperty("编号")
	private Long id;
	/** 资源名称 **/
	@ApiModelProperty("资源名称")
	private String resourcesName;
	/** 类型, 1:菜单 2:操作 **/
	@ApiModelProperty("类型, 1:菜单 2:操作")
	private Integer type;
	/** 排序编号 **/
	@ApiModelProperty("排序编号")
	private Integer sortId;
	/** 创建时间 **/
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 无参构造
	 */
	public TbResourcesEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourcesName() {
		return resourcesName;
	}

	public void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	
}