package com.gaoan.forever.entity;

import java.io.Serializable;
import java.util.Date;

import com.gaoan.forever.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 名称: TbColorEntity 描述: 颜色: JAVA
 */
@ApiModel(value = "TbColorEntity", description = "颜色")
public class TbColorEntity extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3295959748458770833L;
	
	/** 编号 **/
    @ApiModelProperty("编号")
    private Long id;
    /** 颜色名 **/
    @ApiModelProperty("颜色名")
    private String colorName;
    /** 创建时间 **/
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 无参构造
     */
    public TbColorEntity() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}