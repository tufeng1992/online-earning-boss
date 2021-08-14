package com.powerboot.system.dto;

import com.powerboot.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdSendExcelTelDTO implements Serializable {

    @Excel(name = "tel")
    private String tel;


}
