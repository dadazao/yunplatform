<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

	//加载子列表
	ns.product.loadSubList = function(){
		$("#pingjiaSubListDiv").loadUrl('pages/business/product/pingjia/sublist.action?productId='+$("#domainId").val());
	}
	//加载编辑区
	ns.product.loadSubEdit = function(domainId){
		if(domainId){
			$("#pingjiaSubEditDiv").loadUrl('pages/business/product/pingjia/edit.action?pingjiaId='+domainId);
		}else{
			$("#pingjiaSubEditDiv").loadUrl('pages/business/product/pingjiaEdit.jsp');
		}
	}
	
	$(function(){
		ns.product.loadSubEdit();
		ns.product.loadSubList();
		ns.common.mouseForButton();
	});
</script>
</head>
<body>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" align="left" class="Input_Table" >
	<tbody>
	<tr>
		<td>
			<div id="pingjiaSubEditDiv">
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div id="pingjiaSubListDiv">
			</div>
		</td>
	</tr>
	</tbody>
	</table>
</body>
</html>