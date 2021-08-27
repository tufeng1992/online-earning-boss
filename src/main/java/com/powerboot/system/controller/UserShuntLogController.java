package com.powerboot.system.controller;

import java.util.List;
import java.util.Map;

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

import com.powerboot.system.domain.UserShuntLogDO;
import com.powerboot.system.service.UserShuntLogService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 用户分流信息记录
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
 
@Controller
@RequestMapping("/system/userShuntLog")
public class UserShuntLogController {
	@Autowired
	private UserShuntLogService userShuntLogService;
	
	@GetMapping()
	@RequiresPermissions("system:userShuntLog:userShuntLog")
	String UserShuntLog(){
	    return "system/userShuntLog/userShuntLog";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:userShuntLog:userShuntLog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserShuntLogDO> userShuntLogList = userShuntLogService.list(query);
		int total = userShuntLogService.count(query);
		PageUtils pageUtils = new PageUtils(userShuntLogList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:userShuntLog:add")
	String add(){
	    return "system/userShuntLog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:userShuntLog:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserShuntLogDO userShuntLog = userShuntLogService.get(id);
		model.addAttribute("userShuntLog", userShuntLog);
	    return "system/userShuntLog/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:userShuntLog:add")
	public R save( UserShuntLogDO userShuntLog){
		if(userShuntLogService.save(userShuntLog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:userShuntLog:edit")
	public R update( UserShuntLogDO userShuntLog){
		userShuntLogService.update(userShuntLog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:userShuntLog:remove")
	public R remove( Long id){
		if(userShuntLogService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:userShuntLog:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userShuntLogService.batchRemove(ids);
		return R.ok();
	}
	
}
