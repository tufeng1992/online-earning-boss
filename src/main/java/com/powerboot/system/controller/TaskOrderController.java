package com.powerboot.system.controller;

import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.domain.OrderDO;
import com.powerboot.system.response.TaskOrderResponse;
import com.powerboot.system.service.OrderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 刷单订单表
 * 
 */

@Controller
@RequestMapping("/system/taskOrder")
public class TaskOrderController {
	@Autowired
	private OrderService taskOrderService;
	
	@GetMapping()
	@RequiresPermissions("system:taskOrder:taskOrder")
	String taskOrder(){
	    return "system/taskOrder/taskOrder";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:taskOrder:taskOrder")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OrderDO> taskOrderList = taskOrderService.list(query);
		List<TaskOrderResponse> resultList = getResponseList(taskOrderList);
		int total = taskOrderService.count(query);
		PageUtils pageUtils = new PageUtils(resultList, total);
		return pageUtils;
	}

	private List<TaskOrderResponse> getResponseList(List<OrderDO> taskOrderList){
		if(CollectionUtils.isEmpty(taskOrderList)){
			return Collections.emptyList();
		}
		List<TaskOrderResponse> responseList = new ArrayList<>(taskOrderList.size());
		taskOrderList.forEach(o->{
			TaskOrderResponse response = new TaskOrderResponse();
			BeanUtils.copyProperties(o,response);
			responseList.add(response);
		});
		return responseList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:taskOrder:add")
	String add(){
	    return "system/taskOrder/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:taskOrder:edit")
	String edit(@PathVariable("id") Long id,Model model){
		OrderDO taskOrder = taskOrderService.get(id);
		model.addAttribute("taskOrder", taskOrder);
	    return "system/taskOrder/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:taskOrder:add")
	public R save( OrderDO taskOrder){
		if(taskOrderService.save(taskOrder)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:taskOrder:edit")
	public R update( OrderDO taskOrder){
		taskOrderService.update(taskOrder);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:taskOrder:remove")
	public R remove( Long id){
		if(taskOrderService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:taskOrder:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		taskOrderService.batchRemove(ids);
		return R.ok();
	}
	
}
