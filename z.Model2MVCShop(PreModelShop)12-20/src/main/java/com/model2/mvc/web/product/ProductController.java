package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.SearchCounter;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.domain.Product;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	//Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//Constructor
	public ProductController() {
	}

	//Method
	@RequestMapping( value="addProduct", method=RequestMethod.POST )
	public ModelAndView addProduct(HttpServletRequest request)throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		List list = new ArrayList();
		
		//Business Logic	
		if(FileUpload.isMultipartContent(request)) {		
			String temDir = "C:\\Users\\User\\git\\PreModelShop\\z.Model2MVCShop(PreModelShop)\\WebContent\\images\\uploadFiles\\";		
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			// setSizeThreshold의 크기를 벗어나게 되면 지정한 위치에 임시로 저장한다.
			fileUpload.setSizeMax(1024 * 1024 * 10);
			// 최대 1메가까지 업로드 가능 (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); // 한번에 100k 까지는 메모리에 저장
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				Product product = new Product();			
				StringTokenizer token = null;
				List fileItemList = new ArrayList();
				String multiFileName = "";
				int size = 0;
					
				// parseRequest()는 FileItem을 포함하고 있는 List타입을 리턴한다.
				fileItemList = fileUpload.parseRequest(request);
				size = fileItemList.size(); // html page에서 받은 값들의 개수를 구한다.
				for(int i=0; i<size; i++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
					// isFormField()를 통해서 파일형식인지 파타미터인지 구분한다. 파라미터라면 true
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc_kr"),"-");
							String manuDate = token.nextToken()+token.nextToken()+token.nextToken();
							product.setManuDate(manuDate);
						}
						else if(fileItem.getFieldName().equals("prodName")) 
							product.setProdName(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("prodDetail"))
							product.setProdDetail(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("price"))
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
					}else { // 파일형식이면..
						// System.out.print("파일 : " + fileItem.getFieldName() + " = " +
						// fileItem.getName());
						// System.out.print("(" + fileItem.getSize() + " byte)\n");
						
						if(fileItem.getSize() > 0) { // 파일을 저장하는 if
							int idx = fileItem.getName().lastIndexOf("\\");
							// getName()은 경로를 다 가져오기 때문에 lastIndexOf로 잘라낸다
							if(idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							multiFileName += fileName;
							if(i<(size-1) && size>1) {
								multiFileName += "?";
							}else if(i==(size-1)) {
							product.setFileName(multiFileName);
							}
							list.add(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							}catch(IOException e) {
								System.out.println(e);
							}
						}else {
							product.setFileName("../../images/empty.GIF");
						}
					}// else
				}// for
				productService.addProduct(product);
				modelAndView.addObject("product",product);
			}else { // 업로드하는 파일이 setSizeMax보다 큰경우
				int overSize = (request.getContentLength() / 10000000);
				System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은" + overSize + "MB입니다');");
				System.out.println("histroy.back();</scrpt>");
			}
		}else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
		}
		modelAndView.addObject("imageFile",list);
		
		//View
		modelAndView.setViewName("forward:/product/addProduct.jsp");
		return modelAndView;
	}//end of addProduct
