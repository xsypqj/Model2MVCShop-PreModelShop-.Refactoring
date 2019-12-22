package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.transfer.domain.Transfer;

public interface PurchaseDAO {

	public Purchase findPurchase(int tranNo) throws Exception;
	
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;
	
	public Map<String,Object> getSaleList(Search search) throws Exception;
	
	public void insertPurchase(Purchase purchase) throws Exception;
	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public void updateTranCodeByProd(Purchase purchase) throws Exception;
	
	public int getTotalCount(String buyerId) throws Exception;
	
	public void setFlag(int prodNo) throws Exception;
	
	public void addAccount(Transfer transfer) throws Exception;
	
	public Transfer searchAccount(Transfer transfer) throws Exception;
	
	public int depositAccount(Transfer transfer) throws Exception;
	
	public int withdrawalAccount(Transfer transfer) throws Exception;

}
