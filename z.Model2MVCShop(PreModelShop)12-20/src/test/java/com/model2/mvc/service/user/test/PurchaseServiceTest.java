package com.model2.mvc.service.user.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.domain.User;

/*
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	//@Test
	public void getPurchaseTest() throws Exception{

		System.out.println(purchaseService.getPurchase(10021));
	}
	
	//@Test
	public void getTotalCountTest() throws Exception{
		
		System.out.println(purchaseService.getTotalCount("user17"));	
	}
	
	//@Test
	public void updatePurchaseTest() throws Exception{
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();
		purchase.setPaymentOption("2");
		purchase.setReceiverName("홍길동");
		purchase.setReceiverPhone("010-1234-4321");
		purchase.setDlvyAddr("테스트용주소");
		purchase.setDlvyRequest("테스트중입니다.");
		purchase.setTranCode("2");
		purchase.setDlvyDate("20191105");
		purchase.setTranNo(10021);
		purchaseService.updatePurcahse(purchase);
	}
	
	//@Test
	public void updateTranCodeTest() throws Exception{
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();

		//purchase.setTranNo();
		//purchase.setTranCode();
		purchaseService.updateTranCode(purchase);
	}
	
	//@Test
	public void updateTranCodeByProdTest() throws Exception{
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();
		
		//product.setProdNo();
		//purchase.setTranCode();
		purchase.setPurchaseProd(product);
		
		purchaseService.updateTranCode(purchase);
	}

	//@Test
	public void getPurchaseListTest() throws Exception{
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();
		
		Search search = new Search();
		String buyerId = "";
		search.setCurrentPage(1);
		search.setPageSize(3);
		
		Map<String,Object> map = purchaseService.getPurchaseList(search, buyerId);
		List<Purchase> list = (List<Purchase>) map.get("list");
		
		System.out.println(list.get(0));
		System.out.println("\n");
		System.out.println(list.get(1));
		System.out.println("\n");
		System.out.println(list.get(2));

	}		
	
	//@Test
	public void addPurchaseTest() throws Exception{
		//addPurchaseTest and setFlagTest
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();
		
		user.setUserId("user16");
		product.setProdNo(10003);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("SCOTT");
		purchase.setReceiverPhone("01000000000");
		purchase.setDlvyAddr("이순신이 사는곳");
		purchase.setDlvyRequest("이순신을 주세요");
		purchase.setDlvyDate("20191105");
		
		purchaseService.addPurchase(purchase);
	}
	
	
}
	
	
		
	
