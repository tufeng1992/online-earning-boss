package com.powerboot.system.response;

import com.powerboot.system.domain.AppUserDO;

/**
 * 用户表
 */
public class AppUserResponse extends AppUserDO {

    private String roleName;

    private String bindStatusStr;

    private String firstRechargeStr;

    private String blackFlagStr;

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
