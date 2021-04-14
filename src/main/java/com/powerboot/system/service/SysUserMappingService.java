package com.powerboot.system.service;

import com.powerboot.system.dao.SysUserMappingDao;
import com.powerboot.system.dto.SysUserMappingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserMappingService {
    @Autowired
    private SysUserMappingDao SysUserMappingDao;

    public List<SysUserMappingDTO> getBySysUserId(Long sysUserId){
        return SysUserMappingDao.getBySysUserId(sysUserId);
    }

    public int save(SysUserMappingDTO sysUserMappingDTO){
        return SysUserMappingDao.save(sysUserMappingDTO);
    }

    public int deleteBySysUserId(Long sysUserId){
        return SysUserMappingDao.deleteBySysUserId(sysUserId);
    }
}
