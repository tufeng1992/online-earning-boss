package com.powerboot.system.dao;

import com.powerboot.system.domain.FinancialProductDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 理财产品表
 */
@Mapper
public interface FinancialProductDao {

	FinancialProductDO get(Integer id);
	
	List<FinancialProductDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FinancialProductDO financialProduct);
	
	int update(FinancialProductDO financialProduct);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
