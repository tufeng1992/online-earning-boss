package com.powerboot.system.service;

import com.powerboot.system.dao.SysUserMappingDao;
import com.powerboot.system.dto.SysUserMappingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 查询用户关联的saleId
     * @param sysUserId
     * @return
     */
    public List<Long> getUserMappingSaleIds(Long sysUserId) {
        List<SysUserMappingDTO> userMappingDTOS = getBySysUserId(sysUserId);
        List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
        return saleIds;
    }
}
