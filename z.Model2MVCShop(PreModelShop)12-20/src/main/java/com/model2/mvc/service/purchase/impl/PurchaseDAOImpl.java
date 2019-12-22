package com.model2.mvc.service.purchase.impl;

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
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.transfer.domain.Transfer;
import com.model2.mvc.service.user.domain.User;

@Repository("purchaseDAOImpl")
public class PurchaseDAOImpl implements PurchaseDAO{

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public PurchaseDAOImpl() {
		System.out.println(this.getClass());
	}
	
	///Method
	public Purchase findPurchase(int tranNo) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getPurchase",tranNo);
	}
	
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);

		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	public Map<String, Object> getSaleList(Search search) throws Exception {
		return null;
	}

	public void insertPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase",purchase);
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase",purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode",purchase);
	}

	public int getTotalCount(String buyerId) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount",buyerId);
	}

	public void setFlag(int prodNo) throws Exception {
		sqlSession.update("PurchaseMapper.setFlag",prodNo);
	}
	
	public void updateTranCodeByProd(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCodeByProd",purchase);
	}
	
	public void addAccount(Transfer transfer) throws Exception {
		sqlSession.insert("PurchaseMapper.addAccount",transfer);
	}
	
	public Transfer searchAccount(Transfer transfer) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.searchAccount",transfer.getUserId());
	}
	
	public int depositAccount(Transfer transfer) throws Exception {
		return sqlSession.update("PurchaseMapper.depositAccount",transfer);
	}
	
	public int withdrawalAccount(Transfer transfer) throws Exception {
		return sqlSession.update("PurchaseMapper.withdrawalAccount",transfer);
	}
}
