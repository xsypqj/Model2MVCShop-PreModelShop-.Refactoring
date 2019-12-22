<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	<title>회원 목록조회</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncList(currentPage) {
	   	$("#currentPage").val(currentPage);
	   	$("form").attr("method", "POST").attr("action", "/user/listUser").submit();
	}
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
	   	$("form").attr("method", "POST").attr("action", "/user/listUser").submit();	
	}
	
	$(function(){
		
		$("td.ct_btn01:contains('검색')").bind("click", function(){
			fncGetUserList(1);
		});
		$("input[name='searchKeyword']").keydown(function(key){
			if(key.keyCode == 13){
				fncGetUserList(1);
			}
		});
		
		
		/* 10번 리팩토링을 위한 주석처리
		$(".ct_list_pop td:nth-child(3)").bind("click", function(){
			self.location = "/user/getUser?userId="+$(this).text().trim();
		});
		*/
		$(".ct_list_pop td:nth-child(3)").bind("click",function(){
			
			var userId = $(this).text().trim();
			//Low-Level Interface ( jQuery.ajax() )
			$.ajax(
					{	// XMLHttpRequest open
						url : "/user/json/getUser/"+userId ,
						method : "GET" ,
						dataType : "json" ,
						headers : { // header
							"Accept" : "application/json",			
							"Content-Type" : "application/json",			
						},
						success : function(JSONData, status){ // Call Back Function 지정

							var displayValue = "<h4>"
														+"아이디 : "+JSONData.userId+"<br/>"
														+"이  름 : "+JSONData.userName+"<br/>"
														+"이메일 : "+JSONData.email+"<br/>"
														+"ROLE : "+JSONData.role+"<br/>"
														+"등록일 : "+JSONData.regDate+"<br/>"
														+'<a href="/user/updateUserView?userId="'+JSONData.userId+'">개인정보수정</a>'
														+"</h4>";
							//Debug...
							//alert(displayValue);
							$("h4").remove();
							$("#"+userId+"").html(displayValue); // send( Data ) Call Back Function 구현
						}
					});
			
		});
		
		
		
		
		
		
		//$(".ct_list_pop td:nth-child(3)").css("color","red");	
		//$("h7").css("color","red");
		
		//$(".ct_list_pop:nth-child(4n+6)").css("background-color","whitesmoke");
		//console.log ( "console.log" + $(".ct_list_pop:nth-child(1)" ).html() );
		//console.log ( $(".ct_list_pop:nth-child(2)" ).html() );
		//console.log ( $(".ct_list_pop:nth-child(3)" ).html() );
		//console.log ( $(".ct_list_pop:nth-child(4)" ).html() ); //==> ok
		//console.log ( $(".ct_list_pop:nth-child(5)" ).html() ); 
		//console.log ( $(".ct_list_pop:nth-child(6)" ).html() ); //==> ok
		//console.log ( $(".ct_list_pop:nth-child(7)" ).html() ); 
	});
	
	
	</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">회원 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
			
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%-- ValidationCheck 견고한 코딩 --%> <%-- '',"" 차이점 / selected설정 blank 표시목적이므로 둘 다 사용해도 의미가 동일함 --%>
			<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>회원ID</option>
			<option value="1" ${! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>회원명</option>
			
			</select>
			<input 	type="text" name="searchKeyword"  
				value="${! empty search.searchKeyword ? search.searchKeyword : "" }" 
							class="ct_input_g" style="width:200px; height:19px" >
		</td>
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체  ${resultPage.totalCount} 건수, 현재 ${search.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td> 
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">이메일</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0" />
	<c:forEach var="user" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>		
			<td align="left">${user.userId}</td>
			<td></td>
			<td align="left">${user.userName}</td>
			<td></td>
			<td align="left">${user.email}
			</td>		
		</tr>
		<tr>
		<!-- <td colspan="11" bgcolor="D6D7D6" height="1"></td> -->
		<td id="${user.userId}" colspan="11" bgcolor="D6D7D6" height="1"></td>
		
		</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value=""/>

			<jsp:include page="../common/pageNavigator.jsp"/>
		
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>

</body>
</html>