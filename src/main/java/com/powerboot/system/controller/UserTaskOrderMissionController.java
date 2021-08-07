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

import com.powerboot.system.domain.UserTaskOrderMissionDO;
import com.powerboot.system.service.UserTaskOrderMissionService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 刷单任务完成记录表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
 
@Controller
@RequestMapping("/system/userTaskOrderMission")
public class UserTaskOrderMissionController {
	@Autowired
	private UserTaskOrderMissionService userTaskOrderMissionService;
	
	@GetMapping()
	@RequiresPermissions("system:userTaskOrderMission:userTaskOrderMission")
	String UserTaskOrderMission(){
	    return "system/userTaskOrderMission/userTaskOrderMission";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:userTaskOrderMission:userTaskOrderMission")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserTaskOrderMissionDO> userTaskOrderMissionList = userTaskOrderMissionService.list(query);
		int total = userTaskOrderMissionService.count(query);
		PageUtils pageUtils = new PageUtils(userTaskOrderMissionList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:userTaskOrderMission:add")
	String add(){
	    return "system/userTaskOrderMission/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:userTaskOrderMission:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserTaskOrderMissionDO userTaskOrderMission = userTaskOrderMissionService.get(id);
		model.addAttribute("userTaskOrderMission", userTaskOrderMission);
	    return "system/userTaskOrderMission/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:userTaskOrderMission:add")
	public R save( UserTaskOrderMissionDO userTaskOrderMission){
		if(userTaskOrderMissionService.save(userTaskOrderMission)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:userTaskOrderMission:edit")
	public R update( UserTaskOrderMissionDO userTaskOrderMission){
		userTaskOrderMissionService.update(userTaskOrderMission);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:userTaskOrderMission:remove")
	public R remove( Long id){
		if(userTaskOrderMissionService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:userTaskOrderMission:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userTaskOrderMissionService.batchRemove(ids);
		return R.ok();
	}
	
}
