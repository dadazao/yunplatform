<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
sss<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
<!--		
	function copystart(){
		var newFormName = $("#newFormName").val();
		if(newFormName==""){
			alertMsg.warn("请填写表单名称！");
			return;
		}
		var hasFormName=false;
		$.ajax({
	  		type:'post',
	  		url:"<%=basePath %>/pages/resource/formisHasFormName.action",
	  		data:"newFormName="+newFormName,
	  		async:false,
	  		success:function(data){
				if(data=="true"){
					hasFormName=true;
				}
	  		}
	  	});
		
		if(hasFormName==true){
			alertMsg.warn("表单名称已经存在，请修改表单名称！");
			return;
		}
		
		$.pdialog.close("copyformDialog");
		var urlString = "<%=basePath %>/pages/resource/formcopyForm.action";
		var param = $("#tableForm").serialize();
		var result = urlString + "?" + param;
		$.ajax({
	  		type:'post',
	  		url:result,
	  		data:"newFormName="+newFormName,
	  		success:function(data){
				alertMsg.info("复制成功！");
				refresh();
	  		}
	  	});
	}
//-->
</script>
</head>

<body>
	<input id="newFormName" name="newFormName" maxlength="50" style="width: 195px;" type="text"/>&nbsp;&nbsp;<button type="button" class="listbutton" onclick="copystart()">确定</button>
</body>
</html>
