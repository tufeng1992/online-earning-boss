package com.powerboot.system.service;

import com.powerboot.system.domain.LoginLogDO;

import java.util.List;
import java.util.Map;

/**
 * 登录日志表
 * 
 */
public interface LoginLogService {
	
	LoginLogDO get(Long id);
	
	List<LoginLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LoginLogDO log);
	
	int update(LoginLogDO log);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
