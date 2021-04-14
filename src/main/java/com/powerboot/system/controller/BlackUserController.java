package com.powerboot.system.controller;

import java.util.List;
import java.util.Map;

import com.powerboot.common.annotation.Log;
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

import com.powerboot.system.domain.BlackUserDO;
import com.powerboot.system.service.BlackUserService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 黑名单用户表
 */
 
@Controller
@RequestMapping("/system/blackUser")
public class BlackUserController {
	@Autowired
	private BlackUserService blackUserService;
	
	@GetMapping()
	@RequiresPermissions("system:blackUser:blackUser")
	String BlackUser(){
	    return "system/blackUser/blackUser";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:blackUser:blackUser")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BlackUserDO> blackUserList = blackUserService.list(query);
		int total = blackUserService.count(query);
		PageUtils pageUtils = new PageUtils(blackUserList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:blackUser:add")
	String add(){
	    return "system/blackUser/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:blackUser:edit")
	String edit(@PathVariable("id") Long id,Model model){
		BlackUserDO blackUser = blackUserService.get(id);
		model.addAttribute("blackUser", blackUser);
	    return "system/blackUser/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("添加黑名单")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:blackUser:add")
	public R save( BlackUserDO blackUser){
		if(blackUserService.save(blackUser)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log("修改黑名单")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:blackUser:edit")
	public R update( BlackUserDO blackUser){
		blackUserService.update(blackUser);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除黑名单")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:blackUser:remove")
	public R remove( Long id){
		if(blackUserService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:blackUser:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		blackUserService.batchRemove(ids);
		return R.ok();
	}
	
}
