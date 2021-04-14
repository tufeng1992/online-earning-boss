package com.powerboot.system.response;

import com.powerboot.system.domain.FinancialOrderDO;

public class FinancialOrderResponse extends FinancialOrderDO {

    private String orderStatusStr;

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }
}
