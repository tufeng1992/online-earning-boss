package com.powerboot.system.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class MainResp implements Serializable {
    //用户余额
    private PayResp userAmount;
    //用户数量
    private UserCountResp userCountResp;
    //vip购买
    private PayResp VIPPay;
    //充值
    private PayResp charge;
    //实际充值(含税)
    private PayResp relCharge;
    //实际充值(扣税)
    private PayResp relChargeTaxDeduction;
    //提现
    private PayResp withdraw;
    //实际提现(含税)
    private PayResp relWithdraw;
    //实际提现(扣税)
    private PayResp relWithdrawTaxDeduction;
    //净利润
    private PayResp netProfit;
    //首冲奖励
    private PayResp firstRechargeAmount;
    //理财
    private FinOrderResp finOrderResp;
    //系统提现
    private BigDecimal sysPayOut;
    //刷单奖励
    private OrderResp orderResp;
    //用户加钱
    private BigDecimal addAmount;
    //参与刷单日活
    private TaskResponse taskResponse;
    //用户裂变
    private UserCountResp userReferral;
    //客服邀请
    private UserCountResp saleReferral;
    //用户激活
    private TaskResponse userLoginRes;
    //未归属客服
    private TaskResponse noSaleUser;

    public TaskResponse getNoSaleUser() {
        return noSaleUser;
    }

    public void setNoSaleUser(TaskResponse noSaleUser) {
        this.noSaleUser = noSaleUser;
    }

    public TaskResponse getUserLoginRes() {
        return userLoginRes;
    }

    public void setUserLoginRes(TaskResponse userLoginRes) {
        this.userLoginRes = userLoginRes;
    }

    public UserCountResp getUserReferral() {
        return userReferral;
    }

    public void setUserReferral(UserCountResp userReferral) {
        this.userReferral = userReferral;
    }

    public UserCountResp getSaleReferral() {
        return saleReferral;
    }

    public void setSaleReferral(UserCountResp saleReferral) {
        this.saleReferral = saleReferral;
    }

    private RechangeResponse rechangeResponse;

    public RechangeResponse getRechangeResponse() {
        return rechangeResponse;
    }

    public void setRechangeResponse(RechangeResponse rechangeResponse) {
        this.rechangeResponse = rechangeResponse;
    }

    public TaskResponse getTaskResponse() {
        return taskResponse;
    }

    public void setTaskResponse(TaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }

    public PayResp getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(PayResp netProfit) {
        this.netProfit = netProfit;
    }

    public PayResp getFirstRechargeAmount() {
        return firstRechargeAmount;
    }

    public void setFirstRechargeAmount(PayResp firstRechargeAmount) {
        this.firstRechargeAmount = firstRechargeAmount;
    }

    public BigDecimal getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(BigDecimal addAmount) {
        this.addAmount = addAmount;
    }

    public OrderResp getOrderResp() {
        return orderResp;
    }

    public void setOrderResp(OrderResp orderResp) {
        this.orderResp = orderResp;
    }

    public PayResp getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(PayResp userAmount) {
        this.userAmount = userAmount;
    }

    public PayResp getVIPPay() {
        return VIPPay;
    }

    public void setVIPPay(PayResp VIPPay) {
        this.VIPPay = VIPPay;
    }

    public PayResp getRelChargeTaxDeduction() {
        return relChargeTaxDeduction;
    }

    public void setRelChargeTaxDeduction(PayResp relChargeTaxDeduction) {
        this.relChargeTaxDeduction = relChargeTaxDeduction;
    }

    public PayResp getRelWithdrawTaxDeduction() {
        return relWithdrawTaxDeduction;
    }

    public void setRelWithdrawTaxDeduction(PayResp relWithdrawTaxDeduction) {
        this.relWithdrawTaxDeduction = relWithdrawTaxDeduction;
    }

    public BigDecimal getSysPayOut() {
        return sysPayOut;
    }

    public void setSysPayOut(BigDecimal sysPayOut) {
        this.sysPayOut = sysPayOut;
    }

    public FinOrderResp getFinOrderResp() {
        return finOrderResp;
    }

    public void setFinOrderResp(FinOrderResp finOrderResp) {
        this.finOrderResp = finOrderResp;
    }

    public UserCountResp getUserCountResp() {
        return userCountResp;
    }

    public void setUserCountResp(UserCountResp userCountResp) {
        this.userCountResp = userCountResp;
    }

    public PayResp getCharge() {
        return charge;
    }

    public void setCharge(PayResp charge) {
        this.charge = charge;
    }

    public PayResp getRelCharge() {
        return relCharge;
    }

    public void setRelCharge(PayResp relCharge) {
        this.relCharge = relCharge;
    }

    public PayResp getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(PayResp withdraw) {
        this.withdraw = withdraw;
    }

    public PayResp getRelWithdraw() {
        return relWithdraw;
    }

    public void setRelWithdraw(PayResp relWithdraw) {
        this.relWithdraw = relWithdraw;
    }
}
