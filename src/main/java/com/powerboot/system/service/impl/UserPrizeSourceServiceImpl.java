package com.powerboot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.UserPrizeSourceDao;
import com.powerboot.system.domain.UserPrizeSourceDO;
import com.powerboot.system.service.UserPrizeSourceService;



@Service
public class UserPrizeSourceServiceImpl implements UserPrizeSourceService {
	@Autowired
	private UserPrizeSourceDao userPrizeSourceDao;
	
	@Override
	public UserPrizeSourceDO get(Long id){
		return userPrizeSourceDao.get(id);
	}
	
	@Override
	public List<UserPrizeSourceDO> list(Map<String, Object> map){
		return userPrizeSourceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userPrizeSourceDao.count(map);
	}
	
	@Override
	public int save(UserPrizeSourceDO userPrizeSource){
		return userPrizeSourceDao.save(userPrizeSource);
	}
	
	@Override
	public int update(UserPrizeSourceDO userPrizeSource){
		return userPrizeSourceDao.update(userPrizeSource);
	}
	
	@Override
	public int remove(Long id){
		return userPrizeSourceDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userPrizeSourceDao.batchRemove(ids);
	}
	
}
