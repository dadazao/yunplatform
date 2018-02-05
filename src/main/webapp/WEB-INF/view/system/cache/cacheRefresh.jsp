<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">	
	ns.cache.refreshCache = function(cache) {
		var urlString = "<%=basePath %>/pages/system/cache/refresh.action?cacheName=" + cache;
		alertMsg.confirm("确定要"+$("#"+cache).html()+"吗?", {okCall:function(){ajaxTodo(urlString,DWZ.ajaxDone);}});
	}
</script>
</head>
<body>
<div class="pageContent">
		<table border="0" width="100%" align="center" style="margin-top: 50px;">
			<tr>
				<td align="center" height="40" >
					<button id="all" type="button" class="listbutton" onclick="ns.cache.refreshCache('all');" style="width:100px;height:30px;" >刷新所有缓存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="formCache" type="button" class="listbutton" onclick="ns.cache.refreshCache('formCache');" style="width:100px;height:30px;" >刷新表单缓存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="tabulationCache" type="button" class="listbutton" onclick="ns.cache.refreshCache('tabulationCache');" style="width:100px;height:30px;" >刷新列表缓存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="themeCache" type="button" class="listbutton" onclick="ns.cache.refreshCache('themeCache');" style="width:100px;height:30px;" >刷新主题缓存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="privilegeCache" type="button" class="listbutton" onclick="ns.cache.refreshCache('privilegeCache');" style="width:100px;height:30px;" >刷新权限缓存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="desktopCache" type="button" class="listbutton" onclick="ns.cache.refreshCache('desktopCache');" style="width:100px;height:30px;" >刷新桌面缓存</button>
				</td>
			</tr>
		</table>
</div>
</body>
</html>