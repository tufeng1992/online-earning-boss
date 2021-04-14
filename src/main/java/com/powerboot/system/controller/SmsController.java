package com.powerboot.system.controller;

import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.domain.SmsDO;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.powerboot.system.service.SmsService;

/**
 * 短信验证码表
 * 
 */
 
@Controller
@RequestMapping("/system/sms")
public class SmsController {
	@Autowired
	private SmsService smsService;
	
	@GetMapping()
	@RequiresPermissions("system:sms:sms")
	String Sms(){
	    return "system/sms/sms";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:sms:sms")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SmsDO> smsList = smsService.list(query);
		int total = smsService.count(query);
		PageUtils pageUtils = new PageUtils(smsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:sms:add")
	String add(){
	    return "system/sms/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:sms:edit")
	String edit(@PathVariable("id") Long id,Model model){
		SmsDO sms = smsService.get(id);
		model.addAttribute("sms", sms);
	    return "system/sms/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sms:add")
	public R save( SmsDO sms){
		if(smsService.save(sms)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sms:edit")
	public R update( SmsDO sms){
		smsService.update(sms);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:sms:remove")
	public R remove( Long id){
		if(smsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sms:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		smsService.batchRemove(ids);
		return R.ok();
	}
	
}
