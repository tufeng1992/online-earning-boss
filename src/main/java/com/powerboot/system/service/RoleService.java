package com.powerboot.system.service;

import com.powerboot.system.domain.RoleDO;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {

	RoleDO get(Long id);

	List<RoleDO> list();

	int save(RoleDO role);

	int update(RoleDO role);

	int remove(Long id);

	List<RoleDO> list(Long userId);

	int batchremove(Long[] ids);
}
