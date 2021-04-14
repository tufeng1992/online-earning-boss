package com.powerboot.system.consts;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleDataExcelModel {

    public static List<String> fields = Lists
        .newArrayList("id", "saleNo", "userLevel", "mobile", "name", "level", "totalAssets",
            "rechargeAmount", "withdrawalAmount", "vaildCount","totalCount", "clickCount", "clickCommission"
            , "superiorId", "regDate");

    public static Map<String, String> headerNameMap = new HashMap<>();

    static {
        headerNameMap.put("id", "用户id");
        headerNameMap.put("saleNo", "运营编号");
        headerNameMap.put("userLevel", "用户层级");
        headerNameMap.put("mobile", "手机号");
        headerNameMap.put("name", "名称");
        headerNameMap.put("level", "会员等级");
        headerNameMap.put("totalAssets", "总资产");
        headerNameMap.put("rechargeAmount", "充值金额");
        headerNameMap.put("withdrawalAmount", "提现金额");
        headerNameMap.put("vaildCount", "有效推广人数");
        headerNameMap.put("totalCount", "总推广人数");
        headerNameMap.put("clickCount", "刷单数");
        headerNameMap.put("clickCommission", "刷单佣金");
        headerNameMap.put("superiorId", "上级id");
        headerNameMap.put("regDate", "注册时间");
    }
}