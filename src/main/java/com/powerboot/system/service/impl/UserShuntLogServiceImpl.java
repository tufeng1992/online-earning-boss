package com.powerboot.system.service.impl;

import com.google.common.collect.Maps;
import com.powerboot.system.domain.AppUserDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.UserShuntLogDao;
import com.powerboot.system.domain.UserShuntLogDO;
import com.powerboot.system.service.UserShuntLogService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserShuntLogServiceImpl implements UserShuntLogService {
	@Autowired
	private UserShuntLogDao userShuntLogDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addUserShuntLogs(AppUserDO... appUserDOS) {
		if (null == appUserDOS || appUserDOS.length == 0) {
			return 0;
		}
		int res = 0;
		for (AppUserDO appUserDO : appUserDOS) {
			UserShuntLogDO userShuntLogDO = new UserShuntLogDO();
			userShuntLogDO.setSaleId(appUserDO.getSaleId());
			userShuntLogDO.setUserId(appUserDO.getId());
			userShuntLogDO.setShuntType(2);
			res += userShuntLogDao.save(userShuntLogDO);
		}
		return res;
	}

	@Override
	public UserShuntLogDO selectByAppUserId(Long appUserId) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", appUserId);
		List<UserShuntLogDO> userShuntLogDOList = userShuntLogDao.list(params);
		return CollectionUtils.isEmpty(userShuntLogDOList) ? null : userShuntLogDOList.get(0);
	}

	@Override
	public List<UserShuntLogDO> selectBySaleId(Long saleId) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("saleId", saleId);
		return userShuntLogDao.list(params);
	}

	@Override
	public int countBySaleId(Map<String, Object> params) {
		LocalDate nowDate = LocalDate.now();
		Object startTime = params.get("startTime");
		Object endTime = params.get("endTime");
		if (null != startTime && StringUtils.isNotBlank(startTime.toString())) {
			params.put("startTime", startTime);
		} else {
			params.put("startTime", nowDate);
		}
		if (null != params.get("endTime") && StringUtils.isNotBlank(endTime.toString())) {
			params.put("endTime", endTime);
		} else {
			params.put("endTime", nowDate.plusDays(1));
		}
		return userShuntLogDao.count(params);
	}

	@Override
	public UserShuntLogDO get(Long id){
		return userShuntLogDao.get(id);
	}
	
	@Override
	public List<UserShuntLogDO> list(Map<String, Object> map){
		return userShuntLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userShuntLogDao.count(map);
	}
	
	@Override
	public int save(UserShuntLogDO userShuntLog){
		return userShuntLogDao.save(userShuntLog);
	}
	
	@Override
	public int update(UserShuntLogDO userShuntLog){
		return userShuntLogDao.update(userShuntLog);
	}
	
	@Override
	public int remove(Long id){
		return userShuntLogDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userShuntLogDao.batchRemove(ids);
	}
	
}
