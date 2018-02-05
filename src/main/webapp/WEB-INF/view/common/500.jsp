<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	
%>
<style type="text/css">
<!--
.STYLE10 {
	font-family: "黑体";
	font-size: 50px;
}
-->  
</style>
<div align="center" style="margin-top:100px;">
	<div><img src="<%=basePath%>/images/error/error_top.jpg" width="510" height="80" /></div>
	<div style="width:510px;height:200px;" align="center" valign="top" >
	    <table width="510" height="200" border="0" cellspacing="0" cellpadding="0" background="<%=basePath%>/images/error/error_bg.jpg">
	        <tr>
	          <td width="50%" align="right"><img src="<%=basePath%>/images/error/error.gif" width="128" height="128"></td>
	          <td width="50%" valign="middle" align="left">
	          	<span class="STYLE10">500</span>
	          </td>
	        </tr>
	     </table>
	</div>
	<div><img src="<%=basePath%>/images/error/error_bootom.jpg" width="510" height="32" /></div>
</div>
