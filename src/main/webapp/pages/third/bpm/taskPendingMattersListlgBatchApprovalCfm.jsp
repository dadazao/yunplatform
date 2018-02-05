<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>待办事宜</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMsg.js"></script>
<script type="text/javascript">
window.returnValue='';

String.prototype.GetValue= function(para) {  
	  var reg = new RegExp("(^|&)"+ para +"=([^&]*)(&|$)");  
	  var r = this.substr(this.indexOf("\?")+1).match(reg);  
	  if (r!=null) return unescape(r[2]); return null;  
	}

 function save(){
 	var params= {taskIds:location.href.GetValue("taskIds"),opinion:document.getElementById("opinion").value};
 	
 	$.ligerDialog.confirm('批量批准只能批准同意，确认批量批准提交吗？','提示信息',function(rtn) {
		if(rtn) {
			
			$.post("${ctx}/platform/bpm/task/batchApproval.ht",params,function(msg){
				var obj=new com.sf.form.ResultMessage(msg);
				if(obj.isSuccess()){
					 $.ligerDialog.success('任务批准成功!','提示信息',function(){
			    		 //window.opener.location.reload();
			    		 window.returnValue='ok';
						 window.close(); 
			    	 });
				}else{
					 $.ligerDialog.error('任务批准失败!','提示信息');
		    		 window.returnValue='err';
					 window.close();
				}
			});
		}
	});
 }

</script>
</head>

<body>
<div class="panel">
<div class="hide-panel">
<div class="panel-top">
	<div class="tbar-title">
		<span class="tbar-label">批准意见</span>
	</div>

</div>
</div>

<div class="panel-body">
	
	<form id="baCfmForm" method="post" action="">
	<div class="row"><span class="label"><textarea rows="7" cols="61" id="opinion" name="opinion"></textarea></span></div>
<!-- <a href='#' class='button'  onclick="save()" ><span >确定</span></a> -->
			<input type="hidden" name="roleId" value="" />
		</form>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#" onclick="save()">确定并提交</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="javascript:window.close();">关闭</a></div>
			</div>
		</div>
</div>

</div>

</body>
</html>