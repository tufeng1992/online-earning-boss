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

import com.powerboot.system.domain.UserPrizeSourceDO;
import com.powerboot.system.service.UserPrizeSourceService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 用户奖励关联信息表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
 
@Controller
@RequestMapping("/system/userPrizeSource")
public class UserPrizeSourceController {
	@Autowired
	private UserPrizeSourceService userPrizeSourceService;
	
	@GetMapping()
	@RequiresPermissions("system:userPrizeSource:userPrizeSource")
	String UserPrizeSource(){
	    return "system/userPrizeSource/userPrizeSource";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:userPrizeSource:userPrizeSource")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserPrizeSourceDO> userPrizeSourceList = userPrizeSourceService.list(query);
		int total = userPrizeSourceService.count(query);
		PageUtils pageUtils = new PageUtils(userPrizeSourceList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:userPrizeSource:add")
	String add(){
	    return "system/userPrizeSource/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:userPrizeSource:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserPrizeSourceDO userPrizeSource = userPrizeSourceService.get(id);
		model.addAttribute("userPrizeSource", userPrizeSource);
	    return "system/userPrizeSource/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:userPrizeSource:add")
	public R save( UserPrizeSourceDO userPrizeSource){
		if(userPrizeSourceService.save(userPrizeSource)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:userPrizeSource:edit")
	public R update( UserPrizeSourceDO userPrizeSource){
		userPrizeSourceService.update(userPrizeSource);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:userPrizeSource:remove")
	public R remove( Long id){
		if(userPrizeSourceService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:userPrizeSource:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userPrizeSourceService.batchRemove(ids);
		return R.ok();
	}
	
}
