package com.powerboot.system.domain;

import com.powerboot.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class DataBossVo implements Serializable {

    private Integer id;

    private Date generatedDate;

    private String date;

    private Integer vipCount;

    private Integer vip1Count;

    private Integer vip2Count;

    private Integer vip3Count;

    private Integer vip4Count;

    private Integer vip5Count;

    private Integer vipValidCount;

    private BigDecimal rechargeAmount;

    private BigDecimal vipBalanceCount;

    private BigDecimal commissionsAmount;

    private BigDecimal financialProfitAmount;

    //新增用户
    private Integer localUserCount;
    //充值笔数
    private Integer rechargeCount;
    //提现笔数
    private Integer withdrawCount;
    //提现金额
    private BigDecimal withdrawAmount;
    //购买vip单数
    private Integer vipPayCount;
    //购买vip金额
    private BigDecimal vipPayAmount;
    //首冲奖励
    private BigDecimal firstRechargeAmount;
    //理财转入
    private BigDecimal financialProfitInAmount;
    //理财转出
    private BigDecimal financialProfitOutAmount;
    //理财总金额
    private BigDecimal financialProfitCountAmount;

    private BigDecimal netProfit;

    private Integer showRed;

    //用户裂变
    private Integer userReferral;
    //客服推广
    private Integer saleReferral;

    private Long saleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserReferral() {
        return userReferral;
    }

    public void setUserReferral(Integer userReferral) {
        this.userReferral = userReferral;
    }

    public Integer getSaleReferral() {
        return saleReferral;
    }

    public void setSaleReferral(Integer saleReferral) {
        this.saleReferral = saleReferral;
    }

    public Integer getShowRed() {
        return showRed;
    }

    public void setShowRed(Integer showRed) {
        this.showRed = showRed;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public BigDecimal getFinancialProfitCountAmount() {
        return financialProfitCountAmount;
    }

    public void setFinancialProfitCountAmount(BigDecimal financialProfitCountAmount) {
        this.financialProfitCountAmount = financialProfitCountAmount;
    }

    public Integer getLocalUserCount() {
        return localUserCount;
    }

    public void setLocalUserCount(Integer localUserCount) {
        this.localUserCount = localUserCount;
    }

    public Integer getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(Integer rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public Integer getWithdrawCount() {
        return withdrawCount;
    }

    public void setWithdrawCount(Integer withdrawCount) {
        this.withdrawCount = withdrawCount;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Integer getVipPayCount() {
        return vipPayCount;
    }

    public void setVipPayCount(Integer vipPayCount) {
        this.vipPayCount = vipPayCount;
    }

    public BigDecimal getVipPayAmount() {
        return vipPayAmount;
    }

    public void setVipPayAmount(BigDecimal vipPayAmount) {
        this.vipPayAmount = vipPayAmount;
    }

    public BigDecimal getFirstRechargeAmount() {
        return firstRechargeAmount;
    }

    public void setFirstRechargeAmount(BigDecimal firstRechargeAmount) {
        this.firstRechargeAmount = firstRechargeAmount;
    }

    public BigDecimal getFinancialProfitInAmount() {
        return financialProfitInAmount;
    }

    public void setFinancialProfitInAmount(BigDecimal financialProfitInAmount) {
        this.financialProfitInAmount = financialProfitInAmount;
    }

    public BigDecimal getFinancialProfitOutAmount() {
        return financialProfitOutAmount;
    }

    public void setFinancialProfitOutAmount(BigDecimal financialProfitOutAmount) {
        this.financialProfitOutAmount = financialProfitOutAmount;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
        if (generatedDate != null){
            setDate(DateUtils.formatDateYMD(generatedDate));
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getVipCount() {
        return vipCount;
    }

    public void setVipCount(Integer vipCount) {
        this.vipCount = vipCount;
    }

    public Integer getVip1Count() {
        return vip1Count;
    }

    public void setVip1Count(Integer vip1Count) {
        this.vip1Count = vip1Count;
    }

    public Integer getVip2Count() {
        return vip2Count;
    }

    public void setVip2Count(Integer vip2Count) {
        this.vip2Count = vip2Count;
    }

    public Integer getVip3Count() {
        return vip3Count;
    }

    public void setVip3Count(Integer vip3Count) {
        this.vip3Count = vip3Count;
    }

    public Integer getVip4Count() {
        return vip4Count;
    }

    public void setVip4Count(Integer vip4Count) {
        this.vip4Count = vip4Count;
    }

    public Integer getVip5Count() {
        return vip5Count;
    }

    public void setVip5Count(Integer vip5Count) {
        this.vip5Count = vip5Count;
    }

    public Integer getVipValidCount() {
        return vipValidCount;
    }

    public void setVipValidCount(Integer vipValidCount) {
        this.vipValidCount = vipValidCount;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getVipBalanceCount() {
        return vipBalanceCount;
    }

    public void setVipBalanceCount(BigDecimal vipBalanceCount) {
        this.vipBalanceCount = vipBalanceCount;
    }

    public BigDecimal getCommissionsAmount() {
        return commissionsAmount;
    }

    public void setCommissionsAmount(BigDecimal commissionsAmount) {
        this.commissionsAmount = commissionsAmount;
    }

    public BigDecimal getFinancialProfitAmount() {
        return financialProfitAmount;
    }

    public void setFinancialProfitAmount(BigDecimal financialProfitAmount) {
        this.financialProfitAmount = financialProfitAmount;
    }

    @Override
    public String toString() {
        return "DataBossVo{" +
                "generatedDate=" + generatedDate +
                ", date='" + date + '\'' +
                ", vipCount=" + vipCount +
                ", vip1Count=" + vip1Count +
                ", vip2Count=" + vip2Count +
                ", vip3Count=" + vip3Count +
                ", vip4Count=" + vip4Count +
                ", vip5Count=" + vip5Count +
                ", vipValidCount=" + vipValidCount +
                ", rechargeAmount=" + rechargeAmount +
                ", vipBalanceCount=" + vipBalanceCount +
                ", commissionsAmount=" + commissionsAmount +
                ", financialProfitAmount=" + financialProfitAmount +
                '}';
    }
}
