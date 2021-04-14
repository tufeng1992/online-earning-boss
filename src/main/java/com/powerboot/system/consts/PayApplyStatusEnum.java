package com.powerboot.system.consts;

public enum PayApplyStatusEnum {
    APPLY(1,"审批中"),
    PASS(2,"审批通过"),
    REJECT(99,"审批驳回"),;

    private final Integer code;
    private final String msg;

    PayApplyStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    public static String getDescByCode(Integer code){
        PayApplyStatusEnum[] values = values();
        for (PayApplyStatusEnum singEnum: values) {
            if (singEnum.getCode().equals(code)){
                return singEnum.getMsg();
            }
        }
        return "";
    }
}
