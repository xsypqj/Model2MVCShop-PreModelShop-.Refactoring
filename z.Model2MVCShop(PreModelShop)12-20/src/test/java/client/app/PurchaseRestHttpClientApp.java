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
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.user.domain.User;

public class PurchaseRestHttpClientApp {

	public static void main(String[] args) throws Exception{
		// 1.0
		PurchaseRestHttpClientApp.addPurchaseTest_JsonSimple_Codehaus_POST();

	}//end of main

	public static void addPurchaseTest_JsonSimple_Codehaus_POST() throws Exception {
		//XmlHttpRequest
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/purchase/json/addPurchase";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		ObjectMapper objectMapper00 = new ObjectMapper();
		User user = new User();
		Product product = new Product();
		Purchase purchase = new Purchase();
		
		//jsonData
		user.setUserId("user07");
		product.setProdNo(10007);
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setDlvyRequest("力捞郊 府涅胶飘");
		purchase.setDlvyAddr("力捞郊 林家");
		purchase.setReceiverName("力捞郊");
		purchase.setPaymentOption("1");
		purchase.setReceiverPhone("01012341234");
		purchase.setDlvyDate("20191126");
		String jsonData = objectMapper00.writeValueAsString(purchase);
		HttpEntity requestHttpEntity = new StringEntity(jsonData.toString(),"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		//Call Back Function
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		ObjectMapper objectMapper01 = new ObjectMapper();
		JSONObject JSONObj = (JSONObject)JSONValue.parse(serverData);
		Purchase jsonPurchase = objectMapper01.readValue(JSONObj.toString(), Purchase.class);
		System.out.println(jsonPurchase.toString());
		

		
		
	}//end of addPurchaseTest_JsonSimple_Codehaus_POST()
}
