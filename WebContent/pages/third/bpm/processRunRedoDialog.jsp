<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<f:js pre="js/lang/view/platform/bpm" ></f:js>
<title>追回原因</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMsg.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript">
var callBack=function(rtn){
	 if(!rtn) return;
	 var json=$("#frmRecover").serialize() ;
	
	 var url=__ctx +"/platform/bpm/processRun/recover.ht";
	 $.post(url,json,function(responseText){
		 var obj=new com.hotent.form.ResultMessage(responseText);
		 if(obj.isSuccess()){
			 $.ligerDialog.success(obj.getMessage(),$lang.tip.msg,function(){
				 window.returnValue=1;
				 window.close();
			 });
		 }
		 else{
			 $.ligerDialog.err($lang.tip.msg,$lang.save.failure,obj.getMessage());
		 }
	 });
};

$(function(){
	 $("a.save").click(function(){	
		 if($.isEmpty( $('#opinion').val())){
			 $.ligerDialog.warn('必须填写追回原因!',$lang.tip.msg);
			 $('#opinion').focus();
			 return;
		 }
		 $.ligerDialog.confirm('是否确认追回?',$lang.tip.msg,callBack);
	 });
})
</script>
</head>
<body>
	<div class="panel">
		
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">追回原因</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>追回</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link close" href="#" onclick="window.close();"><span></span>关闭</a></div>
				</div>
			</div>
		</div>
		
		<div class="panel-body">
			<form id="frmRecover">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th nowrap="nowrap">发送方式:</th>
						<td>
							<input type="checkbox" name="informType" value="3" checked="checked">站内消息</label>
							<input type="checkbox" name="informType" checked="checked" value="1">邮件
							<input type="checkbox" name="informType" value="2">短信
						</td>
					</tr>
					<tr>
						<th>追回原因:</th>
						<td>
							<textarea rows="7" cols="61" style="width:350px;" id="opinion" name="opinion"></textarea>
							<input type="hidden" name="runId" value="${runId }"/>
							<input type="hidden" name="backToStart" value="${backToStart}"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>