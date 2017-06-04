<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/pages/third/bpm/nodeUserConditionJS.jsp" %>
	<script type="text/javascript">
		$(function(){
			$("a.del").unbind("click");
			$("div.group > a.link.update").unbind('click');
			$('#btnReflesh').attr('href',window.location.href)
		});
		
		function repairDisposableData(){
			$.post(__basePath+"/pages/third/bpm/bpmDefinition/repairDisposableData.action", {},function(data){
				var result=eval('('+data+')');
				if(result.result==1){
					$.ligerMessageBox.warn(result.message+",请自行刷新页面");
				}else{
					$.ligerMessageBox.warn(result.message);
				}					
			});	
			
		}
		resultMessage = function(data) {
			this.data = eval("(" + data + ")");
			this.isSuccess = function() {
				return this.data["result"] == 1;
			};
			this.getMessage = function() {
				return this.data["message"];
			};
		};
		/**
		* 保存批次号
		*/
		function saveGroupNo(nodeId){
			var conditionIds=[],
				groupNos=[];
			var url = __basePath+"/pages/third/bpm/bpmUserCondition/updateGroup.action";
			var id = "table_" + nodeId;
			var table = $('#' + id);
			table.find("tbody.datat").find("input[name=groupNo]").each(function(){
				var _this = $(this);
				if(_this.val()!=_this.attr("ivalue")){
					var groupNo = _this.val();
					var tr = _this.closest("tr");
					var conditionId = $("input[name=conditionId]", tr).val();
					conditionIds.push(conditionId);
					groupNos.push(groupNo);
				}
			});
			if(conditionIds.length==0){
				return;
			}
			var params = {
					conditionIds:conditionIds.join(","),
					groupNos:groupNos.join(",")
			};
			
			$.ajax({
				url: url,
				type:'post',
				data:params,
				dataType:'json',
				success: function(data){
					if (data.statusCode=='200') {
						alertMsg.correct("更新批次号成功");
					} else {
						alertMsg.error("保存失败");
					}	
				}
			});
		};

		/**
		* 分组号值变更
		*/
		function changeGroupNo(){
			var _this=$(this);
			//_this.addClass("inputChange");

			var td = _this.closest("td");
			var tr = _this.closest("tr");
		
			var groupNo = _this.val();
			
			groupNo = groupNo.replace(/(^\s*0*)|(\s*$)/g,"");
			if(!/^\d+$/.test(groupNo)){
				groupNo=1;
			}
			_this.val(groupNo);
			
			$("div[name=groupNo]",td).text(groupNo).show();

			var url = __ctx+"/platform/bpm/bpmUserCondition/updateGroup.ht";

			var conditionId = $("input[name=conditionId]",tr).val();
			var params = {
				conditionId:conditionId,
				groupNo:groupNo
			};

			var oldGroup = _this.attr("ivalue");
			//_this.hide();

			if(oldGroup==groupNo){
				tr.removeClass("inputChange");
				return;
			}else{
				if(!tr.hasClass("inputChange"))
					tr.addClass("inputChange");
			}

		};
	</script>
	
	<base target="_self" />
</head>
<body style="overflow: auto;height:100%;">	
	<div class="panel">
		<div class="panel-body">
			<form action="saveUser.ht" method="post" id="defUserForm">
				<input type="hidden" name="definitionId" value="${definitionId}"/>
				<input type="hidden" name="nodeTag" value="${nodeTag}"/>
				<table class="tableClass" style="width:100%">
					<tr>
						<th class="thClass" width="50" nowrap="nowrap">序号</th>
						<th class="thClass" width="200">节点名称</th>
						<th class="thClass" width="*">节点人员</th>
					</tr>
					<c:forEach items="${nodeUserMapList}" var="nodeUserMap" varStatus="i">
						<tr>
							<td class="tdClass">${i.count}</td>
							<td class="tdClass">${nodeUserMap.nodeName}(${nodeUserMap.nodeId})</td>
							<td class="tdClass">
								<%@include file="/pages/third/bpm/nodeUserCondition.jsp" %>
							</td>
						</tr>
					</c:forEach>
				</table>
			</form>
			
		</div>				
	</div>
	<div id="divScriptData" style="display: none;">
		
		<a href="#" id="btnScript" class="link var" title="常用脚本" onclick="selectScript()">常用脚本</a>
		<ul>
			<li>可以使用的流程变量,[startUser],开始用户,<li>[startUser],上个任务的用户[prevUser]。</li>
			<li>表达式必须返回Set&lt;String&gt;集合类型的数据,集合元素为用户Id。</li>
		</ul>
		<textarea id="txtScriptData" rows="10" cols="80" style="height: 200px;width:480px"></textarea>
	</div>
	
</body>
</html>
