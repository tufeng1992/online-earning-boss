package com.powerboot.system.controller;

import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.domain.LoginLogDO;
import com.powerboot.system.service.LoginLogService;
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

/**
 * 登录日志表
 */
 
@Controller
@RequestMapping("/system/log")
public class LoginLogController {
	@Autowired
	private LoginLogService logService;
	
	@GetMapping()
	@RequiresPermissions("system:log:log")
	String Log(){
	    return "system/log/log";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:log:log")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LoginLogDO> logList = logService.list(query);
		int total = logService.count(query);
		PageUtils pageUtils = new PageUtils(logList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:log:add")
	String add(){
	    return "system/log/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:log:edit")
	String edit(@PathVariable("id") Long id,Model model){
		LoginLogDO log = logService.get(id);
		model.addAttribute("log", log);
	    return "system/log/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:log:add")
	public R save( LoginLogDO log){
		if(logService.save(log)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:log:edit")
	public R update( LoginLogDO log){
		logService.update(log);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:log:remove")
	public R remove( Long id){
		if(logService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:log:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		logService.batchRemove(ids);
		return R.ok();
	}
	
}
