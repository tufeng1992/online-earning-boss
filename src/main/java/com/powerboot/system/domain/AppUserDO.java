package com.powerboot.system.domain;

import com.powerboot.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户表
 */
@Data
public class AppUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键id
    @Excel(name = "用户id")
    private Long id;
    //设备号
    @Excel(name = "设备号")
    private String deviceNumber;
    //昵称
    @Excel(name = "昵称")
    private String nikeName;
    //注册渠道: 网页端 或 android端
    private String appId;
    //角色:1-销售,2-普通用户
    private Integer role;
    //手机号
    @Excel(name = "手机号")
    private String mobile;
    //登陆密码
    private String password;
    //交易密码
    private String fundPassword;
    //会员vip等级 1:lv1、 2:lv2、 3:lv3
    @Excel(name = "会员vip等级 1:lv1、 2:lv2、 3:lv3")
    private Integer memberLevel;
    //余额
    @Excel(name = "余额")
    private BigDecimal balance;
    //上级代理人id
    @Excel(name = "上级代理人id")
    private Long parentId;
    //身份证
    private String identId;
    //银行卡账号
    @Excel(name = "银行卡账号")
    private String accountNumber;
    //银行卡关联手机
    @Excel(name = "银行卡关联手机")
    private String accountPhone;
    //银行卡IFSC账号
    private String accountIfsc;
    //银行卡申请人名字
    @Excel(name = "银行卡申请人名字")
    private String name;
    //绑卡状态：0：未绑定  1：绑定
    @Excel(name = "绑卡状态：0：未绑定  1：绑定")
    private Integer bindStatus;
    //绑卡时间
    @Excel(name = "绑卡时间")
    private Date bindTime;
    //销售人员id
    @Excel(name = "销售人员id")
    private Long saleId;
    //邀请码
    @Excel(name = "邀请码")
    private String referralCode;
    //创建时间
    @Excel(name = "创建时间")
    private Date createTime;
    //更新时间
    private Date updateTime;
    //版本号
    private Integer version;
    //razorpay联系人id
    private String contactsId;
    //razorpay联系人虚拟资金账户id
    private String fundaccountId;
    //是否已完成首冲 0-未完成 1-已完成
    @Excel(name = "是否已完成首冲 0-未完成 1-已完成")
    private Integer firstRecharge;
    //提现开关0-关 1-开
    private Integer withdrawCheck;
    private Integer sdSwitch;
    private Integer lxSwitch;

    private String withdrawCheckStr;

    private String email;

    private String address;

    //充值开关 0-关 1-开
    private Integer rechargeCheck;

    //是否黑名单 0-否 1-是
    @Excel(name = "是否黑名单 0-否 1-是")
    private Integer blackFlag;

    //分享开关 0-关 1-开
    private Integer shareFlag;

    //是否允许登录 0-不允许 1-允许
    @Excel(name = "是否允许登录 0-不允许 1-允许")
    private Integer loginFlag;

    //客服
    private String saleMobile;

    private String teamFlag;

    /**
     * 用户联系销售人员id
     */
    @Excel(name = "用户联系销售人员id")
    private Long contactSaleId;

    /**
     * 注册sdk类型
     */
    @Excel(name = "注册sdk类型")
    private String sdkType;

    /**
     * 是否已完成刷单 0-未完成 1-已完成
     */
    private Integer firstTask;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    public String getTeamFlag() {
        return teamFlag;
    }

    public void setTeamFlag(String teamFlag) {
        this.teamFlag = teamFlag;
    }

    public String getSaleMobile() {
        return saleMobile;
    }

    public void setSaleMobile(String saleMobile) {
        this.saleMobile = saleMobile;
    }

    public Integer getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(Integer loginFlag) {
        this.loginFlag = loginFlag;
    }

    public Integer getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(Integer blackFlag) {
        this.blackFlag = blackFlag;
    }

    public Integer getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(Integer shareFlag) {
        this.shareFlag = shareFlag;
    }

    public Integer getRechargeCheck() {
        return rechargeCheck;
    }

    public void setRechargeCheck(Integer rechargeCheck) {
        this.rechargeCheck = rechargeCheck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSdSwitch() {
        return sdSwitch;
    }

    public void setSdSwitch(Integer sdSwitch) {
        this.sdSwitch = sdSwitch;
    }

    public Integer getLxSwitch() {
        return lxSwitch;
    }

    public void setLxSwitch(Integer lxSwitch) {
        this.lxSwitch = lxSwitch;
    }

    public String getWithdrawCheckStr() {
        return withdrawCheckStr;
    }

    public void setWithdrawCheckStr(String withdrawCheckStr) {
        this.withdrawCheckStr = withdrawCheckStr;
    }

    public Integer getWithdrawCheck() {
        return withdrawCheck;
    }

    public void setWithdrawCheck(Integer withdrawCheck) {
        this.withdrawCheck = withdrawCheck;
        if (withdrawCheck == 0) {
            setWithdrawCheckStr("关闭");
        } else if (withdrawCheck == 1) {
            setWithdrawCheckStr("开启");
        }
    }

    /**
     * 获取：主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：设备号
     */
    public String getDeviceNumber() {
        return deviceNumber;
    }

    /**
     * 设置：设备号
     */
    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    /**
     * 获取：昵称
     */
    public String getNikeName() {
        return nikeName;
    }

    /**
     * 设置：昵称
     */
    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    /**
     * 获取：注册渠道: 网页端 或 android端
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置：注册渠道: 网页端 或 android端
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取：角色:1-销售,2-普通用户
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置：角色:1-销售,2-普通用户
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 获取：手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置：手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：登陆密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：登陆密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：交易密码
     */
    public String getFundPassword() {
        return fundPassword;
    }

    /**
     * 设置：交易密码
     */
    public void setFundPassword(String fundPassword) {
        this.fundPassword = fundPassword;
    }

    /**
     * 获取：会员vip等级 1:lv1、 2:lv2、 3:lv3
     */
    public Integer getMemberLevel() {
        return memberLevel;
    }

    /**
     * 设置：会员vip等级 1:lv1、 2:lv2、 3:lv3
     */
    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    /**
     * 获取：余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置：余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取：上级代理人id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置：上级代理人id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：身份证
     */
    public String getIdentId() {
        return identId;
    }

    /**
     * 设置：身份证
     */
    public void setIdentId(String identId) {
        this.identId = identId;
    }

    /**
     * 获取：银行卡账号
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 设置：银行卡账号
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 获取：银行卡关联手机
     */
    public String getAccountPhone() {
        return accountPhone;
    }

    /**
     * 设置：银行卡关联手机
     */
    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    /**
     * 获取：银行卡IFSC账号
     */
    public String getAccountIfsc() {
        return accountIfsc;
    }

    /**
     * 设置：银行卡IFSC账号
     */
    public void setAccountIfsc(String accountIfsc) {
        this.accountIfsc = accountIfsc;
    }

    /**
     * 获取：银行卡申请人名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：银行卡申请人名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：绑卡状态：0：未绑定  1：绑定
     */
    public Integer getBindStatus() {
        return bindStatus;
    }

    /**
     * 设置：绑卡状态：0：未绑定  1：绑定
     */
    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    /**
     * 获取：绑卡时间
     */
    public Date getBindTime() {
        return bindTime;
    }

    /**
     * 设置：绑卡时间
     */
    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    /**
     * 获取：销售人员id
     */
    public Long getSaleId() {
        return saleId;
    }

    /**
     * 设置：销售人员id
     */
    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    /**
     * 获取：邀请码
     */
    public String getReferralCode() {
        return referralCode;
    }

    /**
     * 设置：邀请码
     */
    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置：更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置：版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取：razorpay联系人id
     */
    public String getContactsId() {
        return contactsId;
    }

    /**
     * 设置：razorpay联系人id
     */
    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    /**
     * 获取：razorpay联系人虚拟资金账户id
     */
    public String getFundaccountId() {
        return fundaccountId;
    }

    /**
     * 设置：razorpay联系人虚拟资金账户id
     */
    public void setFundaccountId(String fundaccountId) {
        this.fundaccountId = fundaccountId;
    }

    /**
     * 获取：是否已完成首冲 0-未完成 1-已完成
     */
    public Integer getFirstRecharge() {
        return firstRecharge;
    }

    /**
     * 设置：是否已完成首冲 0-未完成 1-已完成
     */
    public void setFirstRecharge(Integer firstRecharge) {
        this.firstRecharge = firstRecharge;
    }
}
