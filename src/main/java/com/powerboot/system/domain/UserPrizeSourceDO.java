package com.powerboot.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户奖励关联信息表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
public class UserPrizeSourceDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//用户id
	private Long userId;
	//奖励关联用户id
	private Long prizeSourceUserId;
	//用户奖励id
	private Long userPrizeId;
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
	 * 设置：奖励关联用户id
	 */
	public void setPrizeSourceUserId(Long prizeSourceUserId) {
		this.prizeSourceUserId = prizeSourceUserId;
	}
	/**
	 * 获取：奖励关联用户id
	 */
	public Long getPrizeSourceUserId() {
		return prizeSourceUserId;
	}
	/**
	 * 设置：用户奖励id
	 */
	public void setUserPrizeId(Long userPrizeId) {
		this.userPrizeId = userPrizeId;
	}
	/**
	 * 获取：用户奖励id
	 */
	public Long getUserPrizeId() {
		return userPrizeId;
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
