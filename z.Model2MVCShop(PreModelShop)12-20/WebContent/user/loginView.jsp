<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	<title>로그인</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">

		$(function(){
			
			$("#userId").focus();
			$("img[src='/images/btn_login.gif']").bind("click", function(){
				
				var id=$("input:text").val();
				var pw=$("input:password").val();
				
				if(id == null || id.length <1 ){
					alert('ID 를 입력하지 않으셨습니다.');
					$("input:text").focus();
					return;
				}

				if(pw == null || pw.length <1 ){
					alert('패스워드를 입력하지 않으셨습니다.');
					$("input:password").focus();
					return;
				}
				// 10번 리팩토링을 위한 주석
				//$("form").attr("method", "POST").attr("action", "/user/login").attr("target","_parent").submit();
				
				$.ajax(
					{
						url : "/user/json/login" ,
						method : "POST" ,
						dataType : "json" ,
						headers : {
							"Accept" : "application/json" ,
							"Content-Type" : "application/json"
						} ,
						data : JSON.stringify({
							userId : id,
							password : pw
						}) ,
						success : function(JSONData, status){
							
							if( JSONData != null ){
								//[ 방법1 ]
								//$(window.parent.document.location).atrr("href","/index.jsp");
								
								//[ 방법2 ]
								window.parent.document.location.reload();
								
								//[ 방법3 ]
								//$(window.parent.frames["topFrame"].document.location).attr("href","/layout/top.jsp");
								//$(window.parent.frames["leftFrame"].document.location).attr("href","/layout/left.jsp");
								//$(window.parent.frames["rightFrame"].document.location).attr("href","/user/getUser?userId="+JSONData.userId);
								
							}else{
								alert("아이디, 패스워드를 확인하시고 다시 로그인 해주세요.");
							}
						}//end of Call Back Function	
			});//end of ajax
		});//end of Login Click
			$("img[src='/images/btn_add.gif']").bind("click",function(){
				self.location = "./addUserView.jsp";
			});//end of Add User Click
		
	});//end of function
	</script>
	
</head>

<body bgcolor="#ffffff" text="#000000" >

<form>

<div align="center">

<TABLE WITH="100%" HEIGHT="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
<TR>
<TD ALIGN="CENTER" VALIGN="MIDDLE">

<table width="650" height="390" border="5" cellpadding="0" cellspacing="0" bordercolor="#D6CDB7">
  <tr> 
    <td width="10" height="5" align="left" valign="top" bordercolor="#D6CDB7">
    	<table width="650" height="390" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="305">
            <img src="/images/logo-spring.png" width="305" height="390">
          </td>
          <td width="345" align="left" valign="top" background="/images/login02.gif">
          	<table width="100%" height="220" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="30" height="100">&nbsp;</td>
                <td width="100" height="100">&nbsp;</td>
                <td height="100">&nbsp;</td>
                <td width="20" height="100">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="50">&nbsp;</td>
                <td width="100" height="50">
                	<img src="/images/text_login.gif" width="91" height="32">
                </td>
                <td height="50">&nbsp;</td>
                <td width="20" height="50">&nbsp;</td>
              </tr>
              <tr> 
                <td width="200" height="50" colspan="4">
                </td>
              </tr>              
              <tr> 
                <td width="30" height="30">&nbsp;</td>
                <td width="100" height="30">
                	<img src="/images/text_id.gif" width="100" height="30">
                </td>
                <td height="30">
                  <input 	type="text" name="userId" id="userId"  class="ct_input_g" 
                  				style="width:180px; height:19px"  maxLength='50'/>          
          		</td>
                <td width="20" height="30">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="30">&nbsp;</td>
                <td width="100" height="30">
                	<img src="/images/text_pas.gif" width="100" height="30">
                </td>
                <td height="30">                    
                    <input 	type="password" name="password" id="password" class="ct_input_g" 
                    				style="width:180px; height:19px"  maxLength="50" >
                </td>
                <td width="20" height="30">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="20">&nbsp;</td>
                <td width="100" height="20">&nbsp;</td>
                <td height="20" align="center">
      				<table width="136" height="20" border="0" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td width="56">
                            	 
                            		<img src="/images/btn_login.gif" width="56" height="20" border="0">
                            	</a>
                            </td>
                            <td width="10">&nbsp;</td>
                            <td width="70">
 
                            		<img src="/images/btn_add.gif" width="70" height="20" border="0">
                            	</a>
                            </td>
                          </tr>
                    </table>
                  </td>
                  <td width="20" height="20">&nbsp;</td>
                </tr>
            </table>
         </td>
       </tr>                            
      </table>
      </td>
  </tr>
</table>
</TD>
</TR>
</TABLE>
</div>

</form>

</body>
</html>

<script type="text/javascript">
	document.loginForm.userId.focus();
</script>
