package com.powerboot.system.service.impl;

import com.powerboot.common.utils.ShiroUtils;
import com.powerboot.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.GuestbookDao;
import com.powerboot.system.domain.GuestbookDO;
import com.powerboot.system.service.GuestbookService;



@Service
public class GuestbookServiceImpl implements GuestbookService {
	@Autowired
	private GuestbookDao guestbookDao;
	
	@Override
	public GuestbookDO get(Long id){
		return guestbookDao.get(id);
	}
	
	@Override
	public List<GuestbookDO> list(Map<String, Object> map){
		return guestbookDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return guestbookDao.count(map);
	}
	
	@Override
	public int save(GuestbookDO guestbook){
		UserDO userDO = ShiroUtils.getUser();
		guestbook.setGuestbookUserId(userDO.getUserId());
		return guestbookDao.save(guestbook);
	}
	
	@Override
	public int update(GuestbookDO guestbook){
		return guestbookDao.update(guestbook);
	}
	
	@Override
	public int remove(Long id){
		return guestbookDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return guestbookDao.batchRemove(ids);
	}
	
}
