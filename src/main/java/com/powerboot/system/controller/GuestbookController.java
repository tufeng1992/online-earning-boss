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

import com.powerboot.system.domain.GuestbookDO;
import com.powerboot.system.service.GuestbookService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 留言板
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-05-03 15:56:09
 */
 
@Controller
@RequestMapping("/system/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping()
	@RequiresPermissions("system:guestbook:guestbook")
	String Guestbook(){
	    return "system/guestbook/guestbook";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:guestbook:guestbook")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GuestbookDO> guestbookList = guestbookService.list(query);
		int total = guestbookService.count(query);
		PageUtils pageUtils = new PageUtils(guestbookList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:guestbook:add")
	String add(Long guestbookTargetId, Model model){
		if (null != guestbookTargetId) {
			model.addAttribute("guestbookTargetId", guestbookTargetId);
		}
	    return "system/guestbook/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:guestbook:edit")
	String edit(@PathVariable("id") Long id,Model model){
		GuestbookDO guestbook = guestbookService.get(id);
		model.addAttribute("guestbook", guestbook);
	    return "system/guestbook/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:guestbook:add")
	public R save( GuestbookDO guestbook){
		if(guestbookService.save(guestbook)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:guestbook:edit")
	public R update( GuestbookDO guestbook){
		guestbookService.update(guestbook);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:guestbook:remove")
	public R remove( Long id){
		if(guestbookService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:guestbook:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		guestbookService.batchRemove(ids);
		return R.ok();
	}
	
}
