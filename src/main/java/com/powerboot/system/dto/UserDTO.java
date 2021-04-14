package com.powerboot.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class UserDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    //主键id
    @ApiModelProperty(value = "主键id")
    private Long id;
    //昵称
    @ApiModelProperty(value = "昵称")
    private String nikeName;
    //设备号
    @ApiModelProperty(value = "设备号")
    private String deviceNumber;
    //注册渠道: 网页端 或 android端
    @ApiModelProperty(value = "appId")
    private String appId;
    //角色:1-销售,2-普通用户
    @ApiModelProperty(value = "角色:1-销售,2-普通用户")
    private Integer role;
    //手机号
    @ApiModelProperty(value = "手机号")
    private String mobile;
    //密码,不序列化
    @ApiModelProperty(value = "密码", hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    //交易密码
    @ApiModelProperty(value = "交易密码", hidden = true)
    private String fundPassword;
    //会员vip等级 1:lv1、 2:lv2、 3:lv3
    @ApiModelProperty(value = "会员vip等级 1:lv1、 2:lv2、 3:lv3")
    private Integer memberLevel;
    //余额
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;
    //上级id
    @ApiModelProperty(value = "上级id")
    private Long parentId;
    //身份证
    @ApiModelProperty(value = "身份证")
    private String identId;
    //银行卡号
    @ApiModelProperty(value = "银行卡号")
    private String accountNumber;
    //银行手机号
    @ApiModelProperty(value = "银行手机号")
    private String accountPhone;
    //银行卡IFSC账号
    @ApiModelProperty(value = "银行卡IFSC账号")
    private String accountIfsc;
    //银行卡申请人名字
    @ApiModelProperty(value = "银行卡申请人名字")
    private String name;
    //绑卡状态：0：未绑定  1：绑定
    @ApiModelProperty(value = "绑卡状态：0：未绑定  1：绑定")
    private Integer bindStatus;
    //绑卡时间
    @ApiModelProperty(value = "绑卡时间")
    private LocalDateTime bindTime;
    //销售id
    @ApiModelProperty(value = "销售id")
    private Long saleId;
    //邀请码
    @ApiModelProperty(value = "邀请码")
    private String referralCode;
    @ApiModelProperty(value = "razorpay联系人id")
    private String contactsId;
    @ApiModelProperty(value = "razorpay联系人虚拟资金账户id")
    private String fundAccountId;
    @ApiModelProperty(value = "是否已完成首冲 0-未完成 1-已完成")
    private Integer firstRecharge;

    @ApiModelProperty(value = "提现开关 0-关 1-开")
    private Integer withdrawCheck;
    //创建时间
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    //更新时间
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    //版本号
    @ApiModelProperty(value = "版本号")
    private Integer version;
    @ApiModelProperty(value = "email邮箱")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWithdrawCheck() {
        return withdrawCheck;
    }

    public void setWithdrawCheck(Integer withdrawCheck) {
        this.withdrawCheck = withdrawCheck;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(String fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }


    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFundPassword() {
        return fundPassword;
    }

    public void setFundPassword(String fundPassword) {
        this.fundPassword = fundPassword;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getIdentId() {
        return identId;
    }

    public void setIdentId(String identId) {
        this.identId = identId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountIfsc() {
        return accountIfsc;
    }

    public void setAccountIfsc(String accountIfsc) {
        this.accountIfsc = accountIfsc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public LocalDateTime getBindTime() {
        return bindTime;
    }

    public void setBindTime(LocalDateTime bindTime) {
        this.bindTime = bindTime;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Integer getFirstRecharge() {
        return firstRecharge;
    }

    public void setFirstRecharge(Integer firstRecharge) {
        this.firstRecharge = firstRecharge;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
