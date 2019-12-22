package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.domain.User;
import com.sun.javafx.collections.MappingChange.Map;
public class UserRestHttpClientApp {
	
	// main Method
	public static void main(String[] args) throws Exception {
		
		// 1.1 Http Get 방식 Request : JsonSimple + codehaus 3rd party lib 사용
//		UserRestHttpClientApp.getUserTest_JsonSimple_Codehaus_GET();
		
		// 1.2 Http Post 방식 Request : JsonSimple + codehaus 3rd party lib 사용
//		UserRestHttpClientApp.loginTest_JsonSimple_Codehaus_POST();
	
		// 1.3 http Post 방식 Request : JsonSimple + codehaus 3rd party lib 사용
//		UserRestHttpClientApp.listUserTest__JsonSimple_Codehaus_POST();
		
		// 1.4
//		UserRestHttpClientApp.listUserTest__JsonSimple_Codehaus_GET();
		
		// 1.5
//		UserRestHttpClientApp.updateUserTest_JsonSimple_Codehaus_POST();
		
		// 1.6 
//		UserRestHttpClientApp.addUserTest_JsonSimple_Codehaus_POST();
		
		// 1.7
//		UserRestHttpClientApp.listProductTest_JsonSimple_Codehaus_POST();
		
		// 1.8
//		UserRestHttpClientApp.insertCartTest_JsonSimple_Codehaus_POST();
		
		// 1.9
//		UserRestHttpClientApp.checkDuplicationTest_JsonSimple_Codehaus_POST();
		
		// 2.0
//		UserRestHttpClientApp.deleteCartTest_JsonSimple_Codehaus_GET();
		
		// 2.1
//		UserRestHttpClientApp.getCartListTest_JsonSimple_Codehaus_GET();
		
		// 2.2
		UserRestHttpClientApp.logoutTest_JsonSimple_Codehaus_GET();
		
		
	}//end of main
	
//========================================Example Class========================================//
	//1.1 Http Protocol GET Request :  JsonSimple + codehaus 3rd party lib 사용
	public static void getUserTest_JsonSimple_Codehaus_GET() throws Exception {
		
		// HttpClient : Http Protocol 의 client 추상화 
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/user/json/getUser/admin";
		
		// HttpGet : Http Protocol 의 GET 방식 Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept","application/json");
		httpGet.setHeader("Content-Type","application/json");
		
		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		//==> Response 확인
		System.out.println(httpResponse);
		System.out.println();
		
		//==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();
		
		//==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
/*----------------------------------------JsonSimple----------------------------------------*/		
		System.out.println("[ Server 에서 받은 Data 확인 ]");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> 내용읽기(JSON Value 확인)
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonObj);
/*----------------------------------------JsonSimple----------------------------------------*/		
/*-----------------------------------------Codehaus-----------------------------------------*/	
		//==> API 확인 : Stream 객체를 직접 전달
//		JSONObject jsonObj = (JSONObject)JSONValue.parse(br);
//		System.out.println(jsonObj);
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		User user = objectMapper.readValue(jsonObj.toString(),User.class);
//		System.out.println(user);	
/*-----------------------------------------Codehaus-----------------------------------------*/		
		
	}// end of getUserTest_JsonSimple_Codehaus()
