package com.powerboot.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 短信验证码表
 * 
 */
public class SmsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	private Long id;
	//ip
	private String ip;
	//APP id
	private String appId;
	//手机号
	private String mobile;
	//验证码
	private String verCode;
	//发送结果
	private String sendResult;
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
	 * 设置：ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置：APP id
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：APP id
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：验证码
	 */
	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}
	/**
	 * 获取：验证码
	 */
	public String getVerCode() {
		return verCode;
	}
	/**
	 * 设置：发送结果
	 */
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	/**
	 * 获取：发送结果
	 */
	public String getSendResult() {
		return sendResult;
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
