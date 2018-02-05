<%--
	time:2011-12-14 15:41:53
	desc:edit the 流程节点规则
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>

<html>
<head>
<title>流程节点跳转规则设置</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmNodeRule"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ScriptDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/javacode/codemirror.js"></script>

<style type="text/css">
a.ruledetail,a.delrule {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	var deployId="${deployId}";
	var actDefId="${actDefId}";
	var nodeId="${nodeId}";
	var currentTextarea=null;
	function showRequest(formData, jqForm, options) {
		return true;
	}
	$(function() {

		valid(showRequest,function(){});
  		$("#layoutFlowRule").ligerLayout({ rightWidth:200, height: '95%' });
		 //加载列表
		 loadRuleList(); 		
		 $("#btnScript").click(selectScript);
		 $("a.save").click(save);
		 $("#btnAdd").click(addRule);
		 $('textarea').click(function(){
			 currentTextarea=this;
		 })
		 handFlowVars();
	});
	
	function handFlowVars(){		
		$("select[name='selFlowVar']").change(function(){
			var val=$(this).val();
			$(currentTextarea).append(val);
		});
	}
	
	function addRule(){
		$("#ruleId").val("0");
		$("#memo").val("");
		$("#ruleName").val("");
		$('textarea[name="conditionCode"]').text('return true');
	}
	
	function save(){
		
		 var rtn=$("#bpmNodeRuleForm").valid();
		 if(!rtn) return;
		 var url=__ctx+ "/platform/bpm/bpmNodeRule/save.ht";
		 var para=$('#bpmNodeRuleForm').serialize();
		 $.post(url,para,showResult);
	}
	
	
	function showResult(responseText)
	{
		var obj=new com.hotent.form.ResultMessage(responseText);
		var ruleId=$("#ruleId").val();
		if(!obj.isSuccess()){
			$.ligerDialog.err('出错信息',"添加规则失败",obj.getMessage());
			return;
		}
		//添加
		if(ruleId=="0"){
			$.ligerDialog.confirm('添加规则成功,继续添加吗?','提示信息',function(rtn){
				if(!rtn){
					window.close();
				}
				else{
					__valid.resetForm();
					loadRuleList();
				}
			});
		}
		//更新
		else{
			$.ligerDialog.confirm('更新规则成功,继续更新吗?','提示信息',function(rtn){
				if(!rtn){
					window.close();
				}
				else{
					loadRuleList();
				}
			});
		}
	}
	function selectScript(){
		ScriptDialog({callback:function(script){
			$(currentTextarea).append(script);
		}});
	}
	
	
	//显示规则明细
	function showDetail(){
		var obj=$(this);
		var ruleId=obj.attr("rule");
		var url=__ctx + "/platform/bpm/bpmNodeRule/getById.ht?ruleId=" + ruleId;
		$.get(url,function(data) {
			var json=jQuery.parseJSON(data);
			jQuery.setFormByJson(json);
			var tmp=json.targetNode +"," + json.targetNodeName;
			$("select[name='targetNode']").val(tmp);			
			$(currentTextarea).append(document.getElementById("conditionCode").value);
		});
	}
	
	function getRow(ruleId,ruleName,idx){
		var className=(idx % 2==0)?"odd":"even";
		var aryRow=["<tr class='"+className+"'>",
		"<td>",
		"<a class='ruledetail' rule='"+ruleId+"'>"+ruleName+"</a>",
		"</td>",
		"<td>",
		"<a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'>&nbsp;&nbsp;&nbsp;</a>",
		"<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'>&nbsp;&nbsp;&nbsp;</a>",
		"<a alt='删除'  class='delrule link del' rule='"+ruleId+"'>&nbsp;&nbsp;&nbsp;</a>",
		"</td>",
		"</tr>"];
		return aryRow.join("");
	}
	//加载规则列表
	function loadRuleList(){
		var url=__ctx + "/platform/bpm/bpmNodeRule/getByDefIdNodeId.ht?actDefId=" + actDefId +"&nodeId=" + nodeId;
		url=url.getNewUrl();
		var tbodyList=$("#ruleList");
		tbodyList.empty();
		$.get(url,function(data) {
			var jsonAry=jQuery.parseJSON(data);
			for(var i=0;i<jsonAry.length;i++){
				var obj=jsonAry[i];
				var row=getRow(obj.ruleId,obj.ruleName,i);
				tbodyList.append($(row));
			}
			$("a.ruledetail").click(showDetail);
			$("a.delrule").click(delByRule);
		});
	}

	//删除规则	
	function delByRule(){
		var obj=$(this);
		var ruleId=obj.attr("rule");
		$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
			if(!rtn) return;
			var url=__ctx + "/platform/bpm/bpmNodeRule/del.ht?ruleId=" + ruleId;
			$.get(url,function(data) {
				var obj=new com.hotent.form.ResultMessage(data);
				if(obj.isSuccess()){
					$.ligerDialog.success('删除成功!','提示信息',function(){
						loadRuleList();
					});
				}
				else{
					$.ligerDialog.warn('删除失败!','提示信息');
				}
			});
		});
	}
	
	//规则上移
	function sortUp(obj) {
		var thisTr = $(obj).parents("tr");
		var prevTr = $(thisTr).prev();
		if(prevTr){
			thisTr.insertBefore(prevTr);
			reSort();
		}
	};
	//重新排序
	function reSort(){
		var ruleids="";
		$("a.ruledetail").each(function(i){
			ruleids+=$(this).attr("rule") +",";
		});
		if(ruleids!="")
			ruleids=ruleids.substring(0, ruleids.length-1);
		
		var url=__ctx + "/platform/bpm/bpmNodeRule/sortRule.ht";
		var params="ruleids=" +ruleids;
		$.post(url,params,function(data){});
	}
	 
	// 规则下移
	function sortDown(obj) {
		var thisTr = $(obj).parents("tr");
		var nextTr = $(thisTr).next();
		if(nextTr){
			thisTr.insertAfter(nextTr);
			reSort();
		}
	}
	
	//更新那个bpm_node_set的IsJumpForDef字段
	function updateIsJumpForDef(ck){
		var url=__ctx+"/platform/bpm/bpmNodeRule/updateIsJumpForDef.ht";
		var params={
			nodeId:nodeId,
			actDefId:actDefId,
			isJumpForDef:ck.checked? 1:0
		};
		$.post(url,params,function(data){});
	}

