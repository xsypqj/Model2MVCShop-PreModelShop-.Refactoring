package com.model2.mvc.service.user.impl;
		
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.domain.Product;
import com.model2.mvc.service.user.UserDAO;
import com.model2.mvc.service.user.domain.User;
		
@Repository("userDAOImpl")
public class UserDAOImpl implements UserDAO{
	
	//Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//Constructor
	public UserDAOImpl(){
		System.out.println(this.getClass());
	}
	
	//Method
	public void insertUser(User user) throws Exception {	
		sqlSession.insert("UserMapper.addUser",user);
	}	
		
	public User findUser(String userId) throws Exception {
		return sqlSession.selectOne("UserMapper.getUser", userId);
	}
		
	public List<Object> getUserList(Search search) throws Exception {
		return sqlSession.selectList("UserMapper.getUserList", search);
	}
	
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("UserMapper.getTotalCount", search);
	}
	
	public void updateUser(User user) throws Exception {
		sqlSession.update("UserMapper.updateUser",user);
	}
	
	public void updateCart(Map<String,Object> map) throws Exception{		
		sqlSession.update("UserMapper.updateCart",map);
	}
	
	public String getCartData(String userId) throws Exception {	
		return sqlSession.selectOne("UserMapper.getCartData",userId);
	}
		
}