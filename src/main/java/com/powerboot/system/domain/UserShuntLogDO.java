package com.powerboot.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户分流信息记录
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
public class UserShuntLogDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//销售人员id
	private Long saleId;
	//分流类型 1.系统分配、2.后台分配、3.用户邀请
	private Integer shuntType;
	//用户id
	private Long userId;
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
	 * 设置：分流类型 1.系统分配、2.后台分配、3.用户邀请
	 */
	public void setShuntType(Integer shuntType) {
		this.shuntType = shuntType;
	}
	/**
	 * 获取：分流类型 1.系统分配、2.后台分配、3.用户邀请
	 */
	public Integer getShuntType() {
		return shuntType;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
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
