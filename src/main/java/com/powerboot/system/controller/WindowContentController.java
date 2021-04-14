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

import com.powerboot.system.domain.WindowContentDO;
import com.powerboot.system.service.WindowContentService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 弹窗信息配置表
 * 
 */
 
@Controller
@RequestMapping("/system/windowContent")
public class WindowContentController {
	@Autowired
	private WindowContentService windowContentService;
	
	@GetMapping()
	@RequiresPermissions("system:windowContent:windowContent")
	String WindowContent(){
	    return "system/windowContent/windowContent";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:windowContent:windowContent")
	public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
		List<WindowContentDO> windowContentList = windowContentService.list(query);
		int total = windowContentService.count(query);
		PageUtils pageUtils = new PageUtils(windowContentList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:windowContent:add")
	String add(){
	    return "system/windowContent/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:windowContent:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		WindowContentDO windowContent = windowContentService.get(id);
		model.addAttribute("windowContent", windowContent);
	    return "system/windowContent/edit";
	}
	
	/**
	 * 保存
	 */
	@Log
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:windowContent:add")
	public R save( WindowContentDO windowContent){
		if(windowContentService.save(windowContent)>0){
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
	@RequiresPermissions("system:windowContent:edit")
	public R update( WindowContentDO windowContent){
		windowContentService.update(windowContent);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:windowContent:remove")
	public R remove( Integer id){
		if(windowContentService.remove(id)>0){
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
	@RequiresPermissions("system:windowContent:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		windowContentService.batchRemove(ids);
		return R.ok();
	}
	
}
