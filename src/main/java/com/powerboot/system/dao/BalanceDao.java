package com.powerboot.system.dao;

import com.powerboot.system.domain.BalanceDO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.powerboot.system.vo.PayVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 余额流水表
 */
@Mapper
public interface BalanceDao {

	BalanceDO get(Long id);

	List<BalanceDO> list(Map<String,Object> map);

	List<BalanceDO> listTest(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	BigDecimal sumAmount(@Param("saleIdList") List<Long> saleIdList);

	int save(BalanceDO balance);
	
	int update(BalanceDO balance);

	int updateBatch(List<BalanceDO> balance);

	int updateSaleIdByUserId(@Param("saleId") Long saleId,
							 @Param("userIdList") List<Long> userIdList);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	PayVO getCountByTypeStatusAndDate(@Param("typeList") List<Integer> typeList, @Param("status") Integer status,
		@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
		@Param("saleIdList") List<Long> saleIdList);
}
