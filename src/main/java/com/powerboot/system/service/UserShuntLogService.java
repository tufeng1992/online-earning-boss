package com.powerboot.system.service;

import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.UserShuntLogDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户分流信息记录
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
public interface UserShuntLogService {

	/**
	 * 添加用户分流记录
	 * @param appUserDOS
	 * @return
	 */
	int addUserShuntLogs(AppUserDO... appUserDOS);

	/**
	 * 根据用户id查询
	 * @param appUserId
	 * @return
	 */
	UserShuntLogDO selectByAppUserId(Long appUserId);

	/**
	 * 根据销售人员id查询
	 * @param saleId
	 * @return
	 */
	List<UserShuntLogDO> selectBySaleId(Long saleId);

	/**
	 * 根据销售人员id查询数量
	 * @return
	 */
	int countBySaleId(Map<String, Object> params);
	
	UserShuntLogDO get(Long id);
	
	List<UserShuntLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserShuntLogDO userShuntLog);
	
	int update(UserShuntLogDO userShuntLog);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
