<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
	<style>
    .toggler { width: 500px; height: 200px; position: relative; }
    #button { padding: .5em 1em; text-decoration: none; }
    #effect { width: 240px; height: 170px; padding: 0.4em; position: relative; background: #fff; }
    #effect h3 { margin: 0; padding: 0.4em; text-align: center; }
  </style>
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
	 $( function() {
		    var state = true;
		    $( "#button" ).on( "click", function() {
		      if ( state ) {
		        $( "p" ).animate({
		          backgroundColor: "#aa0000",
		          color: "#fff",
		          width: 170
		        }, 1000 );
		      } else {
		        $( "p" ).animate({
		          backgroundColor: "#fff",
		          color: "#000",
		          width: 240
		        }, 1000 );
		      }
		      state = !state;
		    });
		  } );
	</script>

</head>
	<body>
		<c:choose>
			<c:when test="${exception.message == \"로그인에 실패했습니다.\"}">
				<p>로그인에 실패했습니다.</p>
				<button id="button" class="ui-state-default ui-corner-all">클릭!</button>
			</c:when>
			<c:otherwise>
				<h3> default Exception page</h3>
				<%	Exception exception = (Exception)request.getAttribute("exception");	%>
				<%="Java Code을 이용한 예외 Message 보기 ::" +  exception.getMessage() %><br/>
				EL을 이용한 예외 Message 보기 :: ${ exception.message }<br/> 
				
				<hr/>
				<br/>
				<%=  request.getRequestURI() %>
				<br/>
				<%=  request.getRequestURL() %>
			</c:otherwise>
		</c:choose>
	</body>
</html>