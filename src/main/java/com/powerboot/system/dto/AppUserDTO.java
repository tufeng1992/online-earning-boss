package com.powerboot.system.dto;

import com.powerboot.system.domain.AppUserDO;
import java.util.ArrayList;
import java.util.List;

public class AppUserDTO {

    private List<AppUserDO> appUserDOList = new ArrayList<>();

    private Integer smsCount = 0;

    public List<AppUserDO> getAppUserDOList() {
        return appUserDOList;
    }

    public void setAppUserDOList(List<AppUserDO> appUserDOList) {
        this.appUserDOList = appUserDOList;
    }

    public Integer getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(Integer smsCount) {
        this.smsCount = smsCount;
    }
}
