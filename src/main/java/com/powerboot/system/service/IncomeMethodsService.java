package com.powerboot.system.service;

import com.powerboot.system.domain.IncomeMethodsDO;

import java.util.List;
import java.util.Map;

/**
 * 功能简介表
 * 
 */
public interface IncomeMethodsService {
	
	IncomeMethodsDO get(Integer id);
	
	List<IncomeMethodsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(IncomeMethodsDO incomeMethods);
	
	int update(IncomeMethodsDO incomeMethods);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
