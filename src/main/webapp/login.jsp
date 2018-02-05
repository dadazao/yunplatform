<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="org.springframework.security.authentication.AuthenticationServiceException"%>
<%@page import="org.springframework.security.authentication.AccountExpiredException"%>
<%@page import="org.springframework.security.authentication.DisabledException"%>
<%@page import="org.springframework.security.authentication.LockedException"%>
<%@page import="org.springframework.security.authentication.BadCredentialsException"%>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>系统登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type='text/css' href='<%=basePath %>/themes/css/login.css' rel='stylesheet' />
	<script type="text/javascript" src="<%=basePath %>/js/jquip/jquip.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/common/cookie.js"></script>
	<script type="text/javascript">
		$(function() {
			loadTheme();
			
			//$('#username').focus();
			
			$('#username').keydown(function (e){
				enterLogin(e);
			});
			
			$('#password').keydown(function (e){
				enterLogin(e);
			});
			
			$('#dynamicPassword').keydown(function (e){
				enterLogin(e);
			});
			
			$("#username").click(function(e){
				$("#user_state").html("");
				$('#user_state').removeClass('onError');
			});
			$("#password").click(function(e){
				$("#user_state").html("");
				$('#user_state').removeClass('onError');
			});
			$("#dynamicPassword").focus(function(e){
				$("#user_state").html("");
				$('#user_state').removeClass('onError');
			});
			$("#kaptchaImg").click(function(e){
				$("#kaptchaImg").attr("src","Kaptcha.jpg?"  + new Date().getTime());
			});
			
			/**
			var userNameValue = getCookieValue("username");  
			var userNameValueHistory = getCookieValue("usernameHistory");  
			if(userNameValue!=""){
				$("#username").val(userNameValue);	
			}else{
				$("#username").val(userNameValueHistory);	
			}
			var passwordValue = getCookieValue("password");  
	   		if(passwordValue!=""){
	   			$("#password").val(passwordValue);	
	   			$("#rememeber").attr("checked","checked");
	   		}  
	   		**/
		});
		
		function enterLogin(e) {
			var curKey = e.which;
			if(curKey == 13){
				loginSystem();
				return false;
			}
		}
		
		function loadTheme() {
			$.ajax({
		  		type:'POST',
		  		url:'<%=basePath %>/theme/load.action',
		  		dataType:'json',
		  		async:false,
		  		success:function(data){
					//if(data.themeCode != 'blue') {
					//	$("head").find("link[href$='login.css']").attr("href", "themes/"+data.themeCode+"/login.css");	
					//}
					$("#logo").html("<img src='<%=basePath %>"+data.defaultLogo.logoPath+"' width='89' height='89'/>");
					$("#systitle").html(data.enterpriseInfo.systemCnName);
					$("#copyright").html("&copy;"+data.enterpriseInfo.name+"版权所有");
		  		}
		  	});
		}
		
		function loginSystem(){
			/**
			setCookie("usernameHistory",$("#username").val(),24,"/");
			
			if( document.getElementById("rememeber").checked == true){  
	        	setCookie("username",$("#username").val(),24,"/");  
	        	setCookie("password",$("#password").val(),24,"/");  
	        }else{
	        	deleteCookie("username","/");
	        	deleteCookie("password","/");
	        }  
			**/
			document.getElementById("loginForm").submit();
		}
	</script>
</head>
<body>
    <div id="top"></div>
    <div id="main">
    	<div id="content">
        	<div id="SplitLine"></div>
            <form id="loginForm" method="post" action="login.action">
	            <div id="FormAll">
	            	<div id="FormInput">
	                	<input type="text" id="username" name="username" value="用户名" onfocus="if(value=='用户名') {value=''}" onblur="if (value=='') {value='用户名'}">
	                </div>
	                <div id="FormPassword">
	                	<input type="password" id="password" name="password" value="passwd" onclick="if(value=='passwd') {value='';$('#password').focus();}" onfocus="if(value=='passwd') {value=''}" onblur="if (value=='') {value='passwd'}">
	                </div>
<!-- 	                <div> -->
<!--                     	<span> -->
<!--                     		<input type="text" id="validCode" name="validCode" style="width:76px;height:18px;"> -->
<!--                     		<img id="kaptchaImg" src="Kaptcha.jpg" width="100" height="24" align="top" style="cursor: pointer;"/> -->
<!--                     	</span>	 -->
<!--                     </div>	 -->
                    <%
						Object loginError=(Object)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
						
						if(loginError!=null){
							String msg="";
							if(loginError instanceof BadCredentialsException){
								msg="密码输入错误！";
							}
							else if(loginError instanceof AuthenticationServiceException){
								AuthenticationServiceException ex=(AuthenticationServiceException)loginError;
								msg=ex.getMessage();
							}
							else{
								msg=loginError.toString();
							}
						%>
	                <div id="user_state" class="onError" style="height: 50px; line-height: 20px;margin-top: 18px;"><%=msg %></div>
	                <%
						}
	                %>
	                <div id="FormButton">
<!-- 	                	<div><input id="rememeber" type="checkbox" style="vertical-align:middle;">&nbsp;记住密码</div> -->
	                	<div class="button" onclick="loginSystem();"><div class="shine"></div>登&nbsp;&nbsp;录</div>
	                </div>
	                
	            </div>
            </form>
        </div>
    </div>
	<div id="logo" style="position:absolute; top:50%; left:50%; margin:-110px 0 0 -230px;"></div>
	<div id="systitle"></div>
	<div id="footer"><span id="copyright"></span></div>
</body>
</html>
