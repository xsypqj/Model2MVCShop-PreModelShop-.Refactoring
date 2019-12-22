<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<!-- 확장시 사용할 script
<script src="http://code.jquery.com/jqeury-2.1.4.min.js"></script>
<script type="text/javascript">

$(function(){
	//$("form").attr("action","/purchase/updatePurchaseView?tranNo=0").attr("method","post").submit();
});

</script>
 -->
</head>

<body>

<!-- <form name="updatePurchase" action="/purchase/updatePurchaseView?tranNo=0" method="post"> -->
<form name="updatePurchase">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${property.purchaseProd.prodNo}</td>
		
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${property.buyer.userId}</td>
		
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		
			${property.paymentOption}
		
		</td>
		
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${property.receiverName}</td>
		
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${property.receiverPhone}</td>
		
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${property.dlvyAddr}</td>
		
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${property.dlvyRequest}</td>
		
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${property.dlvyDate}</td>
		
	</tr>
</table>
</form>

</body>
</html>    