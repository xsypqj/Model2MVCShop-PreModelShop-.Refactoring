package com.model2.mvc.service.transfer.domain;

public class Transfer {
	//Field
	private int balance;	
	private int money;		//price
	private String command; 
	private String userName;//예금주
	private String userId;	//세션처리
	private String approval;
	
	//Constructor
	public Transfer() {
	}
	
	//Method
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getUserName() {
		return userName.toUpperCase();
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	public String toString() {
		return userName+"("+userId+") 님의 잔액은 "+balance+"원 입니다."
				+"\n거래금액은 "+money+"원 입니다.";
	}

}
