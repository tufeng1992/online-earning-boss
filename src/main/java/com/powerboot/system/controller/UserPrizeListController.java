package com.powerboot.system.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.powerboot.common.controller.BaseController;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.service.*;
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

import com.powerboot.system.domain.UserPrizeListDO;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 用户奖励信息表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-07-25 14:31:40
 */
 
@Controller
@RequestMapping("/system/userPrizeList")
public class UserPrizeListController extends BaseController {
	@Autowired
	private UserPrizeListService userPrizeListService;

    @Autowired
    SysUserMappingService sysUserMappingService;
    @Autowired
    private UserService sysUserService;
	
	@GetMapping()
	@RequiresPermissions("system:userPrizeList:userPrizeList")
	String UserPrizeList(){
	    return "system/userPrizeList/userPrizeList";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:userPrizeList:userPrizeList")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据

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
		Query query = new Query(params);
		List<UserPrizeListDO> userPrizeListList = userPrizeListService.list(query);
		int total = userPrizeListService.count(query);
		PageUtils pageUtils = new PageUtils(userPrizeListList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:userPrizeList:add")
	String add(){
	    return "system/userPrizeList/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:userPrizeList:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserPrizeListDO userPrizeList = userPrizeListService.get(id);
		model.addAttribute("userPrizeList", userPrizeList);
	    return "system/userPrizeList/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:userPrizeList:add")
	public R save( UserPrizeListDO userPrizeList){
		if(userPrizeListService.save(userPrizeList)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:userPrizeList:edit")
	public R update( UserPrizeListDO userPrizeList){
		userPrizeListService.update(userPrizeList);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:userPrizeList:remove")
	public R remove( Long id){
		if(userPrizeListService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:userPrizeList:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userPrizeListService.batchRemove(ids);
		return R.ok();
	}
	
}
