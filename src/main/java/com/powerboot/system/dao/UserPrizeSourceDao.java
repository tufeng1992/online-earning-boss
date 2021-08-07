package com.powerboot.system.dao;

import com.powerboot.system.domain.UserPrizeSourceDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户奖励关联信息表
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
@Mapper
public interface UserPrizeSourceDao {

	UserPrizeSourceDO get(Long id);
	
	List<UserPrizeSourceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserPrizeSourceDO userPrizeSource);
	
	int update(UserPrizeSourceDO userPrizeSource);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
