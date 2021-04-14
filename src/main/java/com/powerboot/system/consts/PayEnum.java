package com.powerboot.system.consts;

public enum PayEnum {
    TIMEOUT(-1, "超时未支付"),
    PAYING(1, "支付中"),
    PAID(2, "已支付"),
    FAIL(3, "支付失败");

    private final Integer code;
    private final String msg;

    PayEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getNameByCode(Integer code){
        for (PayEnum singleEnum : values()){
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
