package com.powerboot.system.dao;

import com.powerboot.system.domain.DataBossVo;
import com.powerboot.system.domain.DataSaleVo;
import com.powerboot.system.vo.DataCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SummaryTableDao {

    int updateById(DataBossVo dataBossVo);

    DataBossVo getById(@Param("id") Integer id);

    public List<DataBossVo> list(Map<String, Object> params);

    public List<DataBossVo> listAndLimit(@Param("limit") Integer limit);

    public int listSonCount(@Param("param") DataCountVO param);

    public List<DataSaleVo> listSon(@Param("param") DataCountVO param);

    public List<DataSaleVo> listSonDetail(@Param("parentId") Long parentId);
}
