package com.powerboot.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.powerboot.system.dto.AdSendExcelMsgDTO;
import com.powerboot.system.dto.AdSendExcelTelDTO;
import com.powerboot.utils.infobip.model.MessageDTO;
import com.powerboot.utils.infobip.utils.InfobMessageSendUtil;
import com.powerboot.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.powerboot.system.domain.AdMessageLogDO;
import com.powerboot.system.service.AdMessageLogService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 运营短信发送记录
 *
 * @author system
 * @email system@cc.cc
 * @date 2021-08-08 11:04:43
 */

@Controller
@RequestMapping("/system/adMessageLog")
@Slf4j
public class AdMessageLogController {
	@Autowired
	private AdMessageLogService adMessageLogService;

	@GetMapping()
	@RequiresPermissions("system:adMessageLog:adMessageLog")
	String AdMessageLog(){
		return "system/adMessageLog/adMessageLog";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:adMessageLog:adMessageLog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<AdMessageLogDO> adMessageLogList = adMessageLogService.list(query);
		int total = adMessageLogService.count(query);
		PageUtils pageUtils = new PageUtils(adMessageLogList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("system:adMessageLog:add")
	String add(){
		return "system/adMessageLog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:adMessageLog:edit")
	String edit(@PathVariable("id") Long id,Model model){
		AdMessageLogDO adMessageLog = adMessageLogService.get(id);
		model.addAttribute("adMessageLog", adMessageLog);
		return "system/adMessageLog/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:adMessageLog:add")
	public R save( AdMessageLogDO adMessageLog){
		if(adMessageLogService.save(adMessageLog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:adMessageLog:edit")
	public R update( AdMessageLogDO adMessageLog){
		adMessageLogService.update(adMessageLog);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:adMessageLog:remove")
	public R remove( Long id){
		if(adMessageLogService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:adMessageLog:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		adMessageLogService.batchRemove(ids);
		return R.ok();
	}


	@GetMapping("/adSendPage")
	public String adSendPage(){
		return "system/adMessageLog/adSendPage";
	}

	@ResponseBody
	@PostMapping("/adSendBatch")
	public R adSendBatch(@RequestParam("sendFile") MultipartFile sendFile,
						 @RequestParam("msgFile") MultipartFile msgFile, HttpServletRequest request) {
		try {
			List<AdSendExcelTelDTO> sendList = new ExcelUtil<>(AdSendExcelTelDTO.class).importExcel(sendFile.getInputStream());
			if (CollectionUtils.isEmpty(sendList)) {
				return R.error("发送号码不能为空！");
			}
			List<AdSendExcelMsgDTO> msgList = new ExcelUtil<>(AdSendExcelMsgDTO.class).importExcel(msgFile.getInputStream());
			if (CollectionUtils.isEmpty(msgList)) {
				return R.error("发送内容不能为空！");
			}
			adMessageLogService.sendAdSmsBatch(sendList, msgList);
		} catch (Exception e) {
			log.error("批量发送失败", e);
			return R.error("批量发送失败！");
		}
		return R.ok();
	}



}
