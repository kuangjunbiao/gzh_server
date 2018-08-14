package com.gaoan.forever.model.weixin;

import java.io.Serializable;
import java.util.List;

public class SingleMenuDO extends BaseMenuDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7409401593730328027L;

	private List<BaseMenuDO> sub_button;

	public List<BaseMenuDO> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<BaseMenuDO> sub_button) {
		this.sub_button = sub_button;
	}

}
