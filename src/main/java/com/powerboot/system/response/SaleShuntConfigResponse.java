package com.powerboot.system.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 客服分流配置表
 *
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
@Data
public class SaleShuntConfigResponse implements Serializable {
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
	 * 分流用户数
	 */
	private int shuntCount;

}
