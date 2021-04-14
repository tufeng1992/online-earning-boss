package com.powerboot.system.controller;

import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.domain.DictDO;
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

import com.powerboot.system.service.DictService;

/**
 * 数据配置表
 */
 
@Controller
@RequestMapping("/system/dict")
public class DictController {
	@Autowired
	private DictService dictService;
	
	@GetMapping()
	@RequiresPermissions("system:dict:dict")
	String Dict(){
	    return "system/dict/dict";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:dict:dict")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DictDO> dictList = dictService.list(query);
		int total = dictService.count(query);
		PageUtils pageUtils = new PageUtils(dictList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:dict:add")
	String add(){
	    return "system/dict/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:dict:edit")
	String edit(@PathVariable("id") Long id,Model model){
		DictDO dict = dictService.get(id);
		model.addAttribute("dict", dict);
	    return "system/dict/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:dict:add")
	public R save( DictDO dict){
		if(dictService.save(dict)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:dict:edit")
	public R update( DictDO dict){
		dictService.update(dict);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:dict:remove")
	public R remove( Long id){
		if(dictService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:dict:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		dictService.batchRemove(ids);
		return R.ok();
	}
	
}
