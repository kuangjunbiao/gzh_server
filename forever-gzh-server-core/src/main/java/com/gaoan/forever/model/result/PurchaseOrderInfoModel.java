package com.gaoan.forever.model.result;

import com.gaoan.forever.entity.TbPurchaseOrderEntity;

public class PurchaseOrderInfoModel extends TbPurchaseOrderEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013718917380465087L;
	private String purchaseOrderName;
	private String goodsName;
	private String colorName;
	private String sizeName;
	private String realName;

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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

}