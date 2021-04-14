package com.powerboot.system.service.impl;

import com.powerboot.system.dao.SmsDao;
import com.powerboot.system.domain.SmsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.service.SmsService;



@Service
public class SmsServiceImpl implements SmsService {
	@Autowired
	private SmsDao smsDao;
	
	@Override
	public SmsDO get(Long id){
		return smsDao.get(id);
	}
	
	@Override
	public List<SmsDO> list(Map<String, Object> map){
		return smsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return smsDao.count(map);
	}
	
	@Override
	public int save(SmsDO sms){
		return smsDao.save(sms);
	}
	
	@Override
	public int update(SmsDO sms){
		return smsDao.update(sms);
	}
	
	@Override
	public int remove(Long id){
		return smsDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return smsDao.batchRemove(ids);
	}
	
}
