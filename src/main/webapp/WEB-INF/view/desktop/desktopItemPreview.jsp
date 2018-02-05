<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div style="width:100%;height:100%;background-color:white;">
<div class="portlet">
	<div class="phead">
		<div class="ptitle">
			<img src="pages/resource/portal/image/titleicon.gif" style="padding-top:5px;">
			${desktopItem.name}
		</div>
		<div class="poper" style="padding-top:6px;"><a href="#" onclick="loadList('${desktopItem.name}','${desktopItem.alias}','${desktopItem.moduleUrl}')" style="text-decoration: none;" title="列表查看所有信息">更多</a> </div>
	</div>
	<div class="pcontent">
		${Html}
	</div>
</div>
</div>
