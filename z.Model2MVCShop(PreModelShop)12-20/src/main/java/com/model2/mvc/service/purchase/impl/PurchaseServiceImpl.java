package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.domain.Product;
import com.model2.mvc.service.product.impl.ProductDAOImpl;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.transfer.domain.Transfer;
import com.model2.mvc.service.user.UserDAO;
import com.model2.mvc.service.user.domain.User;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	///Field
	@Autowired
	@Qualifier("purchaseDAOImpl")
	private PurchaseDAO purchaseDAO;
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}
	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	///Constructor
	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}
	
	///Method
	public Purchase getPurchase(int tranNo) throws Exception {	
		return purchaseDAO.findPurchase(tranNo);
	}

	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.insertPurchase(purchase);
		purchaseDAO.setFlag(purchase.getPurchaseProd().getProdNo());
	}
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		int totalCount = purchaseDAO.getTotalCount(buyerId);
		List<Purchase> list =  purchaseDAO.getPurchaseList(search, buyerId);
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", totalCount);
		map.put("list", list);
		
		return map;
	}

	public Map<String, Object> getSaleList(Search search) throws Exception {
		return null;
	}

	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
			if(purchase.getTranNo() >= 1) {
				purchaseDAO.updateTranCode(purchase);
			}else if(purchase.getPurchaseProd().getProdNo() > 0) {
				purchaseDAO.updateTranCodeByProd(purchase);
			}
	}

	public int getTotalCount(String buyerId) throws Exception {
		return purchaseDAO.getTotalCount(buyerId);
	}
	
	public Transfer deposit(Transfer transfer) throws Exception{		
		// 계좌 조회
		Transfer dbTransfer = new Transfer();
		User user = new User();
		dbTransfer = purchaseDAO.searchAccount(transfer);
		user = userDAO.findUser(transfer.getUserId());
		transfer.setUserName(user.getUserName());
		if(dbTransfer != null && dbTransfer.getUserId().equals(transfer.getUserId())) {
		
			if(dbTransfer.getUserName().equals(transfer.getUserName())) {
				transfer.setBalance(purchaseDAO.searchAccount(transfer).getBalance()+transfer.getMoney());
				purchaseDAO.depositAccount(transfer);	
				transfer.setApproval("200");
			}else {
				transfer.setApproval("400");
			}
			
		}else {
			// 처음이라면 : 계좌생성 & 잔액부족 코드
			purchaseDAO.addAccount(transfer);
			transfer.setApproval("500");
		}
		return transfer;
	}
	
	public Transfer withdrawal(Transfer transfer) throws Exception{
		// 계좌 조회
		Transfer dbTransfer = new Transfer();
		dbTransfer = purchaseDAO.searchAccount(transfer);  
		if(dbTransfer != null && dbTransfer.getUserId().equals(transfer.getUserId())) {
			// 계좌가 있다면 거래금액 계좌잔액 비교
			if(dbTransfer.getUserName().equals(transfer.getUserName())) {
				if(dbTransfer.getBalance() >= transfer.getMoney()) {
					dbTransfer.setBalance(dbTransfer.getBalance() - transfer.getMoney());
					int returnValue = purchaseDAO.withdrawalAccount(dbTransfer);				
					// 인출진행 후 검사
					if( returnValue == -1 ) {
						transfer.setApproval("500");
					}	
					transfer.setBalance(dbTransfer.getBalance());
					transfer.setApproval("200");
				}else {
					// 잔액부족 코드
					transfer.setApproval("500");
				}
			}else {
				transfer.setApproval("400");
			}
		}else {
			// 처음이라면 : 계좌생성 & 잔액부족 코드		
			purchaseDAO.addAccount(transfer);
			transfer.setApproval("500");
		}
		return transfer;
	}
	
	public Transfer getPoint(Transfer transfer) throws Exception {
		return purchaseDAO.searchAccount(transfer);
	}
}//end of class
