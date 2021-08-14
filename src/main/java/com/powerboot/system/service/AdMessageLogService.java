package com.powerboot.system.service;

import com.powerboot.system.domain.AdMessageLogDO;
import com.powerboot.system.dto.AdSendExcelMsgDTO;
import com.powerboot.system.dto.AdSendExcelTelDTO;

import java.util.List;
import java.util.Map;

/**
 * 运营短信发送记录
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-08-08 11:04:43
 */
public interface AdMessageLogService {
	
	AdMessageLogDO get(Long id);
	
	List<AdMessageLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AdMessageLogDO adMessageLog);
	
	int update(AdMessageLogDO adMessageLog);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	/**
	 * 批量发送营销短信
	 * @param sendList
	 * @param msgList
	 * @return
	 */
	int sendAdSmsBatch(List<AdSendExcelTelDTO> sendList, List<AdSendExcelMsgDTO> msgList);
}
