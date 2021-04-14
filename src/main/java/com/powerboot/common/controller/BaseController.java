package com.powerboot.common.controller;

import com.powerboot.system.domain.UserDO;
import org.springframework.stereotype.Controller;
import com.powerboot.common.utils.ShiroUtils;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}