//========================================Example Class========================================//
	
	//1.2 Http Protocol POST Request :  JsonSimple + codehaus 3rd party lib 사용
	public static void loginTest_JsonSimple_Codehaus_POST() throws Exception {
		
		// HttpClient : Http Protocol 의 Client 추상화 Bean
		HttpClient httpClient = new DefaultHttpClient();
		
		// Request URL Make
		String url = "http://127.0.0.1:8080/user/json/login";
		
		// HttpPost : Http Protocol POST 방식 Request Header 구성
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		
		//==> POST 방식은 Body 에 Data 전송
		//==> QueryString (name = value)으로 전송하지 않고
		//==> JSONData 전송위해 Data Make
		
		//[방법 1 : String 사용]
		//String data = "{\"userId\":\"user01\",\"password\":\"1111\"}";
		
		//[방법 2 : JSONObject 사용]
		//JSONObject json = new JSONObject();
		//json.put("userId", "user01");
		//json.put("password", "1111");
		
		//[방법 3 : Codehaus 사용]
		User user = new User();
		user.setUserId("user01");
		user.setPassword("1111");
		ObjectMapper objectMapper01 = new ObjectMapper();
		//Domain Object ==> JSON Value 변환
		String jsonValue = objectMapper01.writeValueAsString(user);
		System.out.println(jsonValue);
		
		
		//==> Request Header/Body 중 Body 만들기(?)
		// HttpEntity : Http Protocol Body 추상화 Bean
		HttpEntity requestHttpEntity = new StringEntity(jsonValue.toString(),"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		// Request 실행 및 Response 받기(?)
		// HttpResponse : Http Protocol 응답 Message 추상화 Bean
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		//==> Response 출력 확인
		System.out.println(httpResponse);
		System.out.println();
		
		//==> Response Header/Body 중 Body 받기(?)
		// HttpEntity : Http Protocol Body 추상화 Bean
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		
		//==>Server 에서 받은 Data 읽기위해 HttpEntity로 부터 InputStream 생성
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		System.out.println("[ Server 에서 받은 Data 확인 ]");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> Server에서 받은 JSONData => JSONObject 객체생성
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		//==> Server에서 받은 JSONData => Domain Object 로 Binding
		ObjectMapper objectMapper = new ObjectMapper();
		User returnUser = objectMapper.readValue(jsonObj.toString(), User.class);
		System.out.println(returnUser);
	}//end of loginTest_JsonSimple_Codehaus()
	
	//1.3 Http Protocol POST Request :  JsonSimple + codehaus 3rd party lib 사용
	public static void listUserTest__JsonSimple_Codehaus_POST() throws Exception {
		//Field
		HttpClient httpClient = new DefaultHttpClient();
		String url = "";
		Search search = new Search();
		String jsonValue = "";
		ObjectMapper requestCodehaus = new ObjectMapper();
		
		//Make URL
		url = "http://127.0.0.1:8080/user/json/listUser";
		
		//HttpPost
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		
		//Make JSON(Codehaus)
		//[방법1:{\"key\":\"value\",\"key01\":\"value01\"}
		//[방법2:JSONObject에 key,value put,  put,   put...]
		//[방법3:Codehaus]	
		search.setCurrentPage(1);
		search.setMenu("search");
		jsonValue = requestCodehaus.writeValueAsString(search);
		
		//Make HttpEntity & setEntity(to HttpPost)
		HttpEntity requestHttpEntity = new StringEntity(jsonValue.toString(),"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		

		//Response JSON Data Get
		//Data request&response
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		//JSON변환
		JSONObject jsonObject = (JSONObject) JSONValue.parse(serverData);
		
		//Codehaus로 binding
		ObjectMapper objectMapper = new ObjectMapper();
		List<User> list = new ArrayList<User>();
		list = objectMapper.readValue(jsonObject.get("list").toString(),List.class);
		
	}//end of listUserTest__JsonSimple_Codehaus()
	
	//1.4 GET
	public static void listUserTest__JsonSimple_Codehaus_GET() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "";
		
		
		url = "http://127.0.0.1:8080/user/json/listUser/search";
		HttpGet httpGET = new HttpGet(url);
		httpGET.setHeader("Accept","application/json");
		httpGET.setHeader("Content-Type","application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGET);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		//1.Simple 뽑기
		JSONObject jsonObject = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonObject.get("resultPage"));
		
		//2.Codehaus
		ObjectMapper codeHaus = new ObjectMapper();
		List<User> list = new ArrayList<User>();
		list = codeHaus.readValue(jsonObject.get("list").toString(),List.class);
		System.out.println(list.get(0));
	}//end of listUserTest__JsonSimple_Codehaus_GET()
	
	//1.5 Http Protocol POST Request : JsonSimple + codehaus 3rd party lib 사용
	public static void updateUserTest_JsonSimple_Codehaus_POST() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "";
		ObjectMapper requestCodehaus = new ObjectMapper();
		User user = new User();
		
		url="http://127.0.0.1:8080/user/json/updateUser";
		user.setUserId("user03");
		user.setUserName("testtesttest");
		user.setAddr("JSON테스트");
		user.setPhone("01012341234");
		user.setEmail("이메일JSON");
		String jsonValue = requestCodehaus.writeValueAsString(user);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		
		HttpEntity requestHttpEntity = new StringEntity(jsonValue.toString(),"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		String serverData = br.readLine();
		
		ObjectMapper objMapper = new ObjectMapper();
		JSONObject json = (JSONObject)JSONValue.parse(serverData);
		System.out.println(json);
		User myUser = objMapper.readValue(json.toString() ,User.class);
		System.out.println(myUser.getUserId());
	}//end of updateUserTest_JsonSimple_Codehaus()
	
	// 1.6 Http Protocol POST Request : JsonSimple + Codehaus 3rd party lib 사용
	public static void addUserTest_JsonSimple_Codehaus_POST() throws Exception {
		
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/user/json/addUser";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		User user = new User();
		ObjectMapper objectMapper00 = new ObjectMapper();
		
		//
		user.setUserId("hong");
		user.setPassword("1234");
		user.setUserName("홍길동");
		user.setSsn("1234567890123");
		user.setPhone("01012341234");
		user.setEmail("dkdkdk");
		//user.setAddr("주소");
		String jsonValue = objectMapper00.writeValueAsString(user);
		HttpEntity requestHttpEntity = new StringEntity(jsonValue,"UTF-8");
		
		//
		httpPost.setEntity(requestHttpEntity);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		//
		HttpEntity responseEntity = httpResponse.getEntity();
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		System.out.println(serverData);
		System.out.println("?");
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		
		//
		
				
	}//end of addUserTest_JsonSimple_Codehaus_POST()
	
	
	public static void listProductTest_JsonSimple_Codehaus_POST() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String url="http://127.0.0.1:8080/product/json/listProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type","application/json");
		ObjectMapper objectMapper00 = new ObjectMapper();
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setMenu("search");
		String jsonData = objectMapper00.writeValueAsString(search);
		HttpEntity requestHttpEntity = new StringEntity(jsonData.toString(),"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity responseEntity = httpResponse.getEntity();
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		ObjectMapper objectMapper01 = new ObjectMapper();
		List list = objectMapper01.readValue(jsonObj.get("list").toString(), List.class);
		System.out.println(list.toString());
		
		
	}//end of listProductTest_JsonSimple_Codehaus_POST()
	
	public static void insertCartTest_JsonSimple_Codehaus_POST() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/user/json/insertCart/10001";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept","application/json");
		httpGet.setHeader("Content-Type","application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		ObjectMapper objectMapper01 = new ObjectMapper();
		List list = objectMapper01.readValue(jsonObj.get("list").toString(), List.class);
		System.out.println(list.toString());
		System.out.println(jsonObj.get("dupl").toString());
	}//end of insertCartTest_JsonSimple_Codehaus_POST()
	
	public static void checkDuplicationTest_JsonSimple_Codehaus_POST() throws Exception {
		// request
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/user/json/checkDuplication";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept","aplication/json");
		httpPost.setHeader("Content-Type","aplication/json");
		ObjectMapper objectMapper00 = new ObjectMapper();
		
		String jsonData = objectMapper00.writeValueAsString("user04");
		HttpEntity requestHttpEntity = new StringEntity(jsonData.toString(),"UTF-8");
		httpPost.setEntity(requestHttpEntity);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		// response
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		ObjectMapper objectMapper01 = new ObjectMapper();
		
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonObj.get("result").toString());
		User user = objectMapper01.readValue(jsonObj.get("userId").toString(), User.class);
		boolean result = objectMapper01.readValue(jsonObj.get("result").toString(), boolean.class);	
		System.out.println("userId : " + user.getUserId());
		System.out.println("result : " + result);
		
		
		
	}//end of checkDuplicationTest_JsonSimple_Codehaus_POST()
	
	public static void deleteCartTest_JsonSimple_Codehaus_GET() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/user/json/deleteCart/10027";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept","application/json");
		httpGet.setHeader("Content-Type","application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
				
	}//end of deleteCartTest_JsonSimple_Codehaus_GET()
	
	public static void getCartListTest_JsonSimple_Codehaus_GET() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/user/json/getCartList";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept","application/json");
		httpGet.setHeader("Content-Type","application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String serverData = br.readLine();
		
		JSONObject jsonObj = (JSONObject)JSONValue.parse(serverData);
		ObjectMapper objectMapper01 = new ObjectMapper();
		List list = objectMapper01.readValue(jsonObj.get("list").toString(), List.class);
		System.out.println(jsonObj.get("totalCount").toString());
		System.out.println(list.get(1).toString());
	}//end of getCartListTest_JsonSimple_Codehaus_GET()
	
	public static void logoutTest_JsonSimple_Codehaus_GET() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/user/json/logout";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept","application/json");
		httpGet.setHeader("Content-Type","application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
	}//end of logoutTest_JsonSimple_Codehaus_GET()
}//end of class














































