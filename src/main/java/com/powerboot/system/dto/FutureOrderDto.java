package com.powerboot.system.dto;

public class FutureOrderDto {

    private String date;

    private String lastDate;

    private String count;

    private String amount;

    private String interest;

    private int showRed = 0;

    public int getShowRed() {
        return showRed;
    }

    public void setShowRed(int showRed) {
        this.showRed = showRed;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
