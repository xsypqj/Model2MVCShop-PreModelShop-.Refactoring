package com.model2.mvc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.SearchCounter;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.user.UserDAO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.domain.User;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	//Field
	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAO;
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	//Constructor
	public UserServiceImpl() {
		System.out.println(this.getClass());
	}
	
	//Method
	public void addUser(User user) throws Exception {
		userDAO.insertUser(user);
	}

	public User loginUser(User user) throws Exception {
			User dbUser=userDAO.findUser(user.getUserId());

			if(! dbUser.getPassword().equals(user.getPassword())) {
				throw new Exception("로그인에 실패했습니다.");
			}
			return dbUser;
	}

	public User getUser(String userId) throws Exception {
		return userDAO.findUser(userId);
	}

	public Map<String,Object> getUserList(Search search) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list = userDAO.getUserList(search);
		int totalCount = userDAO.getTotalCount(search);
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}

	public void updateUser(User user) throws Exception {
		userDAO.updateUser(user);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDAO.findUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
	
	public void updateCart(String prodNo,String userId) throws Exception {
		if(this.checkDuplicationCartData(prodNo, userId)) {
		String cartData = (String)this.getCartData(prodNo, userId).get("cartData");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cartData", cartData);
		map.put("userId", userId);
		userDAO.updateCart(map);
		}
	}
	
	public Map<String,Object> getCartData(String prodNo, String userId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		boolean door = false;
		String searchCartData;
		String cartData = "";
		if(prodNo!=null && !prodNo.equals("")) {
			cartData += prodNo+",";
		}
		String[] strArray;	
		if(userDAO.getCartData(userId) != null) {
			searchCartData = userDAO.getCartData(userId)+",";
			strArray = searchCartData.split(",");
			for(int i=0; i<strArray.length; i++) {
				if(strArray[i].equals(prodNo)) {
					door = true;
				}
			}
			if(door) {
				cartData = searchCartData;
			}else {
				cartData += searchCartData;
			}
			//for(Object obj : strArray) {
			//	System.out.println("strArray :: "+obj);
			//}
		}
		
		if(cartData.length() > 0) {
		cartData = cartData.substring(0,cartData.lastIndexOf(","));
			if(cartData.substring(cartData.length()-1).equals(",")) {
				cartData = cartData.substring(0,cartData.lastIndexOf(","));
			}
		}
		//System.out.println("cartData :: "+cartData);
		
		map.put("cartData", cartData);
		map.put("door", door);
		
		return map;
	}
	
	public boolean checkDuplicationCartData(String prodNo,String userId) throws Exception{
		boolean result=true;
		if((boolean)this.getCartData(prodNo, userId).get("door")) {
			result=false;
		}
		
		return result;
	}
	
	public void deleteCart(String userId, String prodNo) throws Exception{
		String[] parse = ((String)this.getCartData(prodNo, userId).get("cartData")).split(",");
		//System.out.println("parse[0]::"+parse[0]);
		String resultCart="";
		//System.out.println("parse.length"+parse.length);
		for(int i=0; i<parse.length; i++) {
			
			if(!parse[i].equals(prodNo)) {
				resultCart += parse[i]+",";
			}
		}
		if(resultCart.lastIndexOf(",") > 0) {
		resultCart = resultCart.substring(0,resultCart.lastIndexOf(","));
		}
		//System.out.println("resultCart ==============================> "+resultCart);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cartData", resultCart);
		map.put("userId", userId);
		userDAO.updateCart(map);	
	}
	
	@PostConstruct
	public void init() {
		SearchCounter.getSearchCounter().setProductService(productDAO);
		System.out.println("Method com.model2.mvc.service.user.impl.UserServiceImpl.init()");
		SearchCounter.getSearchCounter().makeCountMap();	
	}
	
	@PreDestroy
	public void destroy() {
		try {
			SearchCounter.getSearchCounter().fileWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}