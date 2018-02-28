<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<SCRIPT LANGUAGE="JavaScript">
extArray = new Array(".gif", ".jpg", ".png",".bmp");
function LimitAttach(form, file) {
	var allowSubmit = false;
	if (!file) {
		alertMsg.warn("请选择上传文件");
		return false;
	}
	while (file.indexOf("\\") != -1)
	file = file.slice(file.indexOf("\\") + 1);
	ext = file.slice(file.indexOf(".")).toLowerCase();
	for (var i = 0; i < extArray.length; i++) {
	if (extArray[i] == ext) { allowSubmit = true; break; }
	}
	if (allowSubmit){
		return iframeCallback(form,selfDialogAjaxDone);
	}
	else{
		alertMsg.warn("只能上传:  " 	+ (extArray.join("  ")) + "\n请重新选择文件再上传.");
	}
	return false;
}

function selfDialogAjaxDone(json) {
	$.pdialog.closeCurrent();
	if(json.success == 'true') {
		alertMsg.correct("操作成功");
		navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
	}else{
		if(json.indexOf("success") > 0) {
			alertMsg.correct("操作成功");
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		}else{
			alertMsg.error("操作失败");
		}		
	}
}
</script>
<form method="post" action="<c:url value='/pages/system/logo/add.action'/>" class="pageForm required-validate" onsubmit="return LimitAttach(this,this.upload.value);" enctype="multipart/form-data">
	<div style="vertical-align: middle;">
		<p>
		<br>
			选择logo：<input type="file" name="upload" style='color: transparent;'>
		</p>
		<br>
		<br>
		<br>
		<p align="right">
			<button id="b1" name="b1" class="listbutton" type="submit">上传</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</p>
	</div>
</form>