package com.model2.mvc.service.user;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.domain.User;

public interface UserDAO {
	
	public void insertUser(User user) throws Exception ;
	
	public User findUser(String userId) throws Exception ;
	
	public List<Object> getUserList(Search search) throws Exception ;
	
	public int getTotalCount(Search search) throws Exception ;
	
	public void updateUser(User user) throws Exception ;
	
	public void updateCart(Map<String,Object> map) throws Exception ;
	
	public String getCartData(String userId) throws Exception ;
}
