package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.transfer.domain.Transfer;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.domain.User;

@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {

	//Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//Constructor
	public PurchaseRestController() {
		System.out.println(this.getClass());
	}
	
	//Method
	@RequestMapping( value="json/addPurchase", method=RequestMethod.POST )
	public Purchase addPurchase(@RequestBody Purchase purchase) throws Exception {
		//Field
		System.out.println("json/addPurchase : POST");

		purchaseService.addPurchase(purchase);
		return purchase;
	}//end of addPurchase
	
	@RequestMapping( value="json/accountTransfer", method=RequestMethod.POST )
	public Transfer accountTransfer(@RequestBody Transfer transfer, HttpSession session) throws Exception {
		//Field
		System.out.println("json/accountTransfer : POST");
		
		//Business Logic
		transfer.setUserId( ((User)session.getAttribute("user")).getUserId() );
		if(transfer.getCommand().equals("1")) {
			transfer = purchaseService.deposit(transfer);
		}else if(transfer.getCommand().equals("2")) {
			transfer = purchaseService.withdrawal(transfer);
		}
		return transfer;
	}//end of accountTransfer
	
	@RequestMapping( value="json/getPoint/{userId}", method=RequestMethod.GET )
	public Transfer getPoint(@PathVariable String userId) throws Exception {
		//Field
		Transfer transfer = new Transfer();
		System.out.println(userId);
		//Business Logic
		transfer.setUserId(userId);
		transfer = purchaseService.getPoint(transfer);
		System.out.println(transfer.toString());
		return transfer;
	}//end of getPoint
}//end of class
