package com.powerboot.system.dao;

import com.powerboot.system.domain.OrderDO;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 刷单任务订单表
 */
@Mapper
public interface OrderDao {

	Integer getCountGroupByUser(@Param("createTimeStart") Date createTimeStart,@Param("createTimeEnd") Date createTimeEnd);

	Integer getCountGroupByNewUser(Map<String, Object> params);
	OrderDO get(Long id);
	BigDecimal sumAmount();

	List<OrderDO> list(Map<String,Object> map);
	List<OrderDO> listByData(@Param("list") List<Long> list);
	List<OrderDO> teamList(Map<String,Object> map);

	int count(Map<String,Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
