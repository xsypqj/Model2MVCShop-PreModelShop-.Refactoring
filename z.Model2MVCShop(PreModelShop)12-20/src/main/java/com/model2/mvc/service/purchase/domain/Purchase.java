package com.model2.mvc.service.purchase.domain;

import java.sql.Date;

import com.model2.mvc.service.product.domain.Product;
import com.model2.mvc.service.user.domain.User;


public class Purchase {
	
	/*1*/private int tranNo;
	/*2*/private Product purchaseProd;
	/*3*/private User buyer;
	/*4*/private String paymentOption;
	/*5*/private String receiverName;
	/*6*/private String receiverPhone;
	/*7*/private String dlvyAddr;
	/*8*/private String dlvyRequest;
	/*9*/private String tranCode;
	/*10*/private Date orderDate;
	/*11*/private String dlvyDate;
	
	public Purchase(){
	}
	
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public Product getPurchaseProd() {
		return purchaseProd;
	}
	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		if(paymentOption != null) {
		this.paymentOption = paymentOption.trim();
		}
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getDlvyAddr() {
		return dlvyAddr;
	}
	public void setDlvyAddr(String dlvyAddr) {
		this.dlvyAddr = dlvyAddr;
	}
	public String getDlvyRequest() {
		return dlvyRequest;
	}
	public void setDlvyRequest(String dlvyRequest) {
		this.dlvyRequest = dlvyRequest;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		if(tranCode != null) {
		this.tranCode = tranCode.trim();
		}
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getDlvyDate() {
		return dlvyDate;
	}
	public void setDlvyDate(String dlvyDate) {
		
		if(dlvyDate != null) {
			String[] ary = dlvyDate.split("-");
			this.dlvyDate = ary[0];
		 }else {
			this.dlvyDate = dlvyDate;
		 }
	}
	
	@Override
	public String toString() {
		return "Purchase [buyer=" + buyer + ", divyAddr=" + dlvyAddr
				+ ", divyDate=" + dlvyDate + ", divyRequest=" + dlvyRequest
				+ ", orderDate=" + orderDate + ", paymentOption="
				+ paymentOption + ", purchaseProd=" + purchaseProd
				+ ", receiverName=" + receiverName + ", receiverPhone="
				+ receiverPhone + ", tranCode=" + tranCode + ", tranNo="
				+ tranNo + "]";
	}
}