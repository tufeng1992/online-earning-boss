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

import com.powerboot.system.domain.ProductDO;
import com.powerboot.system.service.ProductService;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;

/**
 * 刷单商品表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-06-20 16:56:10
 */
 
@Controller
@RequestMapping("/system/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping()
	@RequiresPermissions("system:product:product")
	String Product(){
	    return "system/product/product";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:product:product")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProductDO> productList = productService.list(query);
		int total = productService.count(query);
		PageUtils pageUtils = new PageUtils(productList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:product:add")
	String add(){
	    return "system/product/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:product:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ProductDO product = productService.get(id);
		model.addAttribute("product", product);
	    return "system/product/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:product:add")
	public R save( ProductDO product){
		if(productService.save(product)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:product:edit")
	public R update( ProductDO product){
		String[] balanceArr = product.getBalanceInfo().split(",");
		if (balanceArr.length < 4) {
			return R.error("余额等级信息配置错误");
		}
		productService.update(product);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:product:remove")
	public R remove( Long id){
		if(productService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:product:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		productService.batchRemove(ids);
		return R.ok();
	}
	
}
