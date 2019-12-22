package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.transfer.domain.Transfer;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.domain.User;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
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
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	//Method
	@RequestMapping( value="addPurchase", method=RequestMethod.POST )
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase, HttpServletRequest request) throws Exception{
		//Field
		ModelAndView modelAndView = new ModelAndView();
		Product product = new Product();
		User user = new User();
		
		//Business Logic		
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		user.setUserId(request.getParameter("buyerId"));	
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchaseService.addPurchase(purchase);
		
		//Model
		modelAndView.addObject("property",purchase);
		
		//View
		modelAndView.setViewName( "forward:/purchase/addPurchase.jsp" );
		return modelAndView;
	}//end of addPurchase
	
	@RequestMapping( value="addPurchaseView", method=RequestMethod.GET )
	public ModelAndView addPurchaseView(@RequestParam("prod_no") int prodNo, HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		String userId = "";
		User user = new User();
		Product product = new Product();
		
		//Business Logic
		userId = ((User)session.getAttribute("user")).getUserId();
		user = userService.getUser(userId);
		product = productService.getProduct(prodNo);
		String userPhone = CommonUtil.null2str(user.getPhone());
		String userAddr = CommonUtil.null2str(user.getAddr());
		String productName = CommonUtil.null2str(product.getProdName());
		
		//Model
		modelAndView.addObject("user", user);
		modelAndView.addObject("product", product);
		
		//View
		modelAndView.setViewName( "forward:/purchase/addPurchaseView.jsp" );
		return modelAndView;
	}//end of addPurchaseView
	
	@RequestMapping( value="getPurchase", method=RequestMethod.GET )
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		
		//Service
		
		//Business Logic
		modelAndView.addObject("purchase",purchaseService.getPurchase(tranNo));
		
		//View
		modelAndView.setViewName( "forward:/purchase/getPurchase.jsp" );
		return modelAndView;
	}//end of getPurchase
	
	@RequestMapping( value="listPurchase" )
	public ModelAndView listPurchase(@ModelAttribute("search") Search search,HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		Page page = new Page();
		int totalCount = 0;
		String buyerId = "";
		
		//Business Logic	
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		buyerId = ((User)session.getAttribute("user")).getUserId();
		search.setPageSize(pageSize);
		page.setPageSize(pageSize);
		page.setPageUnit(pageUnit);
		page.setCurrentPage(search.getCurrentPage());	
		map = purchaseService.getPurchaseList(search, buyerId);
		totalCount = (Integer)map.get("totalCount");
		page = new Page(search.getCurrentPage(),totalCount,pageUnit,pageSize);
		
		//Model
		modelAndView.addObject("resultPage", page);
		modelAndView.addObject("list", map.get("list"));
		
		//View
		modelAndView.setViewName( "forward:/purchase/listPurchase.jsp" );
		return modelAndView;
	}//end of listPurchase
	
	@RequestMapping("listSale")
	public void listSale() throws Exception{
	}//end of listSale
	
	@RequestMapping( value="updatePurchase", method=RequestMethod.POST )
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase, HttpServletRequest request) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		
		//Business Logic
		user.setUserId(request.getParameter("buyerId"));
		purchase.setBuyer(user);
		purchaseService.updatePurcahse(purchase);
		
		//Model
		modelAndView.addObject("purchase",purchase);
		
		//View
		modelAndView.setViewName( "forward:/purchase/updatePurchase.jsp" );
		return modelAndView;
	}//end of updatePurchase
	
	@RequestMapping( value="updatePurchaseView", method=RequestMethod.GET )
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		
		//Business Logic
		
		//Model
		modelAndView.addObject("purchase",purchaseService.getPurchase(tranNo));
		
		//View
		modelAndView.setViewName( "forward:/purchase/updatePurchaseView.jsp" );
		return modelAndView;
	}//end of updatePurchaseView
	
	@RequestMapping( value="updateTranCode", method=RequestMethod.GET )
	public ModelAndView updateTranCode(@ModelAttribute("purchase") Purchase purchase) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		
		//Business Logic
		purchaseService.updateTranCode(purchase);
		
		//Model
		
		//View
		modelAndView.setViewName( "redirect:/purchase/listPurchase.do" );
		return modelAndView;
	}//end of updateTranCode
	
	@RequestMapping( value="updateTranCodeByProd", method=RequestMethod.GET )
	public ModelAndView updateTranCodeByProd(@RequestParam("tranCode") String tranCode, @ModelAttribute("product") Product product) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		Purchase purchase = new Purchase();
		
		//Business Logic
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		
		//Model
		
		//View
		modelAndView.setViewName( "redirect:/product/listProduct?menu=manage" );
		return modelAndView;
	}//end of updateTranCodeByProd
	
}//end of class























































