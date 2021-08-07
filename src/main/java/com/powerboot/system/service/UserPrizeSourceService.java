package com.powerboot.system.service;

import com.powerboot.system.domain.UserPrizeSourceDO;

import java.util.List;
import java.util.Map;

/**
 * 用户奖励关联信息表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
public interface UserPrizeSourceService {
	
	UserPrizeSourceDO get(Long id);
	
	List<UserPrizeSourceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserPrizeSourceDO userPrizeSource);
	
	int update(UserPrizeSourceDO userPrizeSource);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
