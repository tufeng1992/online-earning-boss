package com.powerboot.system.dao;

import com.powerboot.system.domain.BlackUserLogDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户拉黑记录表
 * @author system
 * @email system@cc.cc
 * @date 2021-07-16 21:28:49
 */
@Mapper
public interface BlackUserLogDao {

	BlackUserLogDO get(Long id);
	
	List<BlackUserLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BlackUserLogDO blackUserLog);
	
	int update(BlackUserLogDO blackUserLog);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
