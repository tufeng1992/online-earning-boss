package com.powerboot.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 系统提出金额记录表
 *
 */
public class PayOutDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    private Long id;
    //用户id
    private Long userId;
    //变动金额
    private BigDecimal amount;
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
     * 设置：变动金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取：变动金额
     */
    public BigDecimal getAmount() {
        return amount;
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
