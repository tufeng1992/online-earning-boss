package com.powerboot.system.response;

import com.powerboot.system.domain.AppUserDO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户表
 */
@Data
public class AppUserResponse extends AppUserDO {

    private String roleName;

    private String bindStatusStr;

    private String firstRechargeStr;

    private String blackFlagStr;

    /**
     * 是否刷单
     */
    private String firstTaskStr;

    /**
     * 是否登录
     */
    private String loginStatusStr;

    /**
     * 顶级parentId
     */
    private Long topParentId;

    /**
     * 提现总金额
     */
    private BigDecimal withdrawalTotalAmount;

    /**
     * 充值总金额
     */
    private BigDecimal rechargeTotalAmount;

    public String getBlackFlagStr() {
        return blackFlagStr;
    }

    public void setBlackFlagStr(String blackFlagStr) {
        this.blackFlagStr = blackFlagStr;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBindStatusStr() {
        return bindStatusStr;
    }

    public void setBindStatusStr(String bindStatusStr) {
        this.bindStatusStr = bindStatusStr;
    }

    public String getFirstRechargeStr() {
        return firstRechargeStr;
    }

    public void setFirstRechargeStr(String firstRechargeStr) {
        this.firstRechargeStr = firstRechargeStr;
    }
}
