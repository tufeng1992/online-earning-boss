package com.powerboot.system.consts;

/**
 * 字典配置
 **/
public class DictConsts {

    //字典缓存存活时间
    public static final Integer DICT_CACHE_LIVE_TIME = 60 * 60 * 24 * 365;
    public static final String HOST_IP = "127.0.0.1";

    /**
     * 域名
     */
    public static final String DOMIAN_URL = "DOMIAN_URL:%s";

    /**
     * 风控
     */
    //是否开启IP黑名名单 0-关闭 1-开启
    public static final String OPEN_IP_BLACK_LIST = "OPEN_IP_BLACK_LIST";
    //IP黑名单
    public static final String IP_BLACK_LIST = "IP_BLACK_LIST";
    //黑名单拦截提示
    public static final String RISK_USER_BLACK_TIP = "RISK_USER_BLACK_TIP";
    //用户被禁止登录
    public static final String USER_LOGIN_FLAG = "USER_LOGIN_FLAG:%s";
    //风控黑名单
    public static final String BLACK_USER_ID = "BLACK_USER:ID:%s";

    /**
     * 验证码配置
     */
    //验证码位数
    public static final String VER_CODE_LENGTH = "VER_CODE_LENGTH";

    //短信重发间隔
    public static final String SMS_RESEND_TIME = "SMS_RESEND_TIME";

    //同一ip指定时间内发送短信最大次数
    public static final String IP_SEND_MAX_COUNT = "IP_SEND_MAX_COUNT";

    //同一手机号指定时间内发送短信最大次数
    public static final String PHONE_SEND_MAX_COUNT = "PHONE_SEND_MAX_COUNT";

    //验证码有效时间
    public static final String VER_CODE_LIVE_TIME = "VER_CODE_LIVE_TIME";

    //同一ip发送短信间隔时间
    public static final String IP_SEND_LIVE_TIME = "IP_SEND_LIVE_TIME";

    //同一手机号发送短信间隔时间
    public static final String PHONE_SEND_LIVE_TIME = "PHONE_SEND_LIVE_TIME";

    /**
     * 提现配置
     */
    public static final String WITHDRAW_START_TIME = "WITHDRAW_START_TIME";
    public static final String WITHDRAW_END_TIME = "WITHDRAW_END_TIME";
    public static final String WITHDRAW_TIME_TIP = "WITHDRAW_TIME_TIP";
    public static final String WITHDRAW_USER_CLOSE_TIP = "WITHDRAW_USER_CLOSE_TIP";

    /**
     * 支付配置
     */
    //支付开关1-开,0-关
    public static final String PAY_SWITCH = "PAY_SWITCH";

    //系统支付关闭通知文案
    public static final String PAY_CLOSE_CONTENT = "PAY_CLOSE_CONTENT";

    //个人支付关闭通知文案
    public static final String PAY_CLOSE_USER_CONTENT = "PAY_CLOSE_USER_CONTENT";

    //提现审批开关 0-关 1-开
    public static final String APPLY_SWITCH = "APPLY_SWITCH";

    //邀请减半开关 0：关闭 其他数字表示注册天数条件
    public static final String INVITE_FLAG_HALF = "INVITE_FLAG_HALF";

    //价格区间
    public static final String PRICE_SECTION = "PRICE_SECTION";

    //封盘开关 0-关 1-开
    public static final String GO_PAY_SWITCH = "GO_PAY_SWITCH";

    //封盘开始时间
    public static final String GO_PAY_DATE = "GO_PAY_DATE";

    //封盘提示文案
    public static final String GO_PAY_TIP = "GO_PAY_TIP";

    //VIP1 单次提现最大限额
    public static final String VIP1_SINGLE_MAX_WITHDRAW_QUOTA = "VIP1_SINGLE_MAX_WITHDRAW_QUOTA";
    //VIP2 单次提现最大限额
    public static final String VIP2_SINGLE_MAX_WITHDRAW_QUOTA = "VIP2_SINGLE_MAX_WITHDRAW_QUOTA";
    //VIP3 单次提现最大限额
    public static final String VIP3_SINGLE_MAX_WITHDRAW_QUOTA = "VIP3_SINGLE_MAX_WITHDRAW_QUOTA";
    //VIP1 每日提现最大次数限制
    public static final String VIP1_TODAY_MAX_WITHDRAW_COUNT = "VIP1_TODAY_MAX_WITHDRAW_COUNT";
    //VIP2 每日提现最大次数限制
    public static final String VIP2_TODAY_MAX_WITHDRAW_COUNT = "VIP2_TODAY_MAX_WITHDRAW_COUNT";
    //VIP3 每日提现最大次数限制
    public static final String VIP3_TODAY_MAX_WITHDRAW_COUNT = "VIP3_TODAY_MAX_WITHDRAW_COUNT";
    //系统每日最大提现限额
    public static final String SYS_TODAY_MAX_WITHDRAW_QUOTA = "SYS_TODAY_MAX_WITHDRAW_QUOTA";
    //单笔最低提现限额
    public static final String SINGE_LOW_WITHDRAW_QUOTA = "SINGE_LOW_WITHDRAW_QUOTA";
    //系统提现开关0-关(不允许提现) 1-开(允许提现)
    public static final String SYS_WITHDRAWAL_CHECK = "SYS_WITHDRAWAL_CHECK";
    //系统体现提示语
    public static final String SYS_WITHDRAWAL_CHECK_TIPS = "SYS_WITHDRAWAL_CHECK_TIPS";

    /**
     * 支付渠道 1-跳转支付链接 2-三方rzp
     */
    public static final String PAY_CHANNEL = "PAY_CHANNEL";

    /**
     * 支付渠道分支
     */
    public static final String PAY_CHANNEL_BRANCH = "PAY_CHANNEL_BRANCH";

    //支付渠道1-四方 2-三方rzp
    public static final String PAYOUT_CHANNEL = "PAYOUT_CHANNEL";
    //支付渠道分支
    public static final String PAYOUT_CHANNEL_BRANCH = "PAYOUT_CHANNEL_BRANCH";

    //相同ip注册最大个数
    public static final String IP_REGISTER_MAX_COUNT = "IP_REGISTER_MAX_COUNT";

    //钱包警告金额
    public static final String WALLET_WARNING_AMOUNT = "WALLET_WARNING_AMOUNT";

    //提现警告金额
    public static final String WITHDRAW_WARNING_AMOUNT = "WITHDRAW_WARNING_AMOUNT";


}