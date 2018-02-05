<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<f:js pre="js/lang/view/platform/bpm" ></f:js>
<title>批量审批</title>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js"></script>
<script type="text/javascript">
var taskIds="${param.taskIds}";
function save(){
	var result=$("#frmApprove").form().valid();
	if(!result) return;
	
	
	var params= {taskIds:taskIds,opinion:document.getElementById("opinion").value};
	$.ligerDialog.confirm($lang_bpm.batchApproval.confirmApproval,$lang.tip.msg,function(rtn){
		if(!rtn) return ;
		$.post("${ctx}/platform/bpm/task/batComplte.ht",params,function(msg){
		var obj=new com.hotent.form.ResultMessage(msg);
		if(obj.isSuccess()){
				$.ligerDialog.successExt($lang.tip.msg,$lang_bpm.batchApproval.approvalResult,obj.getMessage(),function(){
	    		 window.returnValue='ok';
				 window.close(); 
	    	 });
		}else{
			$.ligerDialog.err($lang.tip.msg,$lang.tip.errorMsg,obj.getMessage());
		}
	});
	});
 }
</script>
</head>

<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">批量审批</span>
			</div>
		</div>
		</div>
		<div class="panel-body">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#" onclick="save()"><span></span>审批</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link close"  href="#" onclick="window.close();"><span></span>关闭</a></div>
				</div>
			</div>
			<form id="frmApprove">
				<div  class="row"><span class="label" style="font-weight: bold;">审批意见：</span></div>
				<div class="row">
					<span class="label"><textarea rows="7" cols="61" id="opinion" name="opinion" validate="{required:true}"></textarea></span>
				</div>
				
			</form>
		</div>
	</div>

</body>
</html>