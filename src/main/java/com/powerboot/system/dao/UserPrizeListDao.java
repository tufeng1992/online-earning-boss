package com.powerboot.system.dao;

import com.powerboot.system.domain.UserPrizeListDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户奖励信息表
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
@Mapper
public interface UserPrizeListDao {

	UserPrizeListDO get(Long id);
	
	List<UserPrizeListDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserPrizeListDO userPrizeList);
	
	int update(UserPrizeListDO userPrizeList);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
