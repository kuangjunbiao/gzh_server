package com.gaoan.forever.model.result;

import com.gaoan.forever.entity.TbStockEntity;

public class StockInfoModel extends TbStockEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013718917380465087L;
	private String colorName;
	private String sizeName;

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