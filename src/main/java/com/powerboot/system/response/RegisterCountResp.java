package com.powerboot.system.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 注册奖励
 */
@Data
public class RegisterCountResp implements Serializable {

    /**
     * 昨天奖励
     */
    private BigDecimal yesterdayAmount;

    /**
     * 今天奖励
     */
    private BigDecimal todayAmount;

}
