package com.powerboot.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 支付结果
 * 
 */
public class PayDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	private Long id;
	//用户id
	private Long userId;
	//支付金额
	private BigDecimal amount;
	//支付状态 1-待支付 2-已支付 3-支付失败
	private Integer status;
	//支付类型:1-充值 , 2-购买VIP2 , 3-购买VIP3,99-提现
	private Integer type;
	//支付类型描述
	private String typeText;
	//支付状态描述
	private String statusText;
	//第三方支付流水号
	private String thirdNo;
	//第三方支付返回状态
	private String thirdStatus;
	//三方返回描述
	private String thirdResponse;
	//第三方支付回调时间
	private Date thirdCallbackTime;
	//订单号
	private String orderNo;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//运营id
	private Long saleId;

	//销售编号
	private String saleNo;

	//用户手机号
	private String mobile;

	//审批状态 1-待审批 2-审批成功 99-驳回
	private Integer applyStatus;
	//审批原因
	private String remark;

	public String getThirdResponse() {
		return thirdResponse;
	}

	public void setThirdResponse(String thirdResponse) {
		this.thirdResponse = thirdResponse;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

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
	 * 设置：支付金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：支付金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：支付状态 1-待支付 2-已支付 3-支付失败
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：支付状态 1-待支付 2-已支付 3-支付失败
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：第三方支付流水号
	 */
	public void setThirdNo(String thirdNo) {
		this.thirdNo = thirdNo;
	}
	/**
	 * 获取：第三方支付流水号
	 */
	public String getThirdNo() {
		return thirdNo;
	}
	/**
	 * 设置：第三方支付返回状态
	 */
	public void setThirdStatus(String thirdStatus) {
		this.thirdStatus = thirdStatus;
	}
	/**
	 * 获取：第三方支付返回状态
	 */
	public String getThirdStatus() {
		return thirdStatus;
	}
	/**
	 * 设置：第三方支付回调时间
	 */
	public void setThirdCallbackTime(Date thirdCallbackTime) {
		this.thirdCallbackTime = thirdCallbackTime;
	}
	/**
	 * 获取：第三方支付回调时间
	 */
	public Date getThirdCallbackTime() {
		return thirdCallbackTime;
	}
	/**
	 * 设置：订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取：订单号
	 */
	public String getOrderNo() {
		return orderNo;
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

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
}
