package com.powerboot.common.domain;

import java.math.BigDecimal;

public class WithdrawalDo {

    //订单id
    private Long id;
    //订单编号
    private String orderNo;

    //用户id
    private Long userId;

    //运营编号
    private String saleNo;

    //上级代理人id
    private Long parentId;

    //手机号
    private String mobile;

    //注册时间
    private String regDate;

    //充值总额
    private BigDecimal rechargeAmount;

    //上次充值时间
    private String lastRechargeDate;

    //提现金额
    private BigDecimal withdrawalAmount;

    //提现申请时间
    private String withdrawalApplyTime;

    //提现审核状态
    private Integer withdrawalAuditStatus;
    //提现审核状态描述
    private String withdrawalAuditStatusDesc;

    //审核失败备注
    private String failReason;

    //提现审核时间
    private String withdrawalAuditTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getLastRechargeDate() {
        return lastRechargeDate;
    }

    public void setLastRechargeDate(String lastRechargeDate) {
        this.lastRechargeDate = lastRechargeDate;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public String getWithdrawalApplyTime() {
        return withdrawalApplyTime;
    }

    public void setWithdrawalApplyTime(String withdrawalApplyTime) {
        this.withdrawalApplyTime = withdrawalApplyTime;
    }

    public Integer getWithdrawalAuditStatus() {
        return withdrawalAuditStatus;
    }

    public void setWithdrawalAuditStatus(Integer withdrawalAuditStatus) {
        this.withdrawalAuditStatus = withdrawalAuditStatus;
    }

    public String getWithdrawalAuditStatusDesc() {
        return withdrawalAuditStatusDesc;
    }

    public void setWithdrawalAuditStatusDesc(String withdrawalAuditStatusDesc) {
        this.withdrawalAuditStatusDesc = withdrawalAuditStatusDesc;
    }

    public String getWithdrawalAuditTime() {
        return withdrawalAuditTime;
    }

    public void setWithdrawalAuditTime(String withdrawalAuditTime) {
        this.withdrawalAuditTime = withdrawalAuditTime;
    }
}
