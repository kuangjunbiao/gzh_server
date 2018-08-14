package com.gaoan.forever.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbPurchaseOrderEntity 描述: 进货订单表 类型: JAVA
 */
@ApiModel(value = "TbPurchaseOrderEntity", description = "进货订单表")
public class TbPurchaseOrderEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9215448973981331258L;

	/** 编号 **/
	@ApiModelProperty("编号")
	private Long id;
	/** 商品编号 **/
	@ApiModelProperty("商品编号")
	private Long goodsId;
	/** 颜色编号 **/
	@ApiModelProperty("颜色编号")
	private Long colorId;
	/** 尺寸编号 **/
	@ApiModelProperty("尺寸编号")
	private Long sizeId;
	/** 成本价 **/
	@ApiModelProperty("成本价")
	private BigDecimal costPrice;
	/** 数量 **/
	@ApiModelProperty("数量")
	private Long quantity;
	/** 进货总额 **/
	@ApiModelProperty("进货总额")
	private BigDecimal total;
	/** 用户编号 **/
	@ApiModelProperty("用户编号")
	private Long userId;
	/** 创建时间 **/
	@ApiModelProperty("创建时间")
	private Date createTime;
	/** 修改时间 **/
	@ApiModelProperty("修改时间")
	private Date updateTime;

	/**
	 * 无参构造
	 */
	public TbPurchaseOrderEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

}