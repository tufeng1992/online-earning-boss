package com.powerboot.system.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.powerboot.common.controller.BaseController;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.service.UserService;
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

import com.powerboot.system.domain.BlackUserLogDO;
import com.powerboot.system.service.BlackUserLogService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 用户拉黑记录表
 * @author system
 * @email system@cc.cc
 * @date 2021-07-16 21:28:49
 */
 
@Controller
@RequestMapping("/system/blackUserLog")
public class BlackUserLogController extends BaseController {
	@Autowired
	private BlackUserLogService blackUserLogService;

	@Autowired
	SysUserMappingService sysUserMappingService;

	@Autowired
	private UserService sysUserService;
	
	@GetMapping()
	@RequiresPermissions("system:blackUserLog:blackUserLog")
	String BlackUserLog(){
	    return "system/blackUserLog/blackUserLog";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:blackUserLog:blackUserLog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		Long sysUserId = this.getUserId();
		UserDO sysUser = sysUserService.get(sysUserId);
		if (sysUser.getTeamFlag() == 1) {
			List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
			List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId)
					.collect(Collectors.toList());
			if (CollectionUtils.isEmpty(saleIds)) {
				return new PageUtils(Collections.emptyList(), 0);
			}
			params.put("saleIdList", saleIds);
		}
		//查询列表数据
        Query query = new Query(params);
		List<BlackUserLogDO> blackUserLogList = blackUserLogService.list(query);
		int total = blackUserLogService.count(query);
		PageUtils pageUtils = new PageUtils(blackUserLogList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:blackUserLog:add")
	String add(){
	    return "system/blackUserLog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:blackUserLog:edit")
	String edit(@PathVariable("id") Long id,Model model){
		BlackUserLogDO blackUserLog = blackUserLogService.get(id);
		model.addAttribute("blackUserLog", blackUserLog);
	    return "system/blackUserLog/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:blackUserLog:add")
	public R save( BlackUserLogDO blackUserLog){
		if(blackUserLogService.save(blackUserLog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:blackUserLog:edit")
	public R update( BlackUserLogDO blackUserLog){
		blackUserLogService.update(blackUserLog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:blackUserLog:remove")
	public R remove( Long id){
		if(blackUserLogService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:blackUserLog:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		blackUserLogService.batchRemove(ids);
		return R.ok();
	}
	
}
