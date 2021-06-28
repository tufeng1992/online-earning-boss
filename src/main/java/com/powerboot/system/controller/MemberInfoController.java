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

import com.powerboot.system.domain.MemberInfoDO;
import com.powerboot.system.service.MemberInfoService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 会员卡配置信息表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-06-24 22:07:10
 */
 
@Controller
@RequestMapping("/system/memberInfo")
public class MemberInfoController {
	@Autowired
	private MemberInfoService memberInfoService;
	
	@GetMapping()
	@RequiresPermissions("system:memberInfo:memberInfo")
	String MemberInfo(){
	    return "system/memberInfo/memberInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:memberInfo:memberInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MemberInfoDO> memberInfoList = memberInfoService.list(query);
		int total = memberInfoService.count(query);
		PageUtils pageUtils = new PageUtils(memberInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:memberInfo:add")
	String add(){
	    return "system/memberInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:memberInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		MemberInfoDO memberInfo = memberInfoService.get(id);
		model.addAttribute("memberInfo", memberInfo);
	    return "system/memberInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:memberInfo:add")
	public R save( MemberInfoDO memberInfo){
		if(memberInfoService.save(memberInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:memberInfo:edit")
	public R update( MemberInfoDO memberInfo){
		memberInfoService.update(memberInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:memberInfo:remove")
	public R remove( Long id){
		if(memberInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:memberInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		memberInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
