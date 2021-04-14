package com.powerboot.system.dao;

import com.powerboot.system.domain.BlackUserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 黑名单用户表
 */
@Mapper
public interface BlackUserDao {

	BlackUserDO get(Long id);
	
	List<BlackUserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BlackUserDO blackUser);
	
	int update(BlackUserDO blackUser);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
