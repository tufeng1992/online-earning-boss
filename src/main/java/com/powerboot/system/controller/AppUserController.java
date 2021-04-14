package com.powerboot.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerboot.system.consts.UserRoleEnum;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.response.AppUserResponse;
import com.powerboot.system.service.AppUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 用户表
 */
@Controller
@RequestMapping("/system/appUser")
public class AppUserController {
	@Autowired
	private AppUserService userService;
	
	@GetMapping()
	@RequiresPermissions("system:appUser:appUser")
	String User(){
	    return "system/appUser/appUser";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:appUser:appUser")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AppUserDO> userList = userService.list(query);
		int total = userService.count(query);
		List<AppUserResponse> resultList = new ArrayList<>();
		userList.forEach(user->{
			AppUserResponse appUserResponse = new AppUserResponse();
			BeanUtils.copyProperties(user,appUserResponse);
			appUserResponse.setRoleName(UserRoleEnum.getMsgByCode(appUserResponse.getRole()));
			appUserResponse.setBindStatusStr(appUserResponse.getBindStatus().equals(0)?"未绑定":"已绑定");
			appUserResponse.setFirstRechargeStr(appUserResponse.getFirstRecharge().equals(0)?"未完成":"已完成");
			appUserResponse.setBlackFlagStr(appUserResponse.getBlackFlag().equals(0)?"否":"是");
			resultList.add(appUserResponse);
		});
		PageUtils pageUtils = new PageUtils(resultList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:appUser:add")
	String add(){
	    return "system/appUser/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:appUser:edit")
	String edit(@PathVariable("id") Long id,Model model){
		AppUserDO user = userService.get(id);
		model.addAttribute("user", user);
	    return "system/appUser/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:appUser:add")
	public R save( AppUserDO user){
		if(userService.save(user)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:appUser:edit")
	public R update( AppUserDO user){
		userService.update(user);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:appUser:remove")
	public R remove( Long id){
		if(userService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:appUser:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userService.batchRemove(ids);
		return R.ok();
	}
	
}
