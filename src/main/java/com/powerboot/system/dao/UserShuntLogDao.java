package com.powerboot.system.dao;

import com.powerboot.system.domain.UserShuntLogDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户分流信息记录
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
@Mapper
public interface UserShuntLogDao {

	UserShuntLogDO get(Long id);
	
	List<UserShuntLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserShuntLogDO userShuntLog);
	
	int update(UserShuntLogDO userShuntLog);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
