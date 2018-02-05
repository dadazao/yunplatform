<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
function openOpinionDialog(){
	var data=CustomForm.getData();
	//增加抄送人员
	var url=__ctx + "/platform/bpm/task/opinionDialog.ht?taskId=${task.id}&formData="+data;
	var winArgs="dialogWidth=500px;dialogHeight=300px;help=0;status=0;scroll=0;center=1";
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(rtn=="ok"){
		if(window.opener && window.opener.location){
			window.opener.location.href=window.opener.location.href.getNewUrl();
		}
		window.close();
	}
}
</script>
<div class="panel-top noprint">
	<div class="panel-toolbar">
		<div class="toolBar">
			<div class="group"><a id="btnNotify" class="link agree" onclick="openOpinionDialog()"><span></span>反馈</a></div>
			<div class="l-bar-separator"></div>
			<div class="group"><a class="link setting" onclick="showTaskUserDlg()"><span></span>流程图</a></div>
			<div class="l-bar-separator"></div>
			<div class="group"><a class="link search" onclick="showTaskOpinions()"><span></span>审批历史</a></div>
		</div>	
	</div>
</div>