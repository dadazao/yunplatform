<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<f:js pre="js/lang/view/platform/bpm" ></f:js>
	<title>批量取消转办(代理)</title>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript">
		var ids="${param.ids}";
	
		$(function() {
			$("#dataFormSave").click(save);
		});
		
		function callBack(rtn) {
			if(!rtn) return;
			var opinion=$("#opinion").val();
			var informType=$.getChkValue("informtype");
			var params= {ids:ids,opinion:opinion,informType:informType};
			$.post(__ctx+"/platform/bpm/bpmTaskExe/cancelBat.ht",params,function(msg){
				var obj=new com.hotent.form.ResultMessage(msg);
				if(obj.isSuccess()){
					$.ligerDialog.successExt($lang.tip.msg,$lang_bpm.cancelAssign.batCancelSuccess,obj.getMessage(),function(){
						 window.returnValue="ok";
			    		 window.close(); 
			    	});
				}else{
					 $.ligerDialog.error(obj.getMessage(),$lang.tip.msg);
				}
			});
		}

		function save(){
			var rtn=$("#bpmTaskExeForm").form().valid();
			if(!rtn) return;
		 	$.ligerDialog.confirm($lang_bpm.cancelAssign.confirmBatCancel,$lang.tip.msg,callBack);
		 }
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">
				批量取消转办(代理)
			</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>取消</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link close" href="#" onclick="window.close()" ><span></span>关闭</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="bpmTaskExeForm" method="post" >
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="20%">发送提醒消息: </th>
					<td>
						<label><input type="checkbox" name="informtype" value="3"  checked="checked"/>站内消息</label>
						<label><input type="checkbox" name="informtype" value="1"  checked="checked"/>邮件</label>
						<label><input type="checkbox" name="informtype" value="2"  />手机短信</label>
					</td>
				</tr>
				<tr>
					<th width="20%">取消原因: </th>
					<td>
						<textarea rows="5" cols="50" id="opinion"  validate="{required:true}" ></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>