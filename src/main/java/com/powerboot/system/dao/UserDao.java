package com.powerboot.system.dao;

import com.powerboot.system.domain.UserDO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.powerboot.system.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

	UserDO get(Long userId);

	List<UserDO> getByLeader(Long userId);
	
	List<UserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);

	int updateByParam(@Param("entity")UserDO entity,@Param("param")UserDO param);

	int updatePwdByUserName(UserDO user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();

	BigDecimal getUserRechargeAmount(Long id);

}
