package com.powerboot.system.consts;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayExportExcelModel {

    public static List<String> fields = Lists
        .newArrayList("type", "createTime", "thirdNo", "amount", "mobile");

    public static Map<String, String> headerNameMap = new HashMap<>();

    static {
        headerNameMap.put("type", "流水类型");
        headerNameMap.put("createTime", "创建时间");
        headerNameMap.put("thirdNo", "第三方流水号");
        headerNameMap.put("amount", "金额");
        headerNameMap.put("mobile", "手机号");
    }
}