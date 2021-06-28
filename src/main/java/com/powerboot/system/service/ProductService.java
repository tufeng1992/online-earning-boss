package com.powerboot.system.service;

import com.powerboot.system.domain.ProductDO;

import java.util.List;
import java.util.Map;

/**
 * 刷单商品表
 * 
 * @author system
 * @email system@cc.cc
 * @date 2021-06-20 16:56:10
 */
public interface ProductService {
	
	ProductDO get(Long id);
	
	List<ProductDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProductDO product);
	
	int update(ProductDO product);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
