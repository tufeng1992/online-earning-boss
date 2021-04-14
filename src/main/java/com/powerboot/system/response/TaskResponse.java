package com.powerboot.system.response;

public class TaskResponse {

    //总数
    private Integer count;
    private String countRate;
    //昨日总数
    private Integer yesterdayCount;
    private String yesterdayCountRate;
    //今天总数
    private Integer localCount;
    private String localCountRate;

    public String getCountRate() {
        return countRate;
    }

    public void setCountRate(String countRate) {
        this.countRate = countRate;
    }

    public String getYesterdayCountRate() {
        return yesterdayCountRate;
    }

    public void setYesterdayCountRate(String yesterdayCountRate) {
        this.yesterdayCountRate = yesterdayCountRate;
    }

    public String getLocalCountRate() {
        return localCountRate;
    }

    public void setLocalCountRate(String localCountRate) {
        this.localCountRate = localCountRate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getYesterdayCount() {
        return yesterdayCount;
    }

    public void setYesterdayCount(Integer yesterdayCount) {
        this.yesterdayCount = yesterdayCount;
    }

    public Integer getLocalCount() {
        return localCount;
    }

    public void setLocalCount(Integer localCount) {
        this.localCount = localCount;
    }
}
