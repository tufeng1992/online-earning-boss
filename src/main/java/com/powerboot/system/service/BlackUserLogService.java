package com.powerboot.system.service;

import com.powerboot.system.domain.BlackUserLogDO;

import java.util.List;
import java.util.Map;

/**
 * 用户拉黑记录表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-16 21:28:49
 */
public interface BlackUserLogService {
	
	BlackUserLogDO get(Long id);
	
	List<BlackUserLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BlackUserLogDO blackUserLog);

	int save(Long blackUserId, String blackReason, Long saleId);
	
	int update(BlackUserLogDO blackUserLog);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
