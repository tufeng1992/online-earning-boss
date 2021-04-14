package com.powerboot.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 功能简介表
 * 
 */
public class IncomeMethodsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//标题
	private String title;
	//内容
	private String content;
	//是否需要描红 0-不需要 1-需要
	private Integer showRed;
	//优先级,越大越前面
	private Integer sort;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：是否需要描红 0-不需要 1-需要
	 */
	public void setShowRed(Integer showRed) {
		this.showRed = showRed;
	}
	/**
	 * 获取：是否需要描红 0-不需要 1-需要
	 */
	public Integer getShowRed() {
		return showRed;
	}
	/**
	 * 设置：优先级,越大越前面
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：优先级,越大越前面
	 */
	public Integer getSort() {
		return sort;
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
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
