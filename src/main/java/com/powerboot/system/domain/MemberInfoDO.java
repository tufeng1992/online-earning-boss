package com.powerboot.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 会员卡配置信息表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-06-24 22:07:10
 */
public class MemberInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	private Long id;
	//限额金额数文案
	private String limitAmount;
	//刷单上限文案
	private String orderTimes;
	//手续费文案
	private String feeStr;
	//提现次文案
	private String withdrawTimes;
	//会员充值金额
	private BigDecimal amount;
	//充值类型
	private Integer type;
	//vip描述
	private String vip;
	//图片地址
	private String picUrl;
	//等级描述
	private String vipDesc;
	//购买vip条件描述
	private String buyVipCondition;
	//升级会员邀请人数限制
	private Integer upLimit;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	/**
	 * 设置：主键id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：限额金额数文案
	 */
	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}
	/**
	 * 获取：限额金额数文案
	 */
	public String getLimitAmount() {
		return limitAmount;
	}
	/**
	 * 设置：刷单上限文案
	 */
	public void setOrderTimes(String orderTimes) {
		this.orderTimes = orderTimes;
	}
	/**
	 * 获取：刷单上限文案
	 */
	public String getOrderTimes() {
		return orderTimes;
	}
	/**
	 * 设置：手续费文案
	 */
	public void setFeeStr(String feeStr) {
		this.feeStr = feeStr;
	}
	/**
	 * 获取：手续费文案
	 */
	public String getFeeStr() {
		return feeStr;
	}
	/**
	 * 设置：提现次文案
	 */
	public void setWithdrawTimes(String withdrawTimes) {
		this.withdrawTimes = withdrawTimes;
	}
	/**
	 * 获取：提现次文案
	 */
	public String getWithdrawTimes() {
		return withdrawTimes;
	}
	/**
	 * 设置：会员充值金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：会员充值金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：充值类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：充值类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：vip描述
	 */
	public void setVip(String vip) {
		this.vip = vip;
	}
	/**
	 * 获取：vip描述
	 */
	public String getVip() {
		return vip;
	}
	/**
	 * 设置：图片地址
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取：图片地址
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置：等级描述
	 */
	public void setVipDesc(String vipDesc) {
		this.vipDesc = vipDesc;
	}
	/**
	 * 获取：等级描述
	 */
	public String getVipDesc() {
		return vipDesc;
	}
	/**
	 * 设置：购买vip条件描述
	 */
	public void setBuyVipCondition(String buyVipCondition) {
		this.buyVipCondition = buyVipCondition;
	}
	/**
	 * 获取：购买vip条件描述
	 */
	public String getBuyVipCondition() {
		return buyVipCondition;
	}
	/**
	 * 设置：升级会员邀请人数限制
	 */
	public void setUpLimit(Integer upLimit) {
		this.upLimit = upLimit;
	}
	/**
	 * 获取：升级会员邀请人数限制
	 */
	public Integer getUpLimit() {
		return upLimit;
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
