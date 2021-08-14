package com.powerboot.system.service.impl;

import com.google.common.collect.Lists;
import com.infobip.model.SmsResponse;
import com.infobip.model.SmsResponseDetails;
import com.powerboot.system.dto.AdSendExcelMsgDTO;
import com.powerboot.system.dto.AdSendExcelTelDTO;
import com.powerboot.utils.infobip.model.MessageDTO;
import com.powerboot.utils.infobip.utils.InfobMessageSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.AdMessageLogDao;
import com.powerboot.system.domain.AdMessageLogDO;
import com.powerboot.system.service.AdMessageLogService;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class AdMessageLogServiceImpl implements AdMessageLogService {
	@Autowired
	private AdMessageLogDao adMessageLogDao;

	@Autowired
	private InfobMessageSendUtil infobMessageSendUtil;
	
	@Override
	public AdMessageLogDO get(Long id){
		return adMessageLogDao.get(id);
	}
	
	@Override
	public List<AdMessageLogDO> list(Map<String, Object> map){
		return adMessageLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return adMessageLogDao.count(map);
	}
	
	@Override
	public int save(AdMessageLogDO adMessageLog){
		return adMessageLogDao.save(adMessageLog);
	}
	
	@Override
	public int update(AdMessageLogDO adMessageLog){
		return adMessageLogDao.update(adMessageLog);
	}
	
	@Override
	public int remove(Long id){
		return adMessageLogDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return adMessageLogDao.batchRemove(ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int sendAdSmsBatch(List<AdSendExcelTelDTO> sendList, List<AdSendExcelMsgDTO> msgList) {
		List<MessageDTO> messageDTOList = Lists.newArrayList();
		int res = 0;
		int temp = 0;
		int msgSize = msgList.size();
		for (AdSendExcelTelDTO adSendExcelTelDTO : sendList) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setTel(adSendExcelTelDTO.getTel().trim());
			AdSendExcelMsgDTO msgDTO = msgList.get(temp);
			messageDTO.setMsg(msgDTO.getMsg().trim());
			temp++;
			if (temp >= msgSize) {
				temp = 0;
			}
			messageDTOList.add(messageDTO);
		}
		MessageDTO[] messageDTOS = new MessageDTO[messageDTOList.size()];
		SmsResponse smsResponse = infobMessageSendUtil.sendSms(messageDTOList.toArray(messageDTOS));
		for (MessageDTO messageDTO : messageDTOS) {
			AdMessageLogDO adMessageLogDO = new AdMessageLogDO();
			adMessageLogDO.setFrom(messageDTO.getFrom());
			adMessageLogDO.setMsg(messageDTO.getMsg());
			adMessageLogDO.setMobile(messageDTO.getTel());
			adMessageLogDO.setMvno("infob");
			SmsResponseDetails smsResponseDetails = smsResponse.getMessages().stream().filter(m -> m.getTo().equalsIgnoreCase(messageDTO.getTel())).findFirst().orElse(null);
			if (null != smsResponseDetails && "PENDING_ACCEPTED".equalsIgnoreCase(smsResponseDetails.getStatus().getName())) {
				adMessageLogDO.setSendStatus(0);
			} else {
				adMessageLogDO.setSendStatus(2);
			}
			res += adMessageLogDao.save(adMessageLogDO);
		}
		log.info("sendAdSmsBatch : " + smsResponse);
		return res;
	}
}
