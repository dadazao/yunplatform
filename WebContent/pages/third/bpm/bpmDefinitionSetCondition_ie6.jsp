<%--
	time:2011-11-28 22:02:01
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/form.jsp"%>
<title>设置分支条件</title>
<script type="text/javascript"src="${ctx}/js/hotent/platform/bpm/FlowVarWindow.js"></script>
<script type="text/javascript"src="${ctx}/js/hotent/platform/system/ScriptDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/javacode/codemirror.js"></script>

<style type="text/css">
body{
	overflow-x:hidden;
	overflow-y:auto;
}
</style>
<script type="text/javascript">
	var nodeId = "${nodeId}";
	var deployId = "${deployId}";
	var defId = "${defId}";
	var currentTextArea=null;
	$(function() {
		$("a[name='btnVars']").click(selectVar);
		$("#btnScript").click(selectScript);
		$("a[name='signResult']").click(function() {
			addToTextarea($(this).attr("result"));
		});
		$("a.save").click(saveCondition);
		$('textarea').click(function(){
			currentTextArea=this;
		});
	});
	var flowVarWindow;
	//选择变量
	function selectVar() {
		FlowVarWindow({
			deployId : deployId,
			nodeId : nodeId,
			callback : function(vars) {
				addToTextarea(vars);
			}
		});
	};
	//将条件表达式追加到脚本输入框内
	function addToTextarea(str){
		$(currentTextArea).text(str);
	};
	
	function selectScript() {
		ScriptDialog({
			callback : function(script) {
				addToTextarea(script);
			}
		});
	};
	function handFlowVars(obj) {
		addToTextarea($(obj).val());
	};
	function saveCondition() {
		
		var tasks = [];
		var conditions = [];		
		$("tr.taskTr > td").each(function(){
			var condition=$("[name='condition']", $(this)).val();
			var task=$("[name='task']", $(this)).val(); 
			tasks.push(task);
			conditions.push(condition);
		});
		
		var url = __ctx + "/platform/bpm/bpmDefinition/saveCondition.ht";
		var paras = {
			"defId" : defId,
			"nodeId" : nodeId,
			"tasks" : tasks.join('#split#'),
			"conditions" : conditions.join('#split#')
		};

		$.post(url, paras, function(data) {
			var resultObj = new com.hotent.form.ResultMessage(data);
			if (resultObj.isSuccess()) {
				$.ligerDialog.success("编辑规则成功!","提示信息", function() {
					window.close();
				});
			} else {
				$.ligerDialog.warn("编辑规则失败,请检查条件表达式是否正确!","提示信息");
			}
		});
	};
</script>
</head>
<body>
	<div class="panel" >
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">条件分支设置</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="btnSearch"><span></span>保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link del" onclick="javasrcipt:window.close()"><span></span>关闭</a>
					</div>

				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="bpmNodeRuleForm" method="post" action="save.ht">
				
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0"  >
						<tr>
							<th>条件表达式</th>
							<td>
								<div style="margin: 8px 0; ">
									<a href="#" id="btnScript" class="link var" title="常用脚本">常用脚本</a>
									&nbsp;&nbsp;表单变量:
									<f:flowVar defId="${defId}" change="handFlowVars(this)"></f:flowVar>
								</div> 
								<c:forEach items="${incomeNodes}" var="inNode">
									<div style="padding: 4px;">
										<c:choose>
											<c:when test="${inNode.isMultiple==true}">
												<a href="#" name="signResult"
													result='signResult_${inNode.nodeId}=="pass"'>[${inNode.nodeName}]投票通过</a>
															&nbsp;
															<a href="#" name="signResult"
													result='signResult_${inNode.nodeId}=="refuse"'>[${inNode.nodeName}]投票不通过</a>
											</c:when>
											<c:otherwise>
												<a href="#" name="signResult"
													result="approvalStatus_${inNode.nodeId}==1">[${inNode.nodeName}]-通过</a>
															&nbsp;
															<a href="#" name="signResult"
													result="approvalStatus_${inNode.nodeId}==2">[${inNode.nodeName}]-反对</a>
											</c:otherwise>
										</c:choose>
										<ul>
											<li>1.先选中下方的脚本输入框，然后再插入条件表达式。</li>
											<li>2.表达式中不能有分号或return语句。</li>
										</ul>
																	
									</div>
								</c:forEach>							
							</td>
						</tr>
						<c:forEach items="${outcomeNodes}" var="outNode">
							<tr class="taskTr">
								<th width="20%">
									 ${outNode.nodeName }
								</th>
								<td>
									<input type="hidden" name="task" value="${outNode.nodeId}" />
									<textarea id="${outNode.nodeId}"  name="condition" rows="3" cols="50" class="inputText">${outNode.condition}</textarea>
								</td>
							</tr>
						</c:forEach>
					</table>
				
			</form>
		</div>
	</div>
</body>
</html>
