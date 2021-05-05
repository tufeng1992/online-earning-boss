package com.powerboot.system.dao;

import com.powerboot.system.domain.PayDO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.powerboot.system.domain.PayImportDo;
import com.powerboot.system.response.PayResp;
import com.powerboot.system.vo.PayVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 支付结果
 */
@Mapper
public interface PayDao {

	Integer getRechangeCount();

	Integer getAgainRechangeCount();

	PayDO get(Long id);
	
	List<PayDO> list(Map<String,Object> map);

	List<PayDO> listTest(Map<String,Object> map);

	PayDO getByOrderNo(String orderNo);

	int count(Map<String,Object> map);
	
	int save(PayDO pay);

	int update(PayDO pay);

	int updateBatch(List<PayDO> pay);

	int updateSaleIdByUserId(@Param("saleId") Long saleId,
							 @Param("userIdList") List<Long> userIdList);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	PayVO getCountByTypeStatusAndDate(@Param("typeList") List<Integer> typeList, @Param("status") Integer status,
		@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate,
		@Param("saleIdList") List<Long> saleIdList);

	List<PayDO> getByUserIdList(@Param("list") List<Long> idList);

	List<PayImportDo> selectImportPay(@Param("payId") Long payId,@Param("createTimeStart") Date createTimeStart,
									  @Param("createTimeEnd") Date createTimeEnd,@Param("limit") Integer limit);

	PayDO getNewOrderByUserId(@Param("userId")Long userId);
}
