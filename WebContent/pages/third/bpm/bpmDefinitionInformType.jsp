<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <title>通知方式配置</title>
	<script type="text/javascript">
		$(function(){
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
		});
		
		function saveInformType() {
			$.ajax( {
				type : 'POST',
				url : __basePath+'/pages/third/bpm/bpmDefinition/saveInformType.action',
				data: $("#informTypeForm").serialize(),
				dataType: 'json',
				success : function(json) {
					if(json.statusCode == 200){
						alertMsg.correct(json.message);
					}else{
						alertMsg.error(json.message);
					}
				}
			});
		}
		
		function getMsgTypeList(id){
			var msgTypeList=[];
			$("input[name='"+id+"']").each(function(){
				var me = $(this),
					val = me.val(),
					state = me.attr("checked");
				if(state)
					msgTypeList.push(val);
			});
			return msgTypeList.join(',');
		}
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.success(obj.getMessage(),"提示信息", function(rtn) {
					if(rtn){
						window.close();
					}
				});
			} else {
				$.ligerDialog.err("出错信息","编辑手机访问设置失败",obj.getMessage());
			}
		}
	</script>
</head>
<body>
<div class="pageContent">
		<div class="panelBar">
			<button class="listbutton" id="dataFormSave" type="button" onclick="saveInformType();">保存</button>
			<button class="listbutton close" >关闭</button>
		</div>
		<div class="pageFormContent" layouth="100">
			<div class="tbar-title">
				<span class="tbar-label">
					 说明：设置流程任务节点通知方式，该节点产生任务将下面选择方式发送通知给用户。
					<br><br><br><br>
			    </span>
			</div>
			<form id="informTypeForm" method="post" action="saveInformType.action">
				<div  id="nodeTypeDiv">
					<label><b>通知方式 ${bpmNodeSet.nodeName} : </b></label> 
					<input type="checkbox" name="informType" value="1" <c:if test="${fn:contains(bpmNodeSet.informType,1)}">checked="checked"</c:if> />
					邮件
					<input type="checkbox" name="informType" value="2" <c:if test="${fn:contains(bpmNodeSet.informType,2)}">checked="checked"</c:if> />
					短信
					<input type="checkbox" name="informType" value="3" <c:if test="${fn:contains(bpmNodeSet.informType,3)}">checked="checked"</c:if> />
					站内消息
					<input type="hidden" name="actDefId" value="${bpmNodeSet.actDefId}" />
					<input type="hidden" name="nodeId" value="${bpmNodeSet.nodeId}" />
					<input type="hidden" id="informTypes" name="informTypes" value=""/>
				</div>
			</form>
		</div>
</div>
</body>
</html>