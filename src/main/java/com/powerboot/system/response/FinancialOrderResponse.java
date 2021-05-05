package com.powerboot.system.response;

import com.powerboot.system.domain.FinancialOrderDO;
import lombok.Data;

@Data
public class FinancialOrderResponse extends FinancialOrderDO {

    private String orderStatusStr;

    /**
     * 运营编号
     */
    private String saleMobile;

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }
}
