package com.powerboot.system.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayVO implements Serializable {
    //总数
    private Integer count;
    //总金额
    private BigDecimal amount;

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
}
