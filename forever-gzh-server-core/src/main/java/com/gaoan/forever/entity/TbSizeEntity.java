package com.gaoan.forever.entity;

import java.io.Serializable;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbSizeEntity 描述: 尺寸: JAVA
 */
@ApiModel(value = "TbSizeEntity", description = "尺寸")
public class TbSizeEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935527534611286412L;

	/** 编号 **/
	@ApiModelProperty("编号")
	private Long id;
	/** 尺寸名 **/
	@ApiModelProperty("尺寸名")
	private String sizeName;
	/** 尺寸简称 **/
	@ApiModelProperty("尺寸简称")
	private String sizeCode;
	/** 创建时间 **/
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 无参构造
	 */
	public TbSizeEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getSizeCode() {
		return sizeCode;
	}

	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}