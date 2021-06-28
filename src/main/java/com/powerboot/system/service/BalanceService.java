package com.powerboot.system.service;


import com.google.common.collect.Maps;
import com.powerboot.system.consts.BalanceTypeEnum;
import com.powerboot.system.consts.StatusTypeEnum;
import com.powerboot.system.dao.AppUserDao;
import com.powerboot.system.dao.BalanceDao;
import com.powerboot.system.domain.BalanceDO;
import com.powerboot.system.response.PayResp;
import com.powerboot.system.response.RegisterCountResp;
import com.powerboot.system.vo.PayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class BalanceService  {
	@Autowired
	private BalanceDao balanceDao;
	@Autowired
	private AppUserDao appUserDao;

	public BalanceDO get(Long id){
		return balanceDao.get(id);
	}

	public BigDecimal sumAmount(List<Long> saleIdList){
		return balanceDao.sumAmount(saleIdList);
	}

	public List<BalanceDO> list(Map<String, Object> map){
		return balanceDao.list(map);
	}
	
	public int count(Map<String, Object> map){
		return balanceDao.count(map);
	}
	
	public int save(BalanceDO balance){
		return balanceDao.save(balance);
	}
	
	public int update(BalanceDO balance){
		return balanceDao.update(balance);
	}

	public int updateStatusByOrderNo(String orderNo, Integer status) {
		return balanceDao.updateStatusByOrderNo(orderNo, status);
	}

	public int updateSaleIdByUserId(Long saleId,List<Long> userIdList){
		return balanceDao.updateSaleIdByUserId(saleId, userIdList);
	}
	
	public int remove(Long id){
		return balanceDao.remove(id);
	}
	
	public int batchRemove(Long[] ids){
		return balanceDao.batchRemove(ids);
	}

	//增加余额流水记录，并修改余额，如遇是减少余额的操作，金额为负数
	@Transactional(rollbackFor = Exception.class)
	public int addBalanceDetail(BalanceDO balance) {
		int count = balanceDao.save(balance);
		if (count == 1) {
			return appUserDao.updateMoney(balance.getUserId(), balance.getAmount());
		}
		return 0;
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateMoneyVip(BalanceDO balance,Integer memberLevel) {
		int count = balanceDao.save(balance);
		if (count == 1) {
			return appUserDao.updateMoneyVip(balance.getUserId(), balance.getAmount(),memberLevel);
		}
		return 0;
	}

	/**
	 * 根据类型,状态查找数量
	 * @param status
	 * @return
	 */
	public PayResp getCountByTypeStatusAndDate(List<Integer> typeList, Integer status,List<Long> saleIdList){
		PayResp resp = new PayResp();
		LocalDate nowDate = LocalDate.now();
		LocalDate yesterdayDate = LocalDate.now().plusDays(-1);

		PayVO count = balanceDao.getCountByTypeStatusAndDate(typeList, status,null,null,saleIdList);
		PayVO now = balanceDao.getCountByTypeStatusAndDate(typeList, status,nowDate,nowDate.plusDays(1),saleIdList);
		PayVO yesterday = balanceDao.getCountByTypeStatusAndDate(typeList, status,yesterdayDate,nowDate,saleIdList);

		resp.setCount(count.getCount());
		resp.setAmount(count.getAmount() == null ? BigDecimal.ZERO : count.getAmount().abs());
		resp.setLocalCount(now.getCount());
		resp.setLocalAmount(now.getAmount() == null ? BigDecimal.ZERO : now.getAmount().abs());
		resp.setYesterdayCount(yesterday.getCount());
		resp.setYesterdayAmount(yesterday.getAmount() == null ? BigDecimal.ZERO : yesterday.getAmount().abs());
		return resp;
	}

	/**
	 * 查询注册奖励
	 * @return
	 */
	public RegisterCountResp selectRegisterResp() {
		RegisterCountResp registerCountResp = new RegisterCountResp();
		LocalDate nowDate = LocalDate.now();
		LocalDate yesterdayDate = LocalDate.now().plusDays(-1);
		Map<String, Object> params = Maps.newHashMap();
		params.put("startDate", yesterdayDate);
		params.put("endDate", nowDate);
		params.put("status", StatusTypeEnum.SUCCESS.getCode());
		params.put("type", BalanceTypeEnum.N.getCode());
		List<BalanceDO> yesterdayList = balanceDao.list(params);
		BigDecimal yesterday = BigDecimal.ZERO;
		for (BalanceDO balanceDO : yesterdayList) {
			yesterday = yesterday.add(balanceDO.getAmount());
		}
		registerCountResp.setYesterdayAmount(yesterday);
		params.put("startDate", nowDate);
		params.put("endDate", nowDate.plusDays(1));
		List<BalanceDO> todayList = balanceDao.list(params);
		BigDecimal today = BigDecimal.ZERO;
		for (BalanceDO balanceDO : todayList) {
			today = today.add(balanceDO.getAmount());
		}
		registerCountResp.setTodayAmount(today);
		return registerCountResp;
	}
	
}
