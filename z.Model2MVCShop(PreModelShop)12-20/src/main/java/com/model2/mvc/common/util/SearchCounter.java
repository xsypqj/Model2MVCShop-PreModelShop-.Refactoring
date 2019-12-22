package com.model2.mvc.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

// 임시방편 중... (추후 인터페이스화, Service의 일부로 운영예정)
public class SearchCounter {
	
	/// Field
	private static SearchCounter searchCounter;
	private Map<String,Integer> countMap;
	private Integer key;
	private Integer one;
	private Integer two;
	private Integer three;
	private Integer four;
	private Integer five;//prodNo
	private String oneProdName;
	private String twoProdName;
	private String threeProdName;
	private String fourProdName;
	private String fiveProdName;
	
	private String test="테스트용 카운터 스트링";
	
	private ProductDAO productDAO;
	public void setProductService(ProductDAO productDAO) {
		System.out.println(this.getClass());
		this.productDAO = productDAO;
	}
	// SingleTon
	private SearchCounter(){
		this.countMap = new HashMap<String,Integer>();
	}
	public synchronized static SearchCounter getSearchCounter() {		
		if(searchCounter == null) {
			searchCounter = new SearchCounter();
		}		
		return searchCounter;
	}
	
	/// Method
	public String test() {
		return "테스트용 카운터메소드";
	}
	
	public void setCountMap(Integer key, String prodName){
		int count = 1;
		String prodNo = Integer.toString(key);
		
		if(countMap.get(prodNo) == null) {
			countMap.put(prodNo,new Integer("1"));
		}else if(countMap.get(prodNo) != null) {
			count += countMap.get(prodNo);
			countMap.put(prodNo, count);
		}
		
		
		this.countMap = countMap;
		this.key = key;
		String swapName = prodName;
		swapLankMap(swapName);
		
		//this.test();
		
	}
	
	public Map<String,Integer> getCountMap(){
		return countMap;
	}
	
	private void swapLankMap(String swapName) {
		
		//System.out.println(countMap.toString());
		
		Integer swapTemp = 0;
		boolean check=false;
		// check
		if(!key.equals(one) && !key.equals(two) && !key.equals(three)&& !key.equals(four) && !key.equals(five)) {
			check = true;
		}
		// SwapRanking
		if(countMap.get(Integer.toString(five)) < countMap.get(Integer.toString(key))){
			if(check) {
			fiveProdName = swapName;
			swapTemp = five;
			five = key;
			}
		}
		if(countMap.get(Integer.toString(four)) < countMap.get(Integer.toString(five))){		
			swapName = fourProdName;
			fourProdName = fiveProdName;
			fiveProdName = swapName;
			
			swapTemp = four;
			four = five;
			five = swapTemp;	
		}
		if(countMap.get(Integer.toString(three)) < countMap.get(Integer.toString(four))){
			swapName = threeProdName;
			threeProdName = fourProdName;
			fourProdName = swapName;
			
			swapTemp = three;
			three = four;
			four = swapTemp;
		}
		if(countMap.get(Integer.toString(two)) < countMap.get(Integer.toString(three))){
			swapName = twoProdName;
			twoProdName = threeProdName;
			threeProdName = swapName;
			
			swapTemp = two;
			two = three;
			three = swapTemp;
		}
		if(countMap.get(Integer.toString(one)) < countMap.get(Integer.toString(two))){
			swapName = oneProdName;
			oneProdName = twoProdName;
			twoProdName = swapName;
			
			swapTemp = one;
			one = two;
			two = swapTemp;
		}
		
	}

	public List<Integer> getLankList(){		
		List<Integer> lankList = new ArrayList<Integer>();
		lankList.add(one);
		lankList.add(two);
		lankList.add(three);
		lankList.add(four);
		lankList.add(five);

		return lankList;
	}
	
	public List<String> getLankProdNameList(){
		List<String> lankProdNameList = new ArrayList<String>();
		lankProdNameList.add(oneProdName);
		lankProdNameList.add(twoProdName);
		lankProdNameList.add(threeProdName);
		lankProdNameList.add(fourProdName);
		lankProdNameList.add(fiveProdName);
		
		return lankProdNameList;
	}
	
	public void fileWriter() throws Exception{
		File file = new File("C:\\Users\\User\\git\\PreModelShop\\z.Model2MVCShop(PreModelShop)\\src\\main\\java\\com\\model2\\mvc\\resources\\counter.txt");
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
		
		countMap.replace("one", one);
		countMap.replace("two", two);
		countMap.replace("three", three);
		countMap.replace("four", four);
		countMap.replace("five", five);
		
		String[] properties = countMap.toString().substring(1, countMap.toString().lastIndexOf("}")).split(", ");
		
		if(properties.length == countMap.size()) {
			for(int i=0; i<properties.length; i++) {
				bw.write(properties[i]);
				bw.newLine();
			}
				bw.flush();
				bw.close();
		}
	}
	 
	public void fileReader() throws Exception{
		File file = new File("C:\\Users\\User\\git\\PreModelShop\\z.Model2MVCShop(PreModelShop)\\src\\main\\java\\com\\model2\\mvc\\resources\\counter.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		for(; ; ) {
			String line = br.readLine();
			if(line==null) {
				break;
			}
			String[] properties =line.split("=");
			countMap.put(properties[0], Integer.parseInt(properties[1]));
		}

		one = countMap.get("one");
		two = countMap.get("two");
		three = countMap.get("three");
		four = countMap.get("four");
		five = countMap.get("five");
		
	
		oneProdName = productDAO.getProduct(one).getProdName();
		twoProdName = productDAO.getProduct(two).getProdName();
		threeProdName = productDAO.getProduct(three).getProdName();
		fourProdName = productDAO.getProduct(four).getProdName();
		fiveProdName = productDAO.getProduct(five).getProdName();

//		countMap.replace("one", 0);
//		countMap.replace("two", 0);
//		countMap.replace("three", 0);
//		countMap.replace("four", 0);
//		countMap.replace("five", 0);
		br.close();
		
		BufferedWriter fileReset = new BufferedWriter(new FileWriter(file));
		fileReset.flush();
		fileReset.close();
	}
	
	public void makeCountMap() {
		try {
			this.fileReader();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
}//end of class
	
	
	
	


