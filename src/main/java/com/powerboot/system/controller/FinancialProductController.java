package com.powerboot.system.controller;

import com.powerboot.common.annotation.Log;
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

import com.powerboot.system.domain.FinancialProductDO;
import com.powerboot.system.service.FinancialProductService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 理财产品表
 */
 
@Controller
@RequestMapping("/system/financialProduct")
public class FinancialProductController {
	@Autowired
	private FinancialProductService financialProductService;
	
	@GetMapping()
	@RequiresPermissions("system:financialProduct:financialProduct")
	String FinancialProduct(){
	    return "system/financialProduct/financialProduct";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:financialProduct:financialProduct")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<FinancialProductDO> financialProductList = financialProductService.list(query);
		int total = financialProductService.count(query);
		PageUtils pageUtils = new PageUtils(financialProductList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:financialProduct:add")
	String add(){
	    return "system/financialProduct/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:financialProduct:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		FinancialProductDO financialProduct = financialProductService.get(id);
		model.addAttribute("financialProduct", financialProduct);
	    return "system/financialProduct/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("新增理财产品")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:financialProduct:add")
	public R save( FinancialProductDO financialProduct){
		if(financialProductService.save(financialProduct)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log("修改理财产品")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:financialProduct:edit")
	public R update( FinancialProductDO financialProduct){
		financialProductService.update(financialProduct);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除理财产品")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:financialProduct:remove")
	public R remove( Integer id){
		if(financialProductService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:financialProduct:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		financialProductService.batchRemove(ids);
		return R.ok();
	}
	
}
