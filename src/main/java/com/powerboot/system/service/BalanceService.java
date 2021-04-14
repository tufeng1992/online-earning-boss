package com.powerboot.system.service;


import com.powerboot.system.dao.AppUserDao;
import com.powerboot.system.dao.BalanceDao;
import com.powerboot.system.domain.BalanceDO;
import com.powerboot.system.response.PayResp;
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
	
}
