package com.powerboot.system.response;

import java.io.Serializable;

public class UserCountResp implements Serializable {
    //用户总量
    private Integer userCount;
    //今天新增
    private Integer localCount;
    //昨日新增
    private Integer yesterdayCount;

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getLocalCount() {
        return localCount;
    }

    public void setLocalCount(Integer localCount) {
        this.localCount = localCount;
    }

    public Integer getYesterdayCount() {
        return yesterdayCount;
    }

    public void setYesterdayCount(Integer yesterdayCount) {
        this.yesterdayCount = yesterdayCount;
    }
}
