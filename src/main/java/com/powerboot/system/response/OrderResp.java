package com.powerboot.system.response;

import java.math.BigDecimal;

public class OrderResp {

    //总转入
    private BigDecimal amount;
    //今天转入
    private BigDecimal todayAmount;
    //昨日转入
    private BigDecimal yesterdayAmount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(BigDecimal todayAmount) {
        this.todayAmount = todayAmount;
    }

    public BigDecimal getYesterdayAmount() {
        return yesterdayAmount;
    }

    public void setYesterdayAmount(BigDecimal yesterdayAmount) {
        this.yesterdayAmount = yesterdayAmount;
    }
}
