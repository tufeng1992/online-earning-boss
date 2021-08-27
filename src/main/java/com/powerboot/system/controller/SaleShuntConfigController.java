package com.powerboot.system.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.powerboot.common.controller.BaseController;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.response.SaleShuntConfigResponse;
import com.powerboot.system.service.*;
import org.apache.commons.collections.CollectionUtils;
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

import com.powerboot.system.domain.SaleShuntConfigDO;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 客服分流配置表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-08-15 14:40:42
 */
 
@Controller
@RequestMapping("/system/saleShuntConfig")
public class SaleShuntConfigController extends BaseController {
	@Autowired
	private SaleShuntConfigService saleShuntConfigService;

	@Autowired
	private UserShuntLogService userShuntLogService;

	@Autowired
	private UserService sysUserService;

	@Autowired
	private AppUserService userService;

	@Autowired
	private SysUserMappingService sysUserMappingService;
	
	@GetMapping()
	@RequiresPermissions("system:saleShuntConfig:saleShuntConfig")
	String SaleShuntConfig(Model model){
		List<UserDO> userDOList = sysUserService.list(Maps.newHashMap());
		List<String> mobiles = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(userDOList)) {
			userDOList.forEach(userDO -> mobiles.add(userDO.getUserId().toString()));
		}
		List<AppUserDO> appUserDOList = userService.getByMobiles(mobiles);
		model.addAttribute("appUserDOList", appUserDOList);
	    return "system/saleShuntConfig/saleShuntConfig";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:saleShuntConfig:saleShuntConfig")
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
		List<SaleShuntConfigDO> saleShuntConfigList = saleShuntConfigService.list(query);
		int total = saleShuntConfigService.count(query);

		List<SaleShuntConfigResponse> res = Lists.newArrayList();
		saleShuntConfigList.forEach(saleShuntConfigDO -> {
			SaleShuntConfigResponse response = new SaleShuntConfigResponse();
			BeanUtils.copyProperties(saleShuntConfigDO, response);
			params.put("saleId", saleShuntConfigDO.getSaleId());
			int shuntCount = userShuntLogService.countBySaleId(params);
			response.setShuntCount(shuntCount);
			res.add(response);
		});
		PageUtils pageUtils = new PageUtils(res, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/shuntCount")
	@RequiresPermissions("system:saleShuntConfig:saleShuntConfig")
	public R selectShuntCount(@RequestParam Map<String, Object> params){
		Long sysUserId = this.getUserId();
		UserDO sysUser = sysUserService.get(sysUserId);
		Integer shuntCount = 0;
		R r = R.ok();
		r.put("shuntCount", shuntCount);
		if (sysUser.getTeamFlag() == 1) {
			List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
			List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId)
					.collect(Collectors.toList());
			if (CollectionUtils.isEmpty(saleIds)) {
				return r;
			}
			params.put("saleIdList", saleIds);
		}
		//查询列表数据
		shuntCount += userShuntLogService.countBySaleId(params);
		r.put("shuntCount", shuntCount);
		return r;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:saleShuntConfig:add")
	String add(Model model){
		List<UserDO> userDOList = sysUserService.list(Maps.newHashMap());
		List<String> mobiles = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(userDOList)) {
			userDOList.forEach(userDO -> mobiles.add(userDO.getUserId().toString()));
		}
		List<AppUserDO> appUserDOList = userService.getByMobiles(mobiles);
		model.addAttribute("appUserDOList", appUserDOList);
	    return "system/saleShuntConfig/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:saleShuntConfig:edit")
	String edit(@PathVariable("id") Long id,Model model){
		List<UserDO> userDOList = sysUserService.list(Maps.newHashMap());
		List<String> mobiles = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(userDOList)) {
			userDOList.forEach(userDO -> mobiles.add(userDO.getUserId().toString()));
		}
		List<AppUserDO> appUserDOList = userService.getByMobiles(mobiles);
		model.addAttribute("appUserDOList", appUserDOList);
		SaleShuntConfigDO saleShuntConfig = saleShuntConfigService.get(id);
		model.addAttribute("saleShuntConfig", saleShuntConfig);
	    return "system/saleShuntConfig/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:saleShuntConfig:add")
	public R save(SaleShuntConfigDO saleShuntConfig){
		if(saleShuntConfigService.save(saleShuntConfig)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:saleShuntConfig:edit")
	public R update( SaleShuntConfigDO saleShuntConfig){
		saleShuntConfigService.update(saleShuntConfig);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:saleShuntConfig:remove")
	public R remove( Long id){
		if(saleShuntConfigService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:saleShuntConfig:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		saleShuntConfigService.batchRemove(ids);
		return R.ok();
	}
	
}
