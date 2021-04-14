package com.powerboot.system.dao;

import com.powerboot.system.domain.SmsDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 短信验证码表
 */
@Mapper
public interface SmsDao {

	SmsDO get(Long id);
	
	List<SmsDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SmsDO sms);
	
	int update(SmsDO sms);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
