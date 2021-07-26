package com.powerboot.system.service;

import com.powerboot.client.ApplyRequest;
import com.powerboot.system.domain.PayDO;
import com.powerboot.system.response.PayResp;

import com.powerboot.system.response.RechangeResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 支付结果
 * 
 */
public interface PayService {
	
	PayDO get(Long id);
	
	List<PayDO> list(Map<String, Object> map);

	/**
	 * 查询用户提现总金额
	 * @param userId
	 * @param o
	 * @return
	 */
	BigDecimal selectUserWithdrawalTotalAmont(Long userId, PayDO o);

	/**
	 * 查询用户充值总金额
	 * @param userId
	 * @return
	 */
	BigDecimal selectUserRechargeTotal(Long userId);
	
	int count(Map<String, Object> map);
	
	int save(PayDO pay);
	
	int update(PayDO pay);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	/**
	 * 根据类型,状态查找数量
	 * @param status
	 * @return
	 */
	PayResp getCountByTypeStatusAndDate(List<Integer> typeList, Integer status,List<Long> saleIdList);

	List<PayDO> getByUserIdList(List<Long> idList);

	int updateSaleIdByUserId(Long saleId,List<Long> userIdList);

	Boolean successPay(String orderNo);

	boolean refreshPay(String orderNo);

	RechangeResponse getRechangeResponse(Integer userCount, List<Long> saleIdList);

	boolean apply(ApplyRequest applyRequest, Boolean apply);

	public PayDO getNewOrderByUserId(Long userId);
}
