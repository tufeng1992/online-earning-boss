package com.powerboot.system.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.powerboot.common.controller.BaseController;
import com.powerboot.system.consts.BalanceTypeEnum;
import com.powerboot.system.consts.StatusTypeEnum;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.response.BalanceResponse;
import com.powerboot.system.service.BalanceService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.service.UserService;
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

import com.powerboot.system.domain.BalanceDO;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 余额流水表
 */
 
@Controller
@RequestMapping("/system/balance")
public class BalanceController extends BaseController {
	@Autowired
	private BalanceService balanceService;
	@Autowired
	private UserService sysUserService;
	@Autowired
	SysUserMappingService sysUserMappingService;
	
	@GetMapping()
	@RequiresPermissions("system:balance:balance")
	String Balance(){
	    return "system/balance/balance";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:balance:balance")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//当前登录boss的账号id
		Long sysUserId = this.getUserId();
		UserDO sysUser = sysUserService.get(sysUserId);
		//参与团队管理
		if(sysUser.getTeamFlag()==1){
			List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
			List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(saleIds)){
				return new PageUtils(Collections.emptyList(), 0);
			}
			params.put("saleIdList",saleIds);
		}
		//查询列表数据
        Query query = new Query(params);
		List<BalanceDO> balanceList = balanceService.list(query);
		int total = balanceService.count(query);
		List<BalanceResponse> resultList = new ArrayList<>();
		balanceList.forEach(o->{
			BalanceResponse balanceResponse = new BalanceResponse();
			BeanUtils.copyProperties(o,balanceResponse);
			balanceResponse.setTypeStr(BalanceTypeEnum.getMsgByCode(balanceResponse.getType()));
			balanceResponse.setStatusStr(StatusTypeEnum.getMsgByCode(balanceResponse.getStatus()));
			resultList.add(balanceResponse);
		});
		PageUtils pageUtils = new PageUtils(resultList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:balance:add")
	String add(){
	    return "system/balance/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:balance:edit")
	String edit(@PathVariable("id") Long id,Model model){
		BalanceDO balance = balanceService.get(id);
		model.addAttribute("balance", balance);
	    return "system/balance/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:balance:add")
	public R save( BalanceDO balance){
		if(balanceService.save(balance)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:balance:edit")
	public R update( BalanceDO balance){
		balanceService.update(balance);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:balance:remove")
	public R remove( Long id){
		if(balanceService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:balance:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		balanceService.batchRemove(ids);
		return R.ok();
	}
	
}
