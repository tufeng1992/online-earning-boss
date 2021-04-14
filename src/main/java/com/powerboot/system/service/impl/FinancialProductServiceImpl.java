package com.powerboot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.FinancialProductDao;
import com.powerboot.system.domain.FinancialProductDO;
import com.powerboot.system.service.FinancialProductService;

@Service
public class FinancialProductServiceImpl implements FinancialProductService {
	@Autowired
	private FinancialProductDao financialProductDao;
	
	@Override
	public FinancialProductDO get(Integer id){
		return financialProductDao.get(id);
	}
	
	@Override
	public List<FinancialProductDO> list(Map<String, Object> map){
		return financialProductDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return financialProductDao.count(map);
	}
	
	@Override
	public int save(FinancialProductDO financialProduct){
		return financialProductDao.save(financialProduct);
	}
	
	@Override
	public int update(FinancialProductDO financialProduct){
		return financialProductDao.update(financialProduct);
	}
	
	@Override
	public int remove(Integer id){
		return financialProductDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return financialProductDao.batchRemove(ids);
	}
	
}
