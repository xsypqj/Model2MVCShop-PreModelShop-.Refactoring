package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.service.product.domain.Product;

public class ProductRestHttpClientApp {

	public static void main(String[] args) throws Exception{
		
		// 1.0
//		ProductRestHttpClientApp.addProductTest_JsonSimple_Codehaus_POST();
		
		// 1.1
//		ProductRestHttpClientApp.getProductTest_JsonSimple_Codehaus_POST();
		
		// 1.2
		ProductRestHttpClientApp.updateProductTest_JsonSimple_Codehaus_POST();
		
	}//end of main
	
	public static void addProductTest_JsonSimple_Codehaus_POST() throws Exception {
		// Client
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/user/json/addProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		ObjectMapper objectMapper00 = new ObjectMapper();
		
		// MultiPart 보내기... (현재 Null값)
		Product product = new Product();
		String jsonData = objectMapper00.writeValueAsString(product); 
		HttpEntity requestHttpEntity = new StringEntity(jsonData,"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		// JSONData
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		// Codehaus 사용
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		ObjectMapper objectMapper01 = new ObjectMapper();
		
	}//end of addProductTest_JsonSimple_Codehaus_POST()
	
	public static void getProductTest_JsonSimple_Codehaus_POST() throws Exception {
		// Client
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/product/json/getProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		ObjectMapper objectMapper00 = new ObjectMapper();
		
		// RequestJsonData
		jsonMap.put("prodNo", "10007");
		jsonMap.put("menu", "manage");
		jsonMap.put("work", "0");
		String jsonData = objectMapper00.writeValueAsString(jsonMap);
		
		HttpEntity requestHttpEntity = new StringEntity(jsonData,"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		// ResponseJsonData
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		// Codehaus
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		ObjectMapper objectMapper01 = new ObjectMapper();
		Product product = objectMapper01.readValue(jsonObj.get("product").toString(), Product.class);
		System.out.println("menu :: "+jsonObj.get("menu").toString());
		System.out.println("work :: "+jsonObj.get("work").toString());
		System.out.println("imageFile :: "+jsonObj.get("imageFile").toString());
		System.out.println("\n=========================================================\n");
		System.out.println(product.toString());
		
	}//end of getProductTest_JsonSimple_Codehaus_POST()
	
	public static void updateProductTest_JsonSimple_Codehaus_POST() throws Exception {
		//XMLHttpRequest
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/product/json/updateProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		ObjectMapper objectMapper00 = new ObjectMapper();
		Product product = new Product();
		
		//jsonData
		product.setProdNo(10021);
		product.setProdName("JSONTest");
		product.setProdDetail("JSONData 테스트를 위한 상세정보");
		product.setManuDate("2019-11-26");
		product.setPrice(50000);
		String jsonValue = objectMapper00.writeValueAsString(product);
		HttpEntity requestHttpEntity = new StringEntity(jsonValue,"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		//Call Back Function
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		ObjectMapper objectMapper01 = new ObjectMapper();
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonObj.get("product").toString());
		System.out.println("\n"+jsonObj.get("menu").toString());
		
		
		
	}//end of updateProductTest_JsonSimple_Codehaus_POST()
}
