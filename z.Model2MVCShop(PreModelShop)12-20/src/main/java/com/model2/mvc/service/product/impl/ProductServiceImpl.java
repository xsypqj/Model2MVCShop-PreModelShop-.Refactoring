package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.domain.Product;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{

	//Field
	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAOImpl;
	public void setProductDAO(ProductDAO productDAOImpl) {
		this.productDAOImpl = productDAOImpl;
	}
	
	//Constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}
	
	//Method
	public void addProduct(Product product) throws Exception {
		productDAOImpl.addProduct(product);
	}
	
	public Product getProduct(int prodNo) throws Exception {
		return productDAOImpl.getProduct(prodNo);
	}
	
	public Map<String, Object> getProductList(Search search) throws Exception {		
		List<Object> list = productDAOImpl.getProductList(search);
		int totalCount = productDAOImpl.getTotalCount(search);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	public void updateProduct(Product product) throws Exception {
		productDAOImpl.updateProduct(product);
 	}
	
	public Map<String,Object> getCartList(String cart) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<String> list = productDAOImpl.getCartList(cart);
		int totalCount = productDAOImpl.getCartCount(cart);
		
		map.put("totalCount", totalCount);
		map.put("list", list);
		
		return map;
		
	}
	//public Map<String,Object> getCartList(String cart) throws Exception{
	//	return dao.getCartList(cart);
	//}

	

}
