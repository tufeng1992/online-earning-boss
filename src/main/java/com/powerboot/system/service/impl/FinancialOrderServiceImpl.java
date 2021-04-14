package com.powerboot.system.service.impl;

import com.powerboot.system.dto.FutureOrderDto;
import com.powerboot.system.response.FinOrderResp;
import com.powerboot.utils.DateUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.FinancialOrderDao;
import com.powerboot.system.domain.FinancialOrderDO;
import com.powerboot.system.service.FinancialOrderService;

@Service
public class FinancialOrderServiceImpl implements FinancialOrderService {
	@Autowired
	private FinancialOrderDao financialOrderDao;

	public FinOrderResp getFinOrderResp(List<Long> saleIdList){
		FinOrderResp finOrderResp = new FinOrderResp();
		Date now = new Date();
		Date todayStart = DateUtils.setDateHMS(now,0,0,0);
		Date todayEnd = DateUtils.setDateHMS(now,23,59,59);
		Date yesterdayStart = DateUtils.addDays(todayStart,-1);
		Date yesterdayEnd = DateUtils.addDays(todayEnd,-1);
		Date beforeYesterdayEnd = DateUtils.addDays(todayEnd,-2);

		Map<String, Object> map = new HashMap<>();
		if(CollectionUtils.isNotEmpty(saleIdList)){
			map.put("saleIdList",saleIdList);
		}
		BigDecimal inCount = financialOrderDao.getInAmountSum(map);
		finOrderResp.setInCount(inCount!=null?inCount:BigDecimal.ZERO);
		map.put("buyDateStart",todayStart);
		map.put("buyDateEnd",todayEnd);
		BigDecimal todayInCount = financialOrderDao.getInAmountSum(map);
		finOrderResp.setTodayInCount(todayInCount!=null?todayInCount:BigDecimal.ZERO);
		map.put("buyDateStart",yesterdayStart);
		map.put("buyDateEnd",yesterdayEnd);
		BigDecimal yesterdayInCount = financialOrderDao.getInAmountSum(map);
		finOrderResp.setYesterdayInCount(yesterdayInCount!=null?yesterdayInCount:BigDecimal.ZERO);



		Map<String, Object> map2 = new HashMap<>();
		if(CollectionUtils.isNotEmpty(saleIdList)){
			map2.put("saleIdList",saleIdList);
		}
		BigDecimal outCount = financialOrderDao.getOutAmountSum(map2);
		finOrderResp.setOutCount(outCount!=null?outCount:BigDecimal.ZERO);
		map2.put("lastDate",yesterdayEnd);
		map2.put("calledTimeStart",todayStart);
		map2.put("calledTimeEnd",todayEnd);
		BigDecimal todayOutCount = financialOrderDao.getOutAmountSum(map2);
		finOrderResp.setTodayOutCount(todayOutCount!=null?todayOutCount:BigDecimal.ZERO);
		map2.put("lastDate",beforeYesterdayEnd);
		map2.put("calledTimeStart",yesterdayStart);
		map2.put("calledTimeEnd",yesterdayEnd);
		BigDecimal yesterdayOutCount = financialOrderDao.getOutAmountSum(map2);
		finOrderResp.setYesterdayOutCount(yesterdayOutCount!=null?yesterdayOutCount:BigDecimal.ZERO);

		return finOrderResp;
	}
	
	@Override
	public FinancialOrderDO get(Integer id){
		return financialOrderDao.get(id);
	}
	
	@Override
	public List<FinancialOrderDO> list(Map<String, Object> map){
		return financialOrderDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return financialOrderDao.count(map);
	}
	
	@Override
	public int save(FinancialOrderDO financialOrder){
		return financialOrderDao.save(financialOrder);
	}
	
	@Override
	public int update(FinancialOrderDO financialOrder){
		return financialOrderDao.update(financialOrder);
	}
	
	@Override
	public int remove(Integer id){
		return financialOrderDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return financialOrderDao.batchRemove(ids);
	}

	@Override
	public List<FutureOrderDto> futureOrder() {
		List<FutureOrderDto> list = new ArrayList<>();
		Date now = new Date();
		for(int i=0;i<30;i++){
			Date date=DateUtils.addDays(now,i+1);
			FutureOrderDto dto = new FutureOrderDto();
			dto.setDate(DateUtils.formatDateYMD(date));
			Date yesDate=DateUtils.setDateHMS(DateUtils.addDays(now,i),23,59,59);
			String lastDate = DateUtils.formatDateYMDHMS(yesDate);
			dto.setLastDate(lastDate);
			Map<String, Object> map = new HashMap<>();
			map.put("lastDate",lastDate);
			map.put("orderStatus",1);
            map.put("minUserId",104);
			int count = financialOrderDao.count(map);
			dto.setCount(String.valueOf(count));
			List<FinancialOrderDO> orderDOList = financialOrderDao.list(map);
			if(CollectionUtils.isEmpty(orderDOList)){
				dto.setAmount("0");
				dto.setInterest("0");
			}else{
				BigDecimal amount =
						orderDOList.stream().map(FinancialOrderDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
				BigDecimal totalInterest =
						orderDOList.stream().map(FinancialOrderDO::getTotalInterest).reduce(BigDecimal.ZERO, BigDecimal::add);
				dto.setAmount(amount.toString());
				dto.setInterest(totalInterest.toString());
				if(totalInterest.compareTo(amount)>=0){
					dto.setShowRed(1);
				}
			}
			list.add(dto);
		}
		return list;
	}

	public BigDecimal getAmountByStatus(Integer status, List<Long> saleIdList){
		BigDecimal inCount = financialOrderDao.getAmountByStatus(status, saleIdList);
		return inCount != null ? inCount : BigDecimal.ZERO;
	}

	public int updateSaleIdByUserId(Long saleId,List<Long> userIdList){
		return financialOrderDao.updateSaleIdByUserId(saleId, userIdList);
	}
	
}
