package com.powerboot.system.dto;

import com.powerboot.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdSendExcelMsgDTO implements Serializable {

    @Excel(name = "msg")
    private String msg;


}
