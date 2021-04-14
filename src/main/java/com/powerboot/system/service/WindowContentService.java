package com.powerboot.system.service;

import com.powerboot.system.domain.WindowContentDO;

import java.util.List;
import java.util.Map;

/**
 * 弹窗信息配置表
 * 
 */
public interface WindowContentService {
	
	WindowContentDO get(Integer id);
	
	List<WindowContentDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(WindowContentDO windowContent);
	
	int update(WindowContentDO windowContent);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
