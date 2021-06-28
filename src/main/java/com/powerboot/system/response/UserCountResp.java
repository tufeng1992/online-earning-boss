package com.powerboot.system.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserCountResp implements Serializable {
    //用户总量
    private Integer userCount;
    //今天新增
    private Integer localCount;
    //昨日新增
    private Integer yesterdayCount;

    /**
     * 今日激活用户数比例
     */
    private String activateCountRate;

    /**
     * 今日未激活用户数
     */
    private String noActivateTotal;

    /**
     * 昨日激活用户数比例
     */
    private String yesterdayActivateCountRate;

    /**
     * 昨日未激活用户数
     */
    private String yesterdayNoActivateTotal;

    /**
     * 今日无客服归属用户数比例
     */
    private String noContactCountRate;

    /**
     * 今日无客服归属用户数
     */
    private String noContactTotal;

    /**
     * 昨日无客服归属用户数比例
     */
    private String yesterdayNoContactCountRate;

    /**
     * 昨日无客服归属用户数
     */
    private String yesterdayNoContactTotal;

    /**
     * 今日刷单用户数比例
     */
    private String taskUserCountRate;

    /**
     * 今日刷单用户数
     */
    private String taskUserCount;

    /**
     * 昨天刷单用户数比例
     */
    private String yesterdayTaskUserCountRate;

    /**
     * 昨天刷单用户数
     */
    private String yesterdayTaskUserCount;

    /**
     * 今日充值人数比例
     */
    private String rechargeRate;

    /**
     * 今日充值人数
     */
    private String rechargeCount;

    /**
     * 历史充值人数比例
     */
    private String totalRechargeRate;

    /**
     * 历史充值人数
     */
    private String totalRechargeCount;

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
