package com.powerboot.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 黑名单用户表
 *
 */
public class BlackUserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    private Long id;
    //手机号
    private String mobile;
    //银行卡IFSC账号
    private String ifsc;
    //名字
    private String name;
    //邮箱
    private String email;
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
     * 设置：银行卡IFSC账号
     */
    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    /**
     * 获取：银行卡IFSC账号
     */
    public String getIfsc() {
        return ifsc;
    }

    /**
     * 设置：名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：邮箱
     */
    public String getEmail() {
        return email;
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
