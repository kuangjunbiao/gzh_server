package com.gaoan.forever.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbStockEntity 描述: 库存表 类型: JAVA
 */
@ApiModel(value = "TbStockEntity", description = "库存表")
public class TbStockEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9215448973981331258L;

	/** 编号 **/
	@ApiModelProperty("编号")
	private Long id;
	/** 进货单名称 **/
	@ApiModelProperty("进货单名称")
	private String purchaseOrderName;
	/** 商品名称 **/
	@ApiModelProperty("商品名称")
	private String goodsName;
	/** 顏色编号 **/
	@ApiModelProperty("顏色编号")
	private Long colorId;
	/** 尺寸编号 **/
	@ApiModelProperty("尺寸编号")
	private Long sizeId;
	/** 剩余数量 **/
	@ApiModelProperty("剩余数量")
	private Long quantity;
	/** 库存平均价 **/
	@ApiModelProperty("库存平均价")
	private BigDecimal avgPrice;
	/** 创建时间 **/
	@ApiModelProperty("创建时间")
	private Date createTime;
	/** 修改时间 **/
	@ApiModelProperty("修改时间")
	private Date updateTime;

	/**
	 * 无参构造
	 */
	public TbStockEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPurchaseOrderName() {
		return purchaseOrderName;
	}

	public void setPurchaseOrderName(String purchaseOrderName) {
		this.purchaseOrderName = purchaseOrderName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

}