<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	
%>
<style type="text/css">
<!--
.STYLE10 {
	font-family: "黑体";
	font-size: 20px;
}
-->  
</style>
<div align="center" style="margin-top:100px;">
	<div><img src="<%=basePath%>/images/error/error_top.jpg" width="510" height="80" /></div>
	<div style="width:510px;height:200px;" align="center" valign="top" >
	    <table width="510" height="200" border="0" cellspacing="0" cellpadding="0" background="<%=basePath%>/images/error/error_bg.jpg">
	        <tr>
	          <td width="40%" align="right" valign="middle">
	          	<img src="<%=basePath%>/images/icon/error.jpg" width="48" height="48">
	          	
	          </td>
	          <td width="60%" valign="middle" align="left">
	          	<div>
	          		<span class="STYLE10">系统错误!</span>&nbsp;&nbsp;
					<span onclick="javascript:document.getElementById('stackId').style.display='block';"><a style="cursor: pointer;color:blue;">详情>></a></span>
				</div>
	          </td>
	        </tr>
	        <tr>
	        	<td colspan="2" align="center">
	        		<div id="stackId" align="left" style="display:none;width:430px;height:140px;white-space:normal;overflow:auto;line-height:15px;overflow: auto;">
						<s:property value="exceptionStack"/>
					</div>
	        	</td>
	        </tr>
	     </table>
	</div>
	<div><img src="<%=basePath%>/images/error/error_bootom.jpg" width="510" height="32" /></div>
</div>

