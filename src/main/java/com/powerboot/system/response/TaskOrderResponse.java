package com.powerboot.system.response;

import com.powerboot.system.domain.OrderDO;
import lombok.Data;

@Data
public class TaskOrderResponse extends OrderDO {

    private String orderStatusStr;

    /**
     * 运营编号
     */
    private String saleMobile;
}
