package com.powerboot.system.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayResp implements Serializable {
    //总数
    private Integer count;
    //总金额
    private BigDecimal amount;
    //今天总数
    private Integer localCount;
    //今天总金额
    private BigDecimal localAmount;
    //昨日总数
    private Integer yesterdayCount;
    //昨日总金额
    private BigDecimal yesterdayAmount;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getLocalCount() {
        return localCount;
    }

    public void setLocalCount(Integer localCount) {
        this.localCount = localCount;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }

    public Integer getYesterdayCount() {
        return yesterdayCount;
    }

    public void setYesterdayCount(Integer yesterdayCount) {
        this.yesterdayCount = yesterdayCount;
    }

    public BigDecimal getYesterdayAmount() {
        return yesterdayAmount;
    }

    public void setYesterdayAmount(BigDecimal yesterdayAmount) {
        this.yesterdayAmount = yesterdayAmount;
    }
}
