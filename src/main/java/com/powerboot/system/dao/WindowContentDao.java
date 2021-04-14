package com.powerboot.system.dao;

import com.powerboot.system.domain.WindowContentDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 弹窗信息配置表
 */
@Mapper
public interface WindowContentDao {

	List<WindowContentDO> getAll();

	WindowContentDO get(Integer id);
	
	List<WindowContentDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(WindowContentDO windowContent);
	
	int update(WindowContentDO windowContent);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
