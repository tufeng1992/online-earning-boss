package com.powerboot.system.response;

import com.powerboot.system.domain.OrderDO;

public class TaskOrderResponse extends OrderDO {

    private String orderStatusStr;

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }
}
