<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>选择流程任务节点</title>
	<script type="text/javascript">
		//选择任务节点
		function selectTaskNode(){
			var nodeIdRd=$('.nodeId:checked');
			var currentDialog = $.pdialog.getCurrent();
			currentDialog.data('param','{nodeId:"'+nodeIdRd.val()+'",nodeName:"'+nodeIdRd.attr('nodeName')+'"}');
			$.pdialog.close("nodeUser");
		}
	</script>
</head>
<body>
<div class="pageContent">
		<div class="pageFormContent" layouth="60">
			<table cellpadding="1" cellspacing="1"  class="tableClass">
				<thead>
					<tr>
						<th width="40" class="thClass">
							序号
						</th>
						<th width="150" class="thClass">
							节点名称
						</th>
					</tr>
				</thead>
				<c:forEach items="${taskNodeMap}" var="map" varStatus="i">
					<tr>
						<td class="tdClass">
							<input type="radio" class="nodeId" name="nodeId" value="${map.key}" nodeName="${map.value}"/>
							<span>${i.count}</span>
						</td>
						<td class="tdClass">
							${map.value}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
				
		<div class="formBar" align="center">
			<button class="listbutton" type="button" onclick="selectTaskNode()">选择</button>
			<button class="listbutton" type="button" onclick="javasrcipt:$.pdialog.close('nodeUser');">关闭</button>
		</div>
</div>
	
</body>
</html>