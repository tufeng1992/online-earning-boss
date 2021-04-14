package com.powerboot.system.response;

import java.math.BigDecimal;
import java.util.List;

public class RegTotalDataResponse {

    private List<RegDataResponse> regDataResponseList;
    private BigDecimal exchangeUsa;
    private BigDecimal exchangeChi;
    private String createTimeIndia;
    private String createTimeChina;

    public List<RegDataResponse> getRegDataResponseList() {
        return regDataResponseList;
    }

    public void setRegDataResponseList(List<RegDataResponse> regDataResponseList) {
        this.regDataResponseList = regDataResponseList;
    }

    public BigDecimal getExchangeUsa() {
        return exchangeUsa;
    }

    public void setExchangeUsa(BigDecimal exchangeUsa) {
        this.exchangeUsa = exchangeUsa;
    }

    public BigDecimal getExchangeChi() {
        return exchangeChi;
    }

    public void setExchangeChi(BigDecimal exchangeChi) {
        this.exchangeChi = exchangeChi;
    }

    public String getCreateTimeIndia() {
        return createTimeIndia;
    }

    public void setCreateTimeIndia(String createTimeIndia) {
        this.createTimeIndia = createTimeIndia;
    }

    public String getCreateTimeChina() {
        return createTimeChina;
    }

    public void setCreateTimeChina(String createTimeChina) {
        this.createTimeChina = createTimeChina;
    }
}
