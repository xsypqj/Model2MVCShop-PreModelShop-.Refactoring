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
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testGetProduct() throws Exception {
		System.out.println(productService.getProduct(10001));
	}
	
	
	//@Test
	public void testGetProductList() throws Exception {
		
		Search search = new Search();
		search.setCurrentPage(2);
		search.setPageSize(3);
		
		Map<String,Object> map = productService.getProductList(search);
		System.out.println(map.get("list"));
		System.out.println(search.getStartRowNum()+","+search.getEndRowNum());
	}
	
	//@Test
	public void testAddProduct() throws Exception {
		Product product = new Product();
		product.setProdName("테스트2호");
		product.setProdDetail("테스트2호");
		product.setManuDate("20191105");
		product.setPrice(40000);
		product.setFileName("테스트2호");
		
		productService.addProduct(product);
	}
	
	//@Test
	public void testUpdateProduct() throws Exception {
		Product product = new Product();
		
		product.setProdName("테스트용");
		product.setProdDetail("테스트용");
		product.setManuDate("20191105");
		product.setPrice(40000);
		product.setFileName("테스트용");
		
		product.setProdNo(10011);
		
		productService.updateProduct(product);
	}
	
	@Test
	public void testGetCartList() throws Exception{
		Map<String,Object> map = productService.getCartList("10001,10002");
		System.out.println(map.get("list")+"\n");
		System.out.println("totalCount :: "+map.get("totalCount"));
	}
	
	
}