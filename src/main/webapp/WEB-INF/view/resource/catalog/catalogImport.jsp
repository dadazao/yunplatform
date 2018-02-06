<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script type="text/javascript">
<!--
function selfImportDialogAjaxDone(json){
	if(json.statusCode == '200') {
		alertMsg.correct("操作成功");
		refresh();
	}else if(json.statusCode == '300') {
		alertMsg.error("操作失败");
	}else if(json.statusCode == '301'){
		alertMsg.error(json.message);
	}else if(json.indexOf("statusCode") > 0 && json.indexOf("200")>0) {
		$.pdialog.closeCurrent();
		alertMsg.confirm("是否要启动服务器?", {okCall:function(){restartServer();}});
	}else if(json.indexOf("statusCode") > 0 && json.indexOf("301")>0){
		alertMsg.error(json.substring(json.indexOf("message")+10,json.indexOf("navTabId")-3));
	}else{
		alertMsg.error("操作失败");
	}
}

function restartServer() {
	$.ajax({
  		type:'POST',
  		async: false,
  		url:'<%=basePath %>/pages/resource/catalog/restart.action',
  		success:function(data){
			eval(data);
  		}
  	});
}
//-->
</script>
  <body>
  	<form method="post" action="<%=basePath %>/pages/resource/catalog/importZip.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this, selfImportDialogAjaxDone);">
  		<br>
	    <table width="98%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td width="20%" height="30" align="left" class="Input_Table_Label">
					<label>压缩包</label>
				</td>
				<td width="80%" height="30" align="left">
					<input name="upload" value="${srcPackge}" type="file" class="required"/><!-- <span class="star">*</span> -->
				</td>
			</tr>
		</table>
		<br>
		<div align="center">
			<button id="domainSubmit" type="submit" class="listbutton" >确认</button>
			<button type="button" class="listbutton close">取消</button>
		</div>
	</form>
  </body>
</html>
