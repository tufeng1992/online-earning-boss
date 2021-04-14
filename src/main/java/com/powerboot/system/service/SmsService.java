package com.powerboot.system.service;

import com.powerboot.system.domain.SmsDO;

import java.util.List;
import java.util.Map;

/**
 * 短信验证码表
 * 
 */
public interface SmsService {
	
	SmsDO get(Long id);
	
	List<SmsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SmsDO sms);
	
	int update(SmsDO sms);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
