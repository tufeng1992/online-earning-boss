package com.powerboot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.UserTaskOrderMissionDao;
import com.powerboot.system.domain.UserTaskOrderMissionDO;
import com.powerboot.system.service.UserTaskOrderMissionService;



@Service
public class UserTaskOrderMissionServiceImpl implements UserTaskOrderMissionService {
	@Autowired
	private UserTaskOrderMissionDao userTaskOrderMissionDao;
	
	@Override
	public UserTaskOrderMissionDO get(Long id){
		return userTaskOrderMissionDao.get(id);
	}
	
	@Override
	public List<UserTaskOrderMissionDO> list(Map<String, Object> map){
		return userTaskOrderMissionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userTaskOrderMissionDao.count(map);
	}
	
	@Override
	public int save(UserTaskOrderMissionDO userTaskOrderMission){
		return userTaskOrderMissionDao.save(userTaskOrderMission);
	}
	
	@Override
	public int update(UserTaskOrderMissionDO userTaskOrderMission){
		return userTaskOrderMissionDao.update(userTaskOrderMission);
	}
	
	@Override
	public int remove(Long id){
		return userTaskOrderMissionDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userTaskOrderMissionDao.batchRemove(ids);
	}
	
}
