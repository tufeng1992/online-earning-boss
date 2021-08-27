package com.powerboot.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 客服分流配置表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
public class SaleShuntConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//销售人员id
	private Long saleId;
	//分流开关
	private Integer shuntSwitch;
	//分流顺序
	private Integer shuntOrder;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：销售人员id
	 */
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	/**
	 * 获取：销售人员id
	 */
	public Long getSaleId() {
		return saleId;
	}
	/**
	 * 设置：分流开关
	 */
	public void setShuntSwitch(Integer shuntSwitch) {
		this.shuntSwitch = shuntSwitch;
	}
	/**
	 * 获取：分流开关
	 */
	public Integer getShuntSwitch() {
		return shuntSwitch;
	}
	/**
	 * 设置：分流顺序
	 */
	public void setShuntOrder(Integer shuntOrder) {
		this.shuntOrder = shuntOrder;
	}
	/**
	 * 获取：分流顺序
	 */
	public Integer getShuntOrder() {
		return shuntOrder;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
