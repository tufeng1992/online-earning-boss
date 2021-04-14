package com.powerboot.system.service;

import com.powerboot.system.domain.FinancialProductDO;

import java.util.List;
import java.util.Map;

/**
 * 理财产品表
 * 
 */
public interface FinancialProductService {
	
	FinancialProductDO get(Integer id);
	
	List<FinancialProductDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FinancialProductDO financialProduct);
	
	int update(FinancialProductDO financialProduct);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
