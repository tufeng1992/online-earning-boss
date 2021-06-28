package com.powerboot.system.dao;

import com.powerboot.system.domain.MemberInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 会员卡配置信息表
 * @author system
 * @email system@cc.cc
 * @date 2021-06-24 22:07:10
 */
@Mapper
public interface MemberInfoDao {

	MemberInfoDO get(Long id);
	
	List<MemberInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MemberInfoDO memberInfo);
	
	int update(MemberInfoDO memberInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
