package com.powerboot.system.service.impl;

import com.powerboot.system.dao.LoginLogDao;
import com.powerboot.system.domain.LoginLogDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.service.LoginLogService;



@Service
public class LoginLogServiceImpl implements LoginLogService {
	@Autowired
	private LoginLogDao logDao;
	
	@Override
	public LoginLogDO get(Long id){
		return logDao.get(id);
	}
	
	@Override
	public List<LoginLogDO> list(Map<String, Object> map){
		return logDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return logDao.count(map);
	}
	
	@Override
	public int save(LoginLogDO log){
		return logDao.save(log);
	}
	
	@Override
	public int update(LoginLogDO log){
		return logDao.update(log);
	}
	
	@Override
	public int remove(Long id){
		return logDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return logDao.batchRemove(ids);
	}
	
}
