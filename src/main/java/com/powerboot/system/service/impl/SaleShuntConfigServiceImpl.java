package com.powerboot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.SaleShuntConfigDao;
import com.powerboot.system.domain.SaleShuntConfigDO;
import com.powerboot.system.service.SaleShuntConfigService;



@Service
public class SaleShuntConfigServiceImpl implements SaleShuntConfigService {
	@Autowired
	private SaleShuntConfigDao saleShuntConfigDao;
	
	@Override
	public SaleShuntConfigDO get(Long id){
		return saleShuntConfigDao.get(id);
	}
	
	@Override
	public List<SaleShuntConfigDO> list(Map<String, Object> map){
		return saleShuntConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return saleShuntConfigDao.count(map);
	}
	
	@Override
	public int save(SaleShuntConfigDO saleShuntConfig){
		return saleShuntConfigDao.save(saleShuntConfig);
	}
	
	@Override
	public int update(SaleShuntConfigDO saleShuntConfig){
		return saleShuntConfigDao.update(saleShuntConfig);
	}
	
	@Override
	public int remove(Long id){
		return saleShuntConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return saleShuntConfigDao.batchRemove(ids);
	}
	
}
