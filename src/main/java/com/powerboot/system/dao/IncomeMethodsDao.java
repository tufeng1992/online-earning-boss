package com.powerboot.system.dao;

import com.powerboot.system.domain.IncomeMethodsDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 功能简介表
 */
@Mapper
public interface IncomeMethodsDao {

	List<IncomeMethodsDO> getAll();

	IncomeMethodsDO get(Integer id);
	
	List<IncomeMethodsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(IncomeMethodsDO incomeMethods);
	
	int update(IncomeMethodsDO incomeMethods);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
