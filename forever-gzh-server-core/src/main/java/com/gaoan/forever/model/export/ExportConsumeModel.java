package com.gaoan.forever.model.export;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gaoan.forever.log.spring.ExportColComment;

public class ExportConsumeModel implements Serializable {

	private static final long serialVersionUID = 5683909231758437624L;

	@ExportColComment("消费时间")
	private Date date;
	@ExportColComment("消费金额")
	private BigDecimal amount;
	@ExportColComment("备注")
	private String remark;

	public ExportConsumeModel() {
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


}