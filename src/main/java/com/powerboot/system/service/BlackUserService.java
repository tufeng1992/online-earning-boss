package com.powerboot.system.service;

import com.powerboot.system.domain.BlackUserDO;

import java.util.List;
import java.util.Map;

/**
 * 黑名单用户表
 */
public interface BlackUserService {
	
	BlackUserDO get(Long id);
	
	List<BlackUserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BlackUserDO blackUser);
	
	int update(BlackUserDO blackUser);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
