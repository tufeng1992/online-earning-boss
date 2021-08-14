package com.powerboot.utils.infobip.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDTO implements Serializable {

    private String tel;

    private String from;

    private String msg;

}
