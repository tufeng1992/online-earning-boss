package com.powerboot.system.consts;

public enum UserRoleEnum {
    SALE(1,"销售"),
    PUBLIC(2,"普通用户");

    private final Integer code;
    private final String msg;

    UserRoleEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code){
        for(UserRoleEnum userRoleEnum:UserRoleEnum.values()){
            if(userRoleEnum.getCode().equals(code)){
                return userRoleEnum.getMsg();
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
