<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
	<title>编辑常用语管理</title>
	
	<script type="text/javascript">
		function showRequest(formData, jqForm, options) { 
			return true;
		} 
	
		$(function() {
			var nodeId =   '${nodeId}';
			var isGlobal = '${isGlobal}'
			
			if(isGlobal=="1"){
				checkIsCommon(0);
				$("#isGlobal").attr("checked",'true');
			}
			else
				checkIsCommon(1);

		});
		
		function saveFlowApp(){
   	    	$.ajax( {
				type : 'POST',
				url : __basePath+'/pages/third/bpm/taskApprovalItems/save.action',
				data: $('#taskApprovalItemsForm').serialize(),
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
		
		function showResponse(data){
			var obj=new com.hotent.form.ResultMessage(data);
			if(obj.isSuccess()){//成功
				$.ligerDialog.confirm('操作成功,继续操作吗?','提示信息',function(rtn){
					if(rtn){
						location.reload();
					}else{
						window.close();
					}
				});
		    }else{//失败
		    	$.ligerDialog.err('出错信息',"保存常用语设置失败",obj.getMessage());
		    }
		};
		
		// 设置是否全局
		function checkIsCommon(value)
		{
			if(value==1){
				$('#trTaskNode').show();
				$('#isGlobal').attr('value',0);
				var nodeExp = $('#tmpNodeExp').attr('value').replaceAll("{,}","\r\n");
				$('#approvalItem').val(nodeExp);
			}
			else
			{
				$('#trTaskNode').hide();
				$('#isGlobal').attr('value',1);
				var defExp = $('#tmpDefExp').attr('value').replaceAll("{,}","\r\n");
				$('#approvalItem').val(defExp);
			}
		}
		
		// 节点ID
		function changeNodeId(value){
			
			$('#nodeId').attr('value',value);
			
			var url=__ctx+ "/platform/bpm/taskApprovalItems/get.ht";
   		 	var para=$('#taskApprovalItemsForm').serialize();
   		 	
   	    	$.post(url,para,function(result){
   	    			var obj = eval('(' + result + ')');
   	    			$('#tmpNodeExp').attr('value',obj.message);
   	    			$('#approvalItem').val(obj.message);
   	    		}
   	    	);
		}
		
	</script>
</head>
<body>
<div class="pageContent">
	<div class="panelBar">
		<button class="listbutton" id="dataFormSave" type="button" onclick="saveFlowApp();">保存</button>
		<button class="listbutton close" >关闭</button>
	</div>
	<div class="pageFormContent" layouth="60">
		<form id="taskApprovalItemsForm" method="post" action="save.action">
			<div class="panel-detail">
				<table class="Input_Table" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="20%" class="Input_Table_Label">是否全局: </td>
						<td ><input type="checkbox" id="isGlobal" name="isGlobal" value="0" onclick="checkIsCommon(value)"/></td>
					</tr>
					<tr id="trTaskNode">
						<td width="20%" class="Input_Table_Label">任务节点: </td>
						<td class="tdClass">
							
							<div id="defNodeDiv">
								<select id="defNode" name="defNode"  onchange="changeNodeId(value)">
									<option value="">-请选择-</option>
									<c:forEach items="${nodeMap}" var="node">
										<c:choose>
											<c:when test="${node.key==nodeId }">
												<option value="${node.key }" selected="selected">${node.value }</option>
											</c:when>
											<c:otherwise>
												<option value="${node.key }">${node.value }</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td width="20%" class="Input_Table_Label">常用语: </td>
						<td class="tdClass">
							<textarea rows="5" cols="60" id="approvalItem" name="approvalItem" 
								style="margin-top: 5px;margin-bottom: 5px;">${nodeExp}</textarea>
						</td>
					</tr>
				</table>
				
				<input type="hidden" id="tmpDefExp" name="tmpDefExp" value="${defExp}" />
				<input type="hidden" id="tmpNodeExp" name="tmpNodeExp" value="${nodeExp}" />
				<input type="hidden" id="actDefId" name="actDefId" value="${actDefId}" />
				<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" />
				<input type="hidden" id="setId" name="setId" value="${setId}" />
			</div>
		</form>
	</div>
</div>
</body>
</html>
