package com.powerboot.client;

import java.io.Serializable;

public class ApplyRequest implements Serializable {
    private String orderNo;
    private String remark;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ApplyRequest{" +
                "orderNo='" + orderNo + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}