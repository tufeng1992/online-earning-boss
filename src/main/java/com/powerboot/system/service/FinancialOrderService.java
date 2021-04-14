package com.powerboot.system.service;

import com.powerboot.system.domain.FinancialOrderDO;
import com.powerboot.system.dto.FutureOrderDto;

import java.util.List;
import java.util.Map;

/**
 * 理财订单表
 * 
 */
public interface FinancialOrderService {
	
	FinancialOrderDO get(Integer id);
	
	List<FinancialOrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FinancialOrderDO financialOrder);
	
	int update(FinancialOrderDO financialOrder);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	List<FutureOrderDto> futureOrder();

	int updateSaleIdByUserId(Long saleId,List<Long> userIdList);
}
