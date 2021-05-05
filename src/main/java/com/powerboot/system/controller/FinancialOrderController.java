package com.powerboot.system.controller;

import com.powerboot.common.controller.BaseController;
import com.powerboot.system.consts.FinancialOrderStatusEnum;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.FutureOrderDto;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.response.FinancialOrderResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.powerboot.system.service.AppUserService;
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

import com.powerboot.system.domain.FinancialOrderDO;
import com.powerboot.system.service.FinancialOrderService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 理财订单表
 */
 
@Controller
@RequestMapping("/system/financialOrder")
public class FinancialOrderController extends BaseController {
	@Autowired
	private FinancialOrderService financialOrderService;
	@Autowired
	private UserService sysUserService;
	@Autowired
	SysUserMappingService sysUserMappingService;
	@Autowired
	private AppUserService appUserService;
	
	@GetMapping()
	@RequiresPermissions("system:financialOrder:financialOrder")
	String FinancialOrder(){
	    return "system/financialOrder/financialOrder";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:financialOrder:financialOrder")
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
		List<FinancialOrderDO> financialOrderList = financialOrderService.list(query);
		List<FinancialOrderResponse> resultList = getResponseList(financialOrderList);
		int total = financialOrderService.count(query);
		PageUtils pageUtils = new PageUtils(resultList, total);
		return pageUtils;
	}

	@GetMapping("/futureOrder")
	@RequiresPermissions("system:financialOrder:futureOrder")
	String futureOrder(Model model) {
		List<FutureOrderDto> list = financialOrderService.futureOrder();
		model.addAttribute("list", list);
		return "system/financialOrder/futureOrder";
	}

	private List<FinancialOrderResponse> getResponseList(List<FinancialOrderDO> financialOrderList){
		if(CollectionUtils.isEmpty(financialOrderList)){
			return Collections.emptyList();
		}
		List<FinancialOrderResponse> responseList = new ArrayList<>(financialOrderList.size());
		financialOrderList.forEach(o->{
			FinancialOrderResponse response = new FinancialOrderResponse();
			BeanUtils.copyProperties(o,response);
			response.setOrderStatusStr(FinancialOrderStatusEnum.getMsgByCode(response.getOrderStatus()));
			if(!FinancialOrderStatusEnum.CALLED_AWAY.getCode().equals(response.getOrderStatus())){
				response.setCalledAmount(null);
				response.setCalledTime(null);
			}
			AppUserDO appUserDO = appUserService.getSaleInfo(o.getUserId().longValue());
			if (null != appUserDO) {
				response.setSaleMobile(appUserDO.getMobile());
			}
			responseList.add(response);
		});
		return responseList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:financialOrder:add")
	String add(){
	    return "system/financialOrder/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:financialOrder:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		FinancialOrderDO financialOrder = financialOrderService.get(id);
		model.addAttribute("financialOrder", financialOrder);
	    return "system/financialOrder/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:financialOrder:add")
	public R save( FinancialOrderDO financialOrder){
		if(financialOrderService.save(financialOrder)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:financialOrder:edit")
	public R update( FinancialOrderDO financialOrder){
		financialOrderService.update(financialOrder);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:financialOrder:remove")
	public R remove( Integer id){
		if(financialOrderService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:financialOrder:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		financialOrderService.batchRemove(ids);
		return R.ok();
	}
	
}
