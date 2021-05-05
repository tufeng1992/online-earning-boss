package com.powerboot.system.service;

import com.powerboot.system.domain.GuestbookDO;

import java.util.List;
import java.util.Map;

/**
 * 留言板
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-05-03 15:56:09
 */
public interface GuestbookService {
	
	GuestbookDO get(Long id);
	
	List<GuestbookDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GuestbookDO guestbook);
	
	int update(GuestbookDO guestbook);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
