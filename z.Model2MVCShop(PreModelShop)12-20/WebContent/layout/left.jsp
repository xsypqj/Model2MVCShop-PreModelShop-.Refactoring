<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	
	<title>PreMade Model Shop</title>

	<link href="/css/left.css" rel="stylesheet" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		
		$( function(){
			
			$.ajax(
					{
						url : "/purchase/json/getPoint/"+$("#userId").val() ,
						method : "GET" ,
						dataType : "json" ,
						success : function(JSONData, status){
							$(".Depth03:contains('포인트')").html("<h4>ⓟ "+JSONData.balance+"원</h4>");
						}//end of Call Back Function
					}//end of setting
			);//end of ajax
			
			$(".Depth03:contains('충전하기')").bind("click",function(){
				
				popWin 
				= window.open("/user/accountTransfer.jsp",
											"popWin", 
											"left=300,top=200,width=300,height=200,marginwidth=0,marginheight=0,"+
											"scrollbars=no,scrolling=no,menubar=no,resizable=no");
				
				$.ajax(
						{
							url : "/purchase/json/accountTransfer" ,
							method : "POST" ,
							headers : {
								"Accept" : "application/json" ,
								"Content-Type" : "application/json"
							} ,
							dataType : "json" ,
							data : {
								userId : $("#userId").val() ,
								command : '1' ,
							} ,
							success : function(JSONData, status){
								
							}//end of Call Back Function
						}		
				);//end of ajax
			});
			
			// 개인정보조회		
			$( ".Depth03:contains('개인정보조회')" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/user/getUser?userId=${user.userId}");
			});
			// 회원정보조회
			$( ".Depth03:contains('회원정보조회')" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/user/listUser");
			});
			// 판매상품등록
			$( ".Depth03:contains('판매상품등록')" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","../product/addProductView.jsp");
			});
			// 판매상품관리
			$( ".Depth03:contains('판매상품관리')" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/product/listProduct?menu=manage");
			});
			// 상 품 검 색
			$( ".Depth03:contains('상 품 검 색')" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/product/listProduct?menu=search");
			}); 
			// 구매이력조회
			$( ".Depth03:contains('구매이력조회')" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/purchase/listPurchase");
			});
			// 장바구니조회
			$( ".Depth03:contains('장바구니조회')" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/user/getCartList");
			});
			// 최근 본 상품
			$( ".Depth03:contains('최근 본 상품')" ).bind( 'click', function(){
				popWin = window.open("/history.jsp","popWin","left=300, top=200, width=300, height=200, marginwidth=0, marginheight=0, scrollbars=no, scrolling=no, menubar=no, resizable=no");
			});
			// name00
			$( "#name00" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/product/getProduct?prodNo=${lankList[0]}&menu=search");
			});
			// name01
			$( "#name01" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/product/getProduct?prodNo=${lankList[1]}&menu=search");
			});
			// name02
			$( "#name02" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/product/getProduct?prodNo=${lankList[2]}&menu=search");
			});
			// name03
			$( "#name03" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/product/getProduct?prodNo=${lankList[3]}&menu=search");
			});
			// name04
			$( "#name04" ).bind( 'click', function(){
				$(window.parent.frames["rightFrame"].document.location).attr("href","/product/getProduct?prodNo=${lankList[4]}&menu=search");
			});
			$("h3").tooltip();
		});
		
	</script>
	
</head>

<body background="/images/left/imgLeftBg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >
<input type="hidden" id="userId" value="${user.userId}">
<input type="hidden" id="userName" value="${user.userName}">
<table width="159" border="0" cellspacing="0" cellpadding="0">

<!--menu 01 line-->
<tr>
<td valign="top"> 
	<table  border="0" cellspacing="0" cellpadding="0" width="159" >	
		<tr>
		<c:if test="${!empty user}">
		<tr>
		<td class="Depth03">
			개인정보조회
		</td>
		</tr>
		</c:if>	
		<c:if test="${!empty user.role && user.role==\"admin\"}">	
		<tr>
		<td class="Depth03" >
			회원정보조회
		</td>
		</tr>
		</c:if>
		<tr>
			<td class="DepthEnd">&nbsp;</td>
		</tr>
	</table>
</td>
</tr>
<c:if test="${user.role==\"admin\"}">
<!--menu 02 line-->
<tr>
<td valign="top"> 
	<table  border="0" cellspacing="0" cellpadding="0" width="159">
		<tr>
			<td class="Depth03">
				판매상품등록
			</td>
		</tr>
			<td class="Depth03">
				판매상품관리
			</td>
		</tr>
		<tr>
			<td class="DepthEnd">&nbsp;</td>
		</tr>
	</table>
</td>
</tr>
</c:if>
<!--menu 03 line-->
<tr>
<td valign="top">
	<table  border="0" cellspacing="0" cellpadding="0" width="159">
		<tr>
			<td class="Depth03">
				상 품 검 색
			</td>
		</tr>
		<c:if test="${!empty user}">
			<c:if test="${user.role==\"user\"}">
		<tr>
			<td class="Depth03">
				구매이력조회
			</td>
		</tr>
		<tr>
			<td class="Depth03">
				장바구니조회
			</td>
		</tr>
			</c:if>
		</c:if>
		<tr>
		<td class="DepthEnd">&nbsp;</td>
		</tr>
		<tr>
			<td class="Depth03">
				최근 본 상품
			</td>
		</tr>
		<tr>
			<td class="Depth03">
				포인트
			</td>
			<c:if test="${!empty user.userId}">
			<td class="Depth03">
				충전하기
			</td>
			</c:if>
		</tr>
					
		
	</table>
</td>
</tr>

</table>
<br/><br/><br/>
<hr/>
<table> 
		<tr><td class="Depth03"><center><h3 title="상품검색 클릭수">인기상품순위</h3></center></td>
	<c:if test="${lankList != null && lankProdNameList != null}">
		<c:set var="i" value="0"/>
		<c:forEach begin="0" step="1" end="4">
		<tr><td class="Depth03" id="name0${i}">${i+1}  ${lankProdNameList[i]}</td></tr>
		<c:set var="i" value="${i+1}"/>
		</c:forEach>
	</c:if>
</table>
<hr/>
</body>
</html>