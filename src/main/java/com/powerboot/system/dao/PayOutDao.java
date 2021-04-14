package com.powerboot.system.dao;

import com.powerboot.system.domain.PayOutDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 系统提出金额记录表
 */
@Mapper
public interface PayOutDao {

    PayOutDO get(Long id);

    List<PayOutDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PayOutDO payOut);

    int update(PayOutDO payOut);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
