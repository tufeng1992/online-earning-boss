package com.powerboot.system.dao;

import com.powerboot.system.domain.FinancialOrderDO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 理财订单表
 */
@Mapper
public interface FinancialOrderDao {

	BigDecimal getInAmountSum(Map<String, Object> map);

	BigDecimal getAmountByStatus(@Param("status") Integer status, @Param("saleIdList") List<Long> saleIdList);

	BigDecimal getOutAmountSum(Map<String, Object> map);

	FinancialOrderDO get(Integer id);

	List<FinancialOrderDO> list(Map<String, Object> map);

	List<FinancialOrderDO> listTest(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FinancialOrderDO financialOrder);
	
	int update(FinancialOrderDO financialOrder);

	int updateSaleIdByUserId(@Param("saleId") Long saleId,
							 @Param("userIdList") List<Long> userIdList);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
