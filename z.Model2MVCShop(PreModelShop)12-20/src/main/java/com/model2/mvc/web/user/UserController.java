package com.model2.mvc.web.user;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.domain.User;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	//Field
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
	public UserController(){
		System.out.println(this.getClass());
	}
	
	//Method
	@RequestMapping( value="addUser", method=RequestMethod.POST )
	public ModelAndView adduser(@ModelAttribute("user") User user) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		
		//Business Logic
		userService.addUser(user);
		
		//Model		
		
		//View
		modelAndView.setViewName( "redirect:/user/loginView.jsp" );
		return modelAndView;
	}//end of addUser
	
	@RequestMapping( value="checkDuplication", method=RequestMethod.POST )
	public ModelAndView checkDuplication(@RequestParam("userId") String userId) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		boolean result = false;
		
		//Business Logic
		result = userService.checkDuplication(userId);
		
		//Model
		modelAndView.addObject("result",new Boolean(result));
		modelAndView.addObject("userId", userId);
		
		//View
		modelAndView.setViewName( "forward:/user/checkDuplication.jsp" );
		return modelAndView;
	}//end of checkDuplication
	
	@RequestMapping( value="deleteCart", method=RequestMethod.GET )
	public ModelAndView deleteCart(@RequestParam("prodNo") String prodNo, HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		
		//Business Logic
		user = (User)session.getAttribute("user");
		userService.deleteCart(user.getUserId(), prodNo);
		
		//Model
		
		//View
		modelAndView.setViewName( "redirect:/user/getCartList" );
		return modelAndView;
	}//end of deleteCart
	
	@RequestMapping( value="getCartList", method=RequestMethod.GET )
	public ModelAndView getCartList(HttpServletRequest request, HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		User user = new User();
		String cartData = "";
		String prodNo = "";
		
		//Business Logic
		user = (User)session.getAttribute("user");		
		cartData = (String)( userService.getCartData(prodNo, user.getUserId()) .get("cartData"));
		if(cartData != null && !cartData.equals("")) {
			map = productService.getCartList(cartData);
		}
		
		//Model
		modelAndView.addObject("totalCount",map.get("totalCount"));
		modelAndView.addObject("list",map.get("list"));
		if(map.get("list")==null) {
			modelAndView.addObject("noCart",new Integer(1));
		}
		
		//View
		modelAndView.setViewName( "forward:/user/listCart.jsp" );
		return modelAndView;
	}//end of getCartList
	
	@RequestMapping( value="getUser", method=RequestMethod.GET )
	public ModelAndView getUser(@RequestParam("userId") String userId) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		//Business Logic
		
		//Model
		modelAndView.addObject("user", userService.getUser(userId));
		
		//View
		modelAndView.setViewName( "forward:/user/readUser.jsp" );
		return modelAndView;
	}//end of getUser
	
	@RequestMapping( value="insertCart", method=RequestMethod.GET )
	public ModelAndView insertCart(@RequestParam("prodNo") String prodNo, HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		String dupl = "success";
	
		//Business Logic	 
		user = (User)session.getAttribute("user");
		if(!userService.checkDuplicationCartData(prodNo, user.getUserId())) {
			dupl = "dupl";
		}
		userService.updateCart(prodNo, user.getUserId());
		
		//Model
		
		//View
		modelAndView.setViewName( "redirect:/product/listProduct?menu=search&dupl="+dupl+"" );
		return modelAndView;
	}//end of insertCart
	
	@RequestMapping( value="listUser" )
	public ModelAndView listUser(@ModelAttribute("search") Search search) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		Page resultPage = new Page();
		int totalCount = 0;
		
		//Business Logic
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);		
		map = userService.getUserList(search);
		totalCount = ((Integer)map.get("totalCount")).intValue();
		resultPage	= new Page( search.getCurrentPage(), totalCount, pageUnit, pageSize);
		
		//Model
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("search", search);
		modelAndView.addObject("resultPage", resultPage);
		
		//View
		modelAndView.setViewName( "forward:/user/listUser.jsp" );	
		return modelAndView;
	}//end of listUser
	
	@RequestMapping( value="login", method=RequestMethod.POST )
	public ModelAndView login(@ModelAttribute("user") User user, HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();			
		
		//Business Logic
		User db = userService.loginUser(user);
		session.setAttribute("user", db);
		
		//Model
		
		//View
		modelAndView.setViewName( "redirect:/index.jsp" );
		return modelAndView;
	}//end of login
	
	@RequestMapping( value="logout", method=RequestMethod.GET )
	public ModelAndView logout(HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		
		//Business Logic
		session.invalidate();
		
		//Model
		
		//View
		modelAndView.setViewName( "redirect:/index.jsp" );
		return modelAndView;
	}//end of logout
	
	@RequestMapping( value="updateUser", method=RequestMethod.POST )
	public ModelAndView updateUser(@ModelAttribute("user") User user, HttpSession session) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		String sessionId = "";
		
		//Business Logic
		userService.updateUser(user);
		sessionId = ( (User)session.getAttribute("user") ) .getUserId();
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		//Model
		
		//view
		modelAndView.setViewName( "redirect:/user/getUser?userId="+user.getUserId());
		return modelAndView;
	}//end of updateUser
	
	@RequestMapping( value="updateUserView", method=RequestMethod.GET )
	public ModelAndView updateUserView(@RequestParam("userId") String userId) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		
		//Business Logic
		user = userService.getUser(userId);
		
		//Model
		modelAndView.addObject("user",user);
		
		//View
		modelAndView.setViewName( "forward:/user/updateUser.jsp" );
		return modelAndView;
	}//end of updateUserView
	
}//end of class

























































