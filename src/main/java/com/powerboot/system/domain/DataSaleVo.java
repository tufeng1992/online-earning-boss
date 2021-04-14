package com.powerboot.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class DataSaleVo {

    private Long id;
    //手机号
    private String mobile;
    //名称
    private String name;
    //等级
    private String level;
    //总资产
    private BigDecimal totalAssets;
    //充值金额
    private BigDecimal rechargeAmount;
    //提现金额
    private BigDecimal withdrawalAmount;
    //有效推广人数
    private Integer vaildCount;
    //总推广人数
    private Integer totalCount;
    //刷单数
    private Integer clickCount;
    //刷单佣金
    private BigDecimal clickCommission;
    //上级id
    private Integer superiorId;
    //销售id
    private Long saleId;
    //销售编号
    private String saleNo;
    //用户层级
    private Integer userLevel;
    //注册时间
    private String regDate;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Integer getVaildCount() {
        return vaildCount;
    }

    public void setVaildCount(Integer vaildCount) {
        this.vaildCount = vaildCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public BigDecimal getClickCommission() {
        return clickCommission;
    }

    public void setClickCommission(BigDecimal clickCommission) {
        this.clickCommission = clickCommission;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
