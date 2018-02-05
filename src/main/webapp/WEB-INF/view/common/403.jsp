<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="org.springframework.security.access.AccessDeniedException"  pageEncoding="UTF-8" isErrorPage="true" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	
	AccessDeniedException ex=(AccessDeniedException)request.getAttribute("ex");
	
%>
<style type="text/css">
<!--
.STYLE10 {
	font-family: "黑体";
	font-size: 36px;
}
-->  
</style>
<div id="403Div" align="center" style="margin-top:100px;">
	<div><img src="<%=basePath%>/images/error/error_top.jpg" width="510" height="80" /></div>
	<div style="width:510px;height:200px;" align="center" valign="top" >
	    <table width="510" height="200" border="0" cellspacing="0" cellpadding="0" background="<%=basePath%>/images/error/error_bg.jpg">
	        <tr>
	          <td width="34%" align="right"><img src="<%=basePath%>/images/error/error.gif" width="128" height="128"></td>
	          <td width="66%" valign="top" align="center">
	          	<table width="100%">
	          		<tr height="25">
	          			<td>
	          			<span class="STYLE10">访问被拒绝</span>
	          			</td>
	          		</tr>
	          		<tr height="70">
	          			<td>
	          			<%=ex.getMessage() %>
	          			</td>
	          		</tr>
	          		
	          		<tr height="25">
		          		<td style="padding-left:50px;">
		          		  <a href="#" onclick="javascript:location.href='<%=basePath%>/logout';" style="color:blue;">重新登录</a> 
		          		</td>
	          		</tr>
	          	</table>
	          </td>
	        </tr>
	     </table>
	</div>
	<div><img src="<%=basePath%>/images/error/error_bootom.jpg" width="510" height="32" /></div>
</div>