</script>
<style>
html { overflow-x: hidden; }
</style>
<body>

	<div class="panel">
		<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">流程节点跳转规则设置</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link add" id="btnAdd"><span></span>增加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link save" id="btnSearch"><span></span>保存</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del" onclick="javasrcipt:window.close()"><span></span>关闭</a></div>
						</div>	
					</div>
				</div>
		<div class="panel-body">
			<div id="layoutFlowRule" style="width: 100%">
				<div  style="width:100%;" position="right" title="规则列表">
					<table  cellpadding="1" class="table-grid table-list" cellspacing="1">
						<tr>
							<th>规则名称</th><th>管理</th>
						</tr>
						<tbody id="ruleList">
						</tbody>
					</table>
				</div>
				<div  id="framecenter" position="center">
					<form id="bpmNodeRuleForm" method="post" action="save.ht">
						<div class="panel-detail">
							<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<th width="20%">当前节点名称:</th>
									<td>${nodeName}</td>
								</tr>
								<tr>
									<th width="20%">后续节点:</th>
									<td>
										<c:forEach items="${nextNodes}" var="node" varStatus="status">
											
											${node.nodeName } (${node.nodeId })
											<c:if test="${! status.last }">,</c:if>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<th>当规则符合时是否正常跳转</th>
									<td>
										<input type="checkbox" name="isJumpForDef" value="1" onclick="updateIsJumpForDef(this);" <c:if test="${bpmNodeSet.isJumpForDef==1}">checked="checked"</c:if> >
									</td>
								</tr>
								<tr>
									<th width="20%">规则名称: <span class="required">*</span></th>
									<td><input type="text" id="ruleName" name="ruleName" size="40" value="${bpmNodeRule.ruleName}" class="inputText" /></td>
								</tr>
								<tr>
									<th width="20%">规则表达式:<span class="required">*</span></th>
									<td>
										<div style="margin:8px 0;">
											
											<a href="#" id="btnScript" class="link var" title="常用脚本">常用脚本</a>
											&nbsp;&nbsp;表单变量:<f:flowVar defId="${defId}" controlName="selFlowVar"></f:flowVar>
										</div>
										<textarea id="conditionCode"  rows="12" cols="55" name="conditionCode" >return true;</textarea>
										<br/> 
										<div style="margin:8px 0;">这个脚本需要使用返回语句(return)返回布尔值，返回true流程将跳转到指定的节点。</div>
									</td>
								</tr>
								<tr>
									<th width="20%">跳转节点名称:</th>
									<td>
										<select name="targetNode">
											<c:forEach items="${activityList}" var="item">
												<optgroup label="${item.key}">
													<c:forEach items="${item.value}" var="node">
														<option value="${node.key},${node.value}">${node.value}</option>
													</c:forEach>
												</optgroup>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<th width="20%">备注:</th>
									<td>
										<textarea id="memo" rows="4" cols="40" name="memo" >${bpmNodeRule.memo}</textarea>
									</td>
								</tr>
							</table>
							<input type="hidden" id="ruleId" name="ruleId" value="${bpmNodeRule.ruleId}" />						
							<input type="hidden" name="priority" value="${bpmNodeRule.priority}" />
							<input type="hidden" name="actDefId" value="${bpmNodeRule.actDefId}" />
							<input type="hidden" name="nodeId" value="${bpmNodeRule.nodeId}" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

