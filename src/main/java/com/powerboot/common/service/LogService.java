package com.powerboot.common.service;

import org.springframework.stereotype.Service;

import com.powerboot.common.domain.LogDO;
import com.powerboot.common.domain.PageDO;
import com.powerboot.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
