package com.powerboot.system.response;

import com.powerboot.system.domain.BalanceDO;
import lombok.Data;

@Data
public class BalanceResponse extends BalanceDO {

    private String typeStr;

    private String statusStr;

    /**
     * 运营编号
     */
    private String saleMobile;

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
