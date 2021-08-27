package com.powerboot.system.dao;

import com.powerboot.system.domain.SaleShuntConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 客服分流配置表
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
@Mapper
public interface SaleShuntConfigDao {

	SaleShuntConfigDO get(Long id);
	
	List<SaleShuntConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SaleShuntConfigDO saleShuntConfig);
	
	int update(SaleShuntConfigDO saleShuntConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
