package com.powerboot.system.response;

import com.powerboot.system.domain.BalanceDO;

public class BalanceResponse extends BalanceDO {

    private String typeStr;

    private String statusStr;

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
