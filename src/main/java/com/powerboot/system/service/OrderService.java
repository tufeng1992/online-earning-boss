package com.powerboot.system.service;


import com.powerboot.common.utils.EhCacheUtil;
import com.powerboot.system.dao.OrderDao;
import com.powerboot.system.domain.DataBossVo;
import com.powerboot.system.domain.OrderDO;
import com.powerboot.system.response.OrderResp;
import com.powerboot.system.response.TaskResponse;
import com.powerboot.utils.DateUtils;
import java.util.Optional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    SummaryTableService summaryTableService;

    //获取刷单日活
    public TaskResponse getTaskResponse(Integer userCount,Integer yesterdayUserCount){
        TaskResponse taskResponse = new TaskResponse();
        Date now = new Date();
        Date yes = DateUtils.addDays(now,-1);
        Date todayStart = DateUtils.setDateHMS(now,0,0,0);
        Date todayEnd = DateUtils.setDateHMS(now,23,59,59);
        Date yesStart = DateUtils.setDateHMS(yes,0,0,0);
        Date yesEnd = DateUtils.setDateHMS(yes,23,59,59);
        taskResponse.setCount(orderDao.getCountGroupByUser(null,null));
        taskResponse.setCountRate(new BigDecimal(taskResponse.getCount()).divide(new BigDecimal(userCount),2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).toString());
        taskResponse.setLocalCount(orderDao.getCountGroupByUser(todayStart,todayEnd));
        taskResponse.setLocalCountRate(new BigDecimal(taskResponse.getLocalCount()).divide(new BigDecimal(userCount),2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).toString());
        taskResponse.setYesterdayCount(orderDao.getCountGroupByUser(yesStart,yesEnd));
        if (taskResponse.getYesterdayCount() == 0 || yesterdayUserCount == 0) {
            taskResponse.setYesterdayCountRate("0");
        } else {
            taskResponse.setYesterdayCountRate(new BigDecimal(taskResponse.getYesterdayCount()).divide(new BigDecimal(yesterdayUserCount), 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).toString());
        }
        return taskResponse;
    }

    //获取首页刷单数据
    public OrderResp getOrderResp() {
        OrderResp orderResp = new OrderResp();
        BigDecimal amount = Optional.ofNullable(orderDao.sumAmount()).orElse(BigDecimal.ZERO);
        orderResp.setAmount(amount.setScale(2, BigDecimal.ROUND_DOWN));
        BigDecimal todayOrderAmount = EhCacheUtil.getValue("todayOrderAmount", BigDecimal.class);
        if (todayOrderAmount == null) {
            List<OrderDO> today = getTodayTeamList();
            BigDecimal todayAmount = today.stream().map(OrderDO::getProductCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
            orderResp.setTodayAmount(todayAmount);
            EhCacheUtil.setValue("todayOrderAmount", todayAmount, 3600);
        } else {
            orderResp.setTodayAmount(todayOrderAmount);
        }
        List<DataBossVo> dataBossVos = summaryTableService.listAndLimit(2);
        orderResp.setYesterdayAmount(BigDecimal.ZERO);
        if (CollectionUtils.isNotEmpty(dataBossVos)){
            if (dataBossVos.size() > 0){
                orderResp.setYesterdayAmount(dataBossVos.get(0).getCommissionsAmount());
            }
        }
        return orderResp;
    }

    //获取今日已刷单列表
    public List<OrderDO> getTodayList(Long userId) {
        Date today = DateUtils.parseDate(new Date(), DateUtils.SIMPLE_DATEFORMAT_YMD + " 00:00:00");
        HashMap<String, Object> todayMap = new HashMap<>();
        todayMap.put("userId", userId);
        todayMap.put("createTime", today);
        return list(todayMap);
    }

    //获取大于等于昨日已刷单列表
    public List<OrderDO> getYesterdayList(Long userId) {
        Date day = DateUtils.parseDate(DateUtils.addDays(new Date(), -1), DateUtils.SIMPLE_DATEFORMAT_YMD + " 00:00:00");
        HashMap<String, Object> todayMap = new HashMap<>();
        todayMap.put("userId", userId);
        todayMap.put("createTime", day);
        return list(todayMap);
    }

    //获取今日团队已刷单列表
    public List<OrderDO> getTodayTeamList() {
        Date day = DateUtils.parseDate(new Date(), DateUtils.SIMPLE_DATEFORMAT_YMD + " 00:00:00");
        HashMap<String, Object> todayMap = new HashMap<>();
        todayMap.put("createTime", day);
        return teamList(todayMap);
    }

    //获取大于等于昨日团队已刷单列表
    public List<OrderDO> getYesterdayTeamList() {
        Date day = DateUtils.parseDate(DateUtils.addDays(new Date(), -1), DateUtils.SIMPLE_DATEFORMAT_YMD + " 00:00:00");
        HashMap<String, Object> todayMap = new HashMap<>();
        todayMap.put("createTime", day);
        return teamList(todayMap);
    }


    public OrderDO get(Long id) {
        return orderDao.get(id);
    }

    public List<OrderDO> list(Map<String, Object> map) {
        return orderDao.list(map);
    }

    public List<OrderDO> listByData(@Param("list") List<Long> list) {
        return orderDao.listByData(list);
    }

    public List<OrderDO> teamList(Map<String, Object> map) {
        return orderDao.teamList(map);
    }

    public int count(Map<String, Object> map) {
        return orderDao.count(map);
    }

    public int save(OrderDO order) {
        return orderDao.save(order);
    }

    public int update(OrderDO order) {
        return orderDao.update(order);
    }

    public int remove(Long id) {
        return orderDao.remove(id);
    }

    public int batchRemove(Long[] ids) {
        return orderDao.batchRemove(ids);
    }


}
