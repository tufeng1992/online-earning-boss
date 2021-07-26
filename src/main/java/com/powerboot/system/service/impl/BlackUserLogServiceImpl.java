package com.powerboot.system.service.impl;

import com.powerboot.common.utils.ShiroUtils;
import com.powerboot.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.BlackUserLogDao;
import com.powerboot.system.domain.BlackUserLogDO;
import com.powerboot.system.service.BlackUserLogService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BlackUserLogServiceImpl implements BlackUserLogService {
	@Autowired
	private BlackUserLogDao blackUserLogDao;
	
	@Override
	public BlackUserLogDO get(Long id){
		return blackUserLogDao.get(id);
	}
	
	@Override
	public List<BlackUserLogDO> list(Map<String, Object> map){
		return blackUserLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return blackUserLogDao.count(map);
	}
	
	@Override
	public int save(BlackUserLogDO blackUserLog){
		return blackUserLogDao.save(blackUserLog);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int save(Long blackUserId, String blackReason, Long saleId) {
		BlackUserLogDO blackUserLog = new BlackUserLogDO();
		blackUserLog.setBlackUserId(blackUserId);
		blackUserLog.setBlackReason(blackReason);
		blackUserLog.setSaleId(saleId);
		UserDO userDO = ShiroUtils.getUser();
		if (null != userDO) {
			blackUserLog.setOperUser(userDO.getUsername());
		} else {
			blackUserLog.setOperUser("SYSTEM");
		}
		return save(blackUserLog);
	}
	
	@Override
	public int update(BlackUserLogDO blackUserLog){
		return blackUserLogDao.update(blackUserLog);
	}
	
	@Override
	public int remove(Long id){
		return blackUserLogDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return blackUserLogDao.batchRemove(ids);
	}
	
}
