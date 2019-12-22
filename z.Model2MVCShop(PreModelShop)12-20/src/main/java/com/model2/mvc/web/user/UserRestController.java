package com.model2.mvc.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.domain.User;
import com.model2.mvc.web.product.ProductRestController;

@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
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
	public UserRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser(@PathVariable String userId) throws Exception {
		
		System.out.println("/user/json./getUser : GET");
		
		//Business Logic
		return userService.getUser(userId);
	}//end of getUser
		
	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(@RequestBody User user, HttpSession session) throws Exception {
		
		System.out.println("/user/json/login : POST");
		System.out.println(user.getPassword());
		System.out.println(user.getUserId());
		//Business Logic
		User dbUser = userService.loginUser(user);
		session.setAttribute("user", dbUser);
		
		return dbUser;
	}//end of login
	
	@RequestMapping( value="json/listUser", method=RequestMethod.POST )
	public Map<String,Object> listUser(@RequestBody Search search) throws Exception {
		
		System.out.println("user/json/listUser : POST");
		
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
		
		map.put("list", map.get("list"));
		map.put("search", search);
		map.put("resultPage", resultPage);
		
		return map;
	}//end of listUser
	
	@RequestMapping( value="json/listUser/{menu}", method=RequestMethod.GET )
	public Map<String,Object> listUser(@PathVariable String menu) throws Exception {
		
		System.out.println("user/json/listUser : GET");
		
		Map<String,Object> map = new HashMap<String,Object>();
		Search search = new Search();
		Page resultPage = new Page();
		int totalCount = 0;

		search.setCurrentPage(1);
		search.setMenu(menu);
		search.setPageSize(pageSize);		
		map = userService.getUserList(search);
		totalCount = ((Integer)map.get("totalCount")).intValue();
		resultPage	= new Page( search.getCurrentPage(), totalCount, pageUnit, pageSize);
		
		map.put("list", map.get("list"));
		map.put("search", search);
		map.put("resultPage", resultPage);
		
		return map;
	}//end of listUser
	
	@RequestMapping( value="json/updateUser", method=RequestMethod.POST )
	public User updateUser(@RequestBody User user, HttpSession session) throws Exception {
		
		//login
//		User sessionUser = new User();
//		sessionUser.setUserId("user03");
//		session.setAttribute("user", sessionUser);
		String sessionId = "";
		
		System.out.println("user/json/updateUser : GET");

		userService.updateUser(user);
		sessionId = ( (User)session.getAttribute("user") ) .getUserId();

		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		return userService.getUser(user.getUserId());
	}//end of updateUser
	
	@RequestMapping( value="json/addUser", method=RequestMethod.POST )
	public String addUser(@RequestBody User user) throws Exception {
		
		System.out.println("user/json/addUser : POST");
		
		String returnMessage = "가입을 축하드립니다.";;
		userService.addUser(user);
		System.out.println("??");
		return "가입을 축하드립니다.";
	}//end of addUser
	
	@RequestMapping( value="json/insertCart/{prodNo}", method=RequestMethod.GET )
	public Map insertCart(@PathVariable String prodNo, HttpSession session) throws Exception {
		String dupl = "success";
		
		//JSON session처리
		User sessionUser = new User();
		sessionUser.setUserId("user04");
		session.setAttribute("user", sessionUser);
		
		//Business Logic
		User user = new User();
		user = (User)session.getAttribute("user");
		if(!userService.checkDuplicationCartData(prodNo, user.getUserId())) {
			dupl = "dupl";
		}
		userService.updateCart(prodNo, user.getUserId());
		
		Map<String,Object> map = new HashMap<String,Object>();
		Page resultPage = new Page();
		int totalCount = 0;
			
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setMenu("search");
		search.setPageSize(pageSize);
		
		map = productService.getProductList(search);
		totalCount = (Integer)map.get("totalCount");
		resultPage = new Page(search.getCurrentPage(), totalCount, pageUnit, pageSize);
		
		map.put("dupl", dupl);
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);
		map.put("search", search);
		map.put("menu", search.getMenu());
		
		
		return map;
	}//end of insertCart
	
	@RequestMapping( value="json/checkDuplication", method=RequestMethod.POST )
	public Map<String,Object> checkDuplication(@RequestBody User user) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean result = false;

		//Business Logic
		result = userService.checkDuplication(user.getUserId());
		map.put("result", result);
		map.put("userId", user.getUserId());
		return map;
	}//end of checkDuplication
	
	@RequestMapping( value="json/deleteCart/{prodNo}", method=RequestMethod.GET )
	public void deleteCart(@PathVariable String prodNo, HttpSession session) throws Exception {
		
		//JSONTest를 위한 session처리
		User user = new User();
		user.setUserId("admin");
		session.setAttribute("user", user);
		
		//Business Logic
		User users = (User)session.getAttribute("user");
		userService.deleteCart(users.getUserId(), prodNo);
		
	}//end of deleteCart
	
	@RequestMapping( value="json/getCartList", method=RequestMethod.GET )
	public Map getCartList(HttpSession session) throws Exception {
		System.out.println("getCartList JSONTest : GET");
		//JSONTest를 위한 session처리
		User sessionUser = new User();
		sessionUser.setUserId("user01");
		session.setAttribute("user", sessionUser);
		
		//Field
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
		
		map.put("totalCount", map.get("totalCount"));
		map.put("list", map.get("list"));
		if(map.get("list")==null) {
			map.put("noCart",new Integer(1));
		}
		
		return map;
	}//end of getCartList
	
	@RequestMapping( value="json/logout", method=RequestMethod.GET )
	public void logout(HttpSession session) throws Exception {
		
		//JSONTest를 위한 session처리
		User user = new User();
		user.setUserId("user06");
		session.setAttribute("user", user);
		
		System.out.println(((User)(session.getAttribute("user"))).getUserId());
		
		session.invalidate();
		
		try {
		System.out.println(((User)(session.getAttribute("user"))).getUserId());
		}catch(IllegalStateException ex) {
			System.out.println("로그아웃 성공!");
		}
	}//end of logout
	
}//end of class





































