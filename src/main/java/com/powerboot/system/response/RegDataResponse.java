package com.powerboot.system.response;

import java.math.BigDecimal;

public class RegDataResponse {

    private String date = "";

    private BigDecimal money = new BigDecimal("0.00");

    private BigDecimal moneyUsa= new BigDecimal("0.00");

    private BigDecimal moneyChi= new BigDecimal("0.00");

    private Integer regNum = 0;

    private Integer faceNum = 0;

    private BigDecimal facePer= new BigDecimal("0.00");

    private Integer baseNum = 0;

    private BigDecimal basePer= new BigDecimal("0.00");

    private Integer bankNum = 0;

    private BigDecimal bankPer= new BigDecimal("0.00");

    private Integer payNum = 0;

    private BigDecimal payPer= new BigDecimal("0.00");

    private Integer smsCount = 0;

    private BigDecimal smsMoney= new BigDecimal("0.00");

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoneyUsa() {
        return moneyUsa;
    }

    public void setMoneyUsa(BigDecimal moneyUsa) {
        this.moneyUsa = moneyUsa;
    }

    public BigDecimal getMoneyChi() {
        return moneyChi;
    }

    public void setMoneyChi(BigDecimal moneyChi) {
        this.moneyChi = moneyChi;
    }

    public Integer getRegNum() {
        return regNum;
    }

    public void setRegNum(Integer regNum) {
        this.regNum = regNum;
    }

    public Integer getFaceNum() {
        return faceNum;
    }

    public void setFaceNum(Integer faceNum) {
        this.faceNum = faceNum;
    }

    public BigDecimal getFacePer() {
        return facePer;
    }

    public void setFacePer(BigDecimal facePer) {
        this.facePer = facePer;
    }

    public Integer getBaseNum() {
        return baseNum;
    }

    public void setBaseNum(Integer baseNum) {
        this.baseNum = baseNum;
    }

    public BigDecimal getBasePer() {
        return basePer;
    }

    public void setBasePer(BigDecimal basePer) {
        this.basePer = basePer;
    }

    public Integer getBankNum() {
        return bankNum;
    }

    public void setBankNum(Integer bankNum) {
        this.bankNum = bankNum;
    }

    public BigDecimal getBankPer() {
        return bankPer;
    }

    public void setBankPer(BigDecimal bankPer) {
        this.bankPer = bankPer;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public BigDecimal getPayPer() {
        return payPer;
    }

    public void setPayPer(BigDecimal payPer) {
        this.payPer = payPer;
    }

    public Integer getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(Integer smsCount) {
        this.smsCount = smsCount;
    }

    public BigDecimal getSmsMoney() {
        return smsMoney;
    }

    public void setSmsMoney(BigDecimal smsMoney) {
        this.smsMoney = smsMoney;
    }
}
