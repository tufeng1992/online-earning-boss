package com.powerboot.system.service;

import com.powerboot.system.domain.MemberInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 会员卡配置信息表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-06-24 22:07:10
 */
public interface MemberInfoService {
	
	MemberInfoDO get(Long id);
	
	List<MemberInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MemberInfoDO memberInfo);
	
	int update(MemberInfoDO memberInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
