package com.powerboot.system.service;

import com.powerboot.system.domain.UserTaskOrderMissionDO;

import java.util.List;
import java.util.Map;

/**
 * 刷单任务完成记录表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
public interface UserTaskOrderMissionService {
	
	UserTaskOrderMissionDO get(Long id);
	
	List<UserTaskOrderMissionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserTaskOrderMissionDO userTaskOrderMission);
	
	int update(UserTaskOrderMissionDO userTaskOrderMission);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
