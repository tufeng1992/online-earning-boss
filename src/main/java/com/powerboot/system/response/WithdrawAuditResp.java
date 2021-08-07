package com.powerboot.system.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WithdrawAuditResp implements Serializable {

    //通过总数
    private Integer passCount;
    //通过总金额
    private BigDecimal passAmount;

    //待审核总数
    private Integer waitCount;
    //待审核总金额
    private BigDecimal waitAmount;

}
