package com.powerboot.system.response;

import java.math.BigDecimal;

public class FinOrderResp {

    //总转入
    private BigDecimal inCount;
    //今天转入
    private BigDecimal todayInCount;
    //昨日转入
    private BigDecimal yesterdayInCount;

    //总转出
    private BigDecimal outCount;
    //今天转出
    private BigDecimal todayOutCount;
    //昨日转出
    private BigDecimal yesterdayOutCount;

    //总收益
    private BigDecimal moneyCount;
    //今天收益
    private BigDecimal todayMoneyCount;
    //昨日收益
    private BigDecimal yesterdayMoneyCount;

    public BigDecimal getInCount() {
        return inCount;
    }

    public void setInCount(BigDecimal inCount) {
        this.inCount = inCount;
    }

    public BigDecimal getTodayInCount() {
        return todayInCount;
    }

    public void setTodayInCount(BigDecimal todayInCount) {
        this.todayInCount = todayInCount;
    }

    public BigDecimal getYesterdayInCount() {
        return yesterdayInCount;
    }

    public void setYesterdayInCount(BigDecimal yesterdayInCount) {
        this.yesterdayInCount = yesterdayInCount;
    }

    public BigDecimal getOutCount() {
        return outCount;
    }

    public void setOutCount(BigDecimal outCount) {
        this.outCount = outCount;
    }

    public BigDecimal getTodayOutCount() {
        return todayOutCount;
    }

    public void setTodayOutCount(BigDecimal todayOutCount) {
        this.todayOutCount = todayOutCount;
    }

    public BigDecimal getYesterdayOutCount() {
        return yesterdayOutCount;
    }

    public void setYesterdayOutCount(BigDecimal yesterdayOutCount) {
        this.yesterdayOutCount = yesterdayOutCount;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }

    public BigDecimal getTodayMoneyCount() {
        return todayMoneyCount;
    }

    public void setTodayMoneyCount(BigDecimal todayMoneyCount) {
        this.todayMoneyCount = todayMoneyCount;
    }

    public BigDecimal getYesterdayMoneyCount() {
        return yesterdayMoneyCount;
    }

    public void setYesterdayMoneyCount(BigDecimal yesterdayMoneyCount) {
        this.yesterdayMoneyCount = yesterdayMoneyCount;
    }
}
