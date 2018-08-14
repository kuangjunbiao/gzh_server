package com.gaoan.forever.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbConsumeEntity 描述: 店铺消費: JAVA
 */
@ApiModel(value = "TbConsumeEntity", description = "店铺消費")
public class TbConsumeEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3295959748458770833L;

	/** 编号 **/
	@ApiModelProperty("编号")
	private Long id;
	/** 消费日期 **/
	@ApiModelProperty("消费日期")
	private Date date;
	/** 消费金额 **/
	@ApiModelProperty("消费金额")
	private BigDecimal amount;
	/** 备注 **/
	@ApiModelProperty("备注")
	private String remark;
	/** 创建时间 **/
	@ApiModelProperty("创建时间")
	private Date createTime;
	/** 修改时间 **/
	@ApiModelProperty("修改时间")
	private Date updateTime;

	/**
	 * 无参构造
	 */
	public TbConsumeEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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