package com.gaoan.forever.model.export;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gaoan.forever.log.spring.ExportColComment;

public class ExportSalesOrderDetailModel implements Serializable {

	private static final long serialVersionUID = 5683909231758437624L;

	@ExportColComment("创建时间")
	private Date createTime;
	@ExportColComment("进货单名称")
	private String purchaseOrderName;
	@ExportColComment("货品名称")
	private String goodsName;
	@ExportColComment("颜色")
	private String colorName;
	@ExportColComment("尺寸")
	private String sizeName;
	@ExportColComment("销售价")
	private BigDecimal sellPrice;
	@ExportColComment("数量")
	private Long quantity;
	@ExportColComment("平均成本价")
	private BigDecimal avgPrice;
	@ExportColComment("利润")
	private BigDecimal profit;
	@ExportColComment("创建人")
	private String realName;

	public ExportSalesOrderDetailModel() {
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

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}