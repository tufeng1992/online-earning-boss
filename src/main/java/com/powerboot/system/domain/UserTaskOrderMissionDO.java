package com.powerboot.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 刷单任务完成记录表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
public class UserTaskOrderMissionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//用户id
	private Long userId;
	//刷单数量
	private Integer taskOrderNum;
	//刷单总金额
	private BigDecimal taskOrderAmount;
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
	 * 设置：刷单数量
	 */
	public void setTaskOrderNum(Integer taskOrderNum) {
		this.taskOrderNum = taskOrderNum;
	}
	/**
	 * 获取：刷单数量
	 */
	public Integer getTaskOrderNum() {
		return taskOrderNum;
	}
	/**
	 * 设置：刷单总金额
	 */
	public void setTaskOrderAmount(BigDecimal taskOrderAmount) {
		this.taskOrderAmount = taskOrderAmount;
	}
	/**
	 * 获取：刷单总金额
	 */
	public BigDecimal getTaskOrderAmount() {
		return taskOrderAmount;
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
