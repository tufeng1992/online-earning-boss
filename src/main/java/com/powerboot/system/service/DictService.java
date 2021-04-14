package com.powerboot.system.service;

import com.powerboot.system.domain.DictDO;

import java.util.List;
import java.util.Map;

/**
 * 数据配置表
 */
public interface DictService {
	
	DictDO get(Long id);

	DictDO getByKey(String key);

	List<DictDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DictDO dict);
	
	int update(DictDO dict);

	int updateByKey(String key,String value);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
