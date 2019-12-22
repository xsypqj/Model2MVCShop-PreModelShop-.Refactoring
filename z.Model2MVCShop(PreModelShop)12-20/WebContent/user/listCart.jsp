<%@ page language="java" contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	<title>구매 목록조회</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
	function fncList(currentPage){
		//document.getElementById("currentPage").value = currentPage;
		//document.detailForm.submit();
		$("#currentPage").val(currentPage)
		$("form").attr("method", "POST").attr("action", "/user/listUser").submit();
	}
	
	function fncGetSearchList(){
		//document.detailForm.submit();
		$("#currentPage").val(currentPage)
		$("form").attr("method", "POST").attr("action", "/user/listUser").submit();
	}
	
	$(function(){
		
		$(".ct_list_pop td:nth-child(3)").bind("click", function(){	
			self.location = "/product/getProduct?menu=search&prodNo="+$(this).parent().find("#prodNo").val();
		});
		
		$(".ct_list_pop td:nth-child(11)").bind("click", function(){
			self.location = "/user/deleteCart?prodNo="+$(this).parent().find("#prodNo").val();
		});
		
	});
	
	
	
	</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<!-- <form name="detailForm" action="/user/listPurchase" method="post"> -->
<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">카트 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${totalCount} 건수</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">상품상세정보</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">제조일자</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
		
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
	<c:set var="i" value="${i+1}" />
	
	<tr class="ct_list_pop"> 
		<td align="center">
			${i}
		</td>
		<td></td> 
		<td align="center">
			<input type="hidden" id="prodNo" name="prodNo" value="${product.prodNo}"/>
			<!-- <a href="/product/getProduct?menu=search&prodNo=${product.prodNo}"></a> -->
			${product.prodName}
		</td>
		<td></td>
		<td align="center">${product.price}</td>
		<td></td>
		<td align="center">${product.prodDetail}</td>
		<td></td>
		<td align="center">${product.manuDate}</td>
		<td></td>		   <!-- <a href="/user/deleteCart?prodNo=${product.prodNo}"></a> -->
		<td align="center">장바구니 삭제</td>
	</tr>
	
	
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>
	


</form>
<c:if test="${!empty noCart}">
<p>
<h5><center>! 카트에 담긴 상품이 없습니다 !</center></h5>
</p><hr/>
</c:if>
</div>

</body>
