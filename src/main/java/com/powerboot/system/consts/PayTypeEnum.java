package com.powerboot.system.consts;

public enum PayTypeEnum {
    CHARGE(1, "充值"),
    PAY_VIP2(2, "购买VIP2"),
    PAY_VIP3(3, "购买VIP3"),
    PAY_VIP4(4, "购买VIP4"),
    PAY_VIP5(5, "购买VIP5"),
    WITHDRAW(99, "提现"),
    ;

    private final Integer code;
    private final String msg;

    PayTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getNameByCode(Integer code){
        for (PayTypeEnum singleEnum : values()){
            if (singleEnum.getCode().equals(code)){
                return singleEnum.getMsg();
            }
        }
        return "/";
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
