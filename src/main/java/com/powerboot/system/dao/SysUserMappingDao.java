package com.powerboot.system.dao;

import com.powerboot.system.dto.SysUserMappingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMappingDao {

    List<SysUserMappingDTO> getBySysUserId(@Param("sysUserId") Long sysUserId);

    int save(SysUserMappingDTO sysUserMappingDTO);

    int deleteBySysUserId(@Param("sysUserId") Long sysUserId);


}
