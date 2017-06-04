<%
	//某个任务节点的分发及汇总属性的设置
%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑流程分发配置</title>
    <%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript">
		$(function(){
			initControl();
			$("#dataFormSave").click(function(){
				saveConfig();
			});
		});
		
		function saveConfig(){
			var url=__ctx+ "/platform/bpm/bpmDefinition/saveForkJoin.ht";
			var checked=$("#nodeType1").attr("checked");
			if(checked){
				$("#joinTaskKey").val('');
				$("#joinTaskName").val('');
			}
			 var param=$('#forkJoinForm').serialize();		        		
			 $.post(url,param,showResult);
		}
		
		function showResult(responseText){
			var obj=new com.hotent.form.ResultMessage(responseText);
			if(!obj.isSuccess()){
				$.ligerDialog.err('出错信息',"编辑流程分发配置失败",obj.getMessage());
				return;
			}else{
				$.ligerDialog.success(obj.getMessage(),'提示消息',function(){
					window.close();
				});
			}
		}
		
		function initControl(){
			$("#nodeTypeDiv input[name='nodeType']").on('click',function(){
				   var value=$(this).val();
				   if(value==1){
					   $("#joinTaskDiv").css('display','block');
				   }else{
					   $("#joinTaskDiv").css('display','none');
				   }
			});
		
			$("#joinTaskKey").on('change',function(){
				   var value=this.value;
				   var text=this.options[this.selectedIndex].text;
				  
				   if(value!=''){
					   $("#joinTaskName").val(text);
				   }else{
					   $("#joinTaskName").val('');
				   }
			});
			
		}
	</script>
</head>
<body>
<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
					<c:choose>
						<c:when test="${bpmNodeSet.joinTaskName==null}">
							添加流程分发汇总配置
						</c:when>
						<c:otherwise>
							编辑流程分发汇总配置
						</c:otherwise>
				   </c:choose> 
			    </span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" href="#" onclick="window.close()"><span></span>关闭</a></div>
				</div>
			</div>
</div>
<form id="forkJoinForm">
	<div style="padding:8px 8px 8px 8px">
		<b>说明：</b>
		<ul>
			<li>若为普通任务节点时，流程跳转至该任务，会自动产生该一个任务实例</li>
			<li>若为分发任务节点：流程跳至该任务时，会根据分发的个数，产生分发数的任务实例</li>
			<li>若为汇集任务节点：会根据分布的任务个数执行的路径，最终汇集生成一个任务实例</li>
		</ul>
	</div>
	<div style="padding:8px 8px 8px 8px" id="nodeTypeDiv">
		<input type="radio" name="nodeType" <c:if test="${bpmNodeSet.nodeType==0}">checked="checked"</c:if> value="0" id="nodeType1"><label for="nodeType1">普通任务节点</label>
		<input type="radio" name="nodeType" <c:if test="${bpmNodeSet.nodeType==1}">checked="checked"</c:if> value="1" id="nodeType2"><label for="nodeType2">分发任务节点</label>
		<input type="hidden" name="actDefId" value="${actDefId}" />
		<input type="hidden" name="nodeId" value="${nodeId}" />
	</div>
	<div style="padding:8px 8px 8px 8px; <c:if test="${bpmNodeSet.nodeType!=1}">display:none</c:if>" id="joinTaskDiv">
		<label>汇总节点</label>
		<select name="joinTaskKey" id="joinTaskKey">
			<option value="">请选择汇总节点</option>
			<c:forEach items="${nodeMap}" var="node">
				<option value="${node.key}" <c:if test="${bpmNodeSet.joinTaskKey==node.key}">selected</c:if> >${node.value}</option>
			</c:forEach>
		</select>
		<input type="hidden" name="joinTaskName" id="joinTaskName" value="${bpmNodeSet.joinTaskName}"/>
	</div>
</form>
</body>
</html>