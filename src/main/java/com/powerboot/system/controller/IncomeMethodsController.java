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

import com.powerboot.system.domain.IncomeMethodsDO;
import com.powerboot.system.service.IncomeMethodsService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 功能简介表
 */
 
@Controller
@RequestMapping("/system/incomeMethods")
public class IncomeMethodsController {
	@Autowired
	private IncomeMethodsService incomeMethodsService;
	
	@GetMapping()
	@RequiresPermissions("system:incomeMethods:incomeMethods")
	String IncomeMethods(){
	    return "system/incomeMethods/incomeMethods";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:incomeMethods:incomeMethods")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<IncomeMethodsDO> incomeMethodsList = incomeMethodsService.list(query);
		int total = incomeMethodsService.count(query);
		PageUtils pageUtils = new PageUtils(incomeMethodsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:incomeMethods:add")
	String add(){
	    return "system/incomeMethods/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:incomeMethods:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		IncomeMethodsDO incomeMethods = incomeMethodsService.get(id);
		model.addAttribute("incomeMethods", incomeMethods);
	    return "system/incomeMethods/edit";
	}
	
	/**
	 * 保存
	 */
	@Log
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:incomeMethods:add")
	public R save( IncomeMethodsDO incomeMethods){
		if(incomeMethodsService.save(incomeMethods)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:incomeMethods:edit")
	public R update( IncomeMethodsDO incomeMethods){
		incomeMethodsService.update(incomeMethods);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:incomeMethods:remove")
	public R remove( Integer id){
		if(incomeMethodsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@Log
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:incomeMethods:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		incomeMethodsService.batchRemove(ids);
		return R.ok();
	}
	
}
