package com.powerboot.system.consts;

public enum FinancialOrderStatusEnum {

    //状态 1-未到期 2-已到期 3-提前赎回
    UNEXPIRED(1,"未到期"),
    EXPIRED(2,"已到期"),
    CALLED_AWAY(3,"提前赎回");

    private final Integer code;
    private final String msg;

    FinancialOrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code){
        for(FinancialOrderStatusEnum financialOrderStatusEnum:FinancialOrderStatusEnum.values()){
            if(financialOrderStatusEnum.getCode().equals(code)){
                return financialOrderStatusEnum.getMsg();
            }
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
