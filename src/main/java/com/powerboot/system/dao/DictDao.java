package com.powerboot.system.dao;

import com.powerboot.system.domain.DictDO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据配置表
 */
@Mapper
public interface DictDao {

    DictDO get(Long id);

    DictDO getByKey(@Param("key") String key);

    List<DictDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DictDO dict);

    int update(DictDO dict);

    int updateByKey(@Param("key") String key, @Param("value") String value);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
