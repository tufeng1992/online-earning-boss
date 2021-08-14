package com.powerboot.system.dao;

import com.powerboot.system.domain.AdMessageLogDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 运营短信发送记录
 * @author system
 * @email system@cc.cc
 * @date 2021-08-08 11:04:43
 */
@Mapper
public interface AdMessageLogDao {

	AdMessageLogDO get(Long id);
	
	List<AdMessageLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AdMessageLogDO adMessageLog);
	
	int update(AdMessageLogDO adMessageLog);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
