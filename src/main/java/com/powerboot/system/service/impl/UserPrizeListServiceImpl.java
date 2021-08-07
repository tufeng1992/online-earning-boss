package com.powerboot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.UserPrizeListDao;
import com.powerboot.system.domain.UserPrizeListDO;
import com.powerboot.system.service.UserPrizeListService;



@Service
public class UserPrizeListServiceImpl implements UserPrizeListService {
	@Autowired
	private UserPrizeListDao userPrizeListDao;
	
	@Override
	public UserPrizeListDO get(Long id){
		return userPrizeListDao.get(id);
	}
	
	@Override
	public List<UserPrizeListDO> list(Map<String, Object> map){
		return userPrizeListDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userPrizeListDao.count(map);
	}
	
	@Override
	public int save(UserPrizeListDO userPrizeList){
		return userPrizeListDao.save(userPrizeList);
	}
	
	@Override
	public int update(UserPrizeListDO userPrizeList){
		return userPrizeListDao.update(userPrizeList);
	}
	
	@Override
	public int remove(Long id){
		return userPrizeListDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userPrizeListDao.batchRemove(ids);
	}
	
}
