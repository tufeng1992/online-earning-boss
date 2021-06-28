package com.powerboot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.MemberInfoDao;
import com.powerboot.system.domain.MemberInfoDO;
import com.powerboot.system.service.MemberInfoService;



@Service
public class MemberInfoServiceImpl implements MemberInfoService {
	@Autowired
	private MemberInfoDao memberInfoDao;
	
	@Override
	public MemberInfoDO get(Long id){
		return memberInfoDao.get(id);
	}
	
	@Override
	public List<MemberInfoDO> list(Map<String, Object> map){
		return memberInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return memberInfoDao.count(map);
	}
	
	@Override
	public int save(MemberInfoDO memberInfo){
		return memberInfoDao.save(memberInfo);
	}
	
	@Override
	public int update(MemberInfoDO memberInfo){
		return memberInfoDao.update(memberInfo);
	}
	
	@Override
	public int remove(Long id){
		return memberInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return memberInfoDao.batchRemove(ids);
	}
	
}
