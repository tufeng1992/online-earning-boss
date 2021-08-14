package com.powerboot.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 运营短信发送记录
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-08-08 11:04:43
 */
public class AdMessageLogDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	private Long id;
	//发送手机号
	private String mobile;
	//发送人
	private String from;
	//发送状态 0：pengding、1：success、2：fail
	private Integer sendStatus;
	//发送内容
	private String msg;
	//移动虚拟运营商
	private String mvno;
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
	 * 设置：发送手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：发送手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：发送人
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * 获取：发送人
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * 设置：发送状态 0：pengding、1：success、2：fail
	 */
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	/**
	 * 获取：发送状态 0：pengding、1：success、2：fail
	 */
	public Integer getSendStatus() {
		return sendStatus;
	}
	/**
	 * 设置：发送内容
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 获取：发送内容
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置：移动虚拟运营商
	 */
	public void setMvno(String mvno) {
		this.mvno = mvno;
	}
	/**
	 * 获取：移动虚拟运营商
	 */
	public String getMvno() {
		return mvno;
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