/* -------------------------------------------------------------------------------------------------------------- 
|	public ModelAndView addProduct(@ModelAttribute("product") Product product, HttpServletRequest request)throws Exception {
|		//Field																									|
|		ModelAndView modelAndView = new ModelAndView();															|
|		String[] getmanuDate = null;																			|
|		String manuDate = "";																					|
|																												|
|		//Business Logic																						|
|		if(product.getManuDate().length() > 8) {																|
|			getmanuDate = (product.getManuDate()).split("-");													|
|			for(int i=0; i<getmanuDate.length; i++) {															|
|				manuDate += getmanuDate[i]; 																	|
|				product.setManuDate(manuDate);																	|
|			}																									|
|		}																										|
|		productService.addProduct(product);																		|
|																												|
|		//Model																									|
|		modelAndView.addObject("product",product);																|
|																												|
|		//View																									|
|		modelAndView.setViewName( "forward:/product/addProduct.jsp" );											|
|		return modelAndView; 																					|
|	}//end of addProduct																						|
-------------------------------------------------------------------------------------------------------------- */		
	
	@RequestMapping( value="getProduct" )
	public ModelAndView getProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		//Field
		ModelAndView modelAndView = new ModelAndView();
		List<String> imageFile = new ArrayList<String>();
		StringTokenizer fileCSV;
		Product product = new Product();
		String value = "";
		Cookie cookie = null;
		
		//Business Logic
		product = productService.getProduct(Integer.parseInt(request.getParameter("prodNo")));

		if(product.getFileName() != null && !product.getFileName().equals("")) {
		fileCSV = new StringTokenizer(product.getFileName(),"?");
			int size = fileCSV.countTokens();
			for (int i=0; i<size; i++) {
				imageFile.add(i,fileCSV.nextToken());
			}
		}
		// Cookie + 최근 본 상품
		if(request.getCookies() != null) {
			for(int i=0; i<request.getCookies().length; i++) {
				if(request.getCookies()[i].getName().equals("history")) {
					value += request.getCookies()[i].getValue();
				}
			}
		}
		cookie = new Cookie("history",request.getParameter("prodNo")+","+value);
		response.addCookie(cookie);
		
		// Counter + 추가기능
		if(request.getParameter("menu") != null && request.getParameter("menu").equals("search")) {		
			SearchCounter.getSearchCounter().setCountMap(Integer.parseInt(request.getParameter("prodNo")),product.getProdName());		
			request.getServletContext().setAttribute("lankList", SearchCounter.getSearchCounter().getLankList());
			request.getServletContext().setAttribute("lankProdNameList", SearchCounter.getSearchCounter().getLankProdNameList());	
		}
		
		//ModelAndView
		String menu = "forward:/product/getProduct.jsp";
		if(request.getParameter("menu") != null && request.getParameter("menu").equals("manage")) {
			menu = "forward:/product/getProduct.jsp";
		}	
		if(request.getParameter("work")!=null && request.getParameter("work").equals("0")) {
			menu = "forward:/product/getProduct.jsp";
			modelAndView.addObject("work", request.getParameter("work"));
		}
		modelAndView.addObject("product", product);
		modelAndView.addObject("menu", request.getAttribute("menu"));
		modelAndView.addObject("imageFile",imageFile);
		modelAndView.setViewName(menu);
		return modelAndView; 
	}//end of getProduct
	
	@RequestMapping( value="listProduct" )
	public ModelAndView listProduct(@ModelAttribute("search") Search search, HttpServletRequest request) throws Exception {
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
		map = productService.getProductList(search);
		totalCount = (Integer)map.get("totalCount");
		resultPage = new Page(search.getCurrentPage(), totalCount, pageUnit, pageSize);
		
		//Model
		if(request.getParameter("dupl") != null && !request.getParameter("dupl").equals("")) {
			modelAndView.addObject("dupl", request.getParameter("dupl"));
		}
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("menu", search.getMenu());		
		
		//View
		modelAndView.setViewName( "forward:/product/listProduct.jsp" );			
		return modelAndView; 
	}//end of listProduct
	
	@RequestMapping( value="updateProduct", method=RequestMethod.POST )
	public ModelAndView updateProduct(@ModelAttribute("product") Product product, HttpServletRequest request) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		String[] getManuDate = null;
		String manuDate = "";
		String menu = "";
		
		//Business Logic
		if(product.getManuDate().length() > 8) {		
			getManuDate = (product.getManuDate()).split("-");	
			for(int i=0; i<getManuDate.length; i++) {
				 manuDate += getManuDate[i]; 
				 product.setManuDate(manuDate);
			}
			modelAndView.addObject("manuDate", manuDate);
		}
		productService.updateProduct(product);
		
		//Model		
		if(request.getParameter("manage")!=null) {
			menu = "manage";
			modelAndView.addObject("work","0");
		}
		
		//View
		modelAndView.setViewName( "redirect:/product/getProduct?prodNo="+request.getParameter("prodNo")+"&menu="+menu );
		return modelAndView;
	}//end of updateProduct
	
	@RequestMapping( value="updateProductView", method=RequestMethod.GET )
	public ModelAndView updateProductView(@RequestParam("prodNo") int prodNo) throws Exception {
		//Field
		ModelAndView modelAndView = new ModelAndView();
		Product product = new Product();
		
		//Business Logic
		product = productService.getProduct(prodNo);
		
		//Model
		modelAndView.addObject("product", product);

		//View
		modelAndView.setViewName( "forward:/product/updateProductView.jsp" );		
		return modelAndView;
	}//end of updateProductView
		
}//end of class












































