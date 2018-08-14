package com.gaoan.forever.model.weixin;

import java.io.Serializable;
import java.util.List;

public class AddMenuParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7409401593730328027L;

	private List<SingleMenuDO> button;

	public List<SingleMenuDO> getButton() {
		return button;
	}

	public void setButton(List<SingleMenuDO> button) {
		this.button = button;
	}

}
