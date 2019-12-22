package com.model2.mvc.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.domain.User;

public interface UserService {
	
/*1*/ public void addUser(User user) throws Exception;
	
/*2*/ public User loginUser(User user) throws Exception;
	
/*3*/ public User getUser(String userId) throws Exception;
	
/*4*/ public Map<String,Object> getUserList(Search search) throws Exception;
	
/*5*/ public void updateUser(User user) throws Exception;
	
/*6*/ public boolean checkDuplication(String userId) throws Exception;
	
/*7*/ public void updateCart(String userId, String prodNo) throws Exception;
	
/*8*/ public Map<String,Object> getCartData(String prodNo, String userId) throws Exception;
	
/*9*/ public boolean checkDuplicationCartData(String prodNo,String userId) throws Exception;
	
/*10*/public void deleteCart(String userId, String prodNo) throws Exception;

	  public void init();
	  
	  public void destroy();
	
}