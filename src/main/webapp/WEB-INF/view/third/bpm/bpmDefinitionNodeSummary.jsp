<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<style>
.green-set{
 	font-weight: bold;color: green;font-size: 20px;
 }
.red-set{
	font-weight: bold;color: red;font-size: 20px;
}
.normal-set{
	font-weight: bold;font-size: 20px;
}
</style>
</head>    
<body>
	<div>
		<div>
			图示：<span class="green-set">√</span>：表示已经设置 &nbsp;&nbsp;&nbsp;&nbsp;
			<span class="red-set" >×</span>：表示未设置&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="normal-set" >-</span>：表示没有该功能&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<form method="post" >
			<input type="hidden" name="defId" value="${definitionId}" />
			<input type="hidden" name="nodeId" value="${nodeId}" />
			<table class="Input_Table" width="100%" style="margin-top:10px;text-align: center;">
					<tr>
						<td width="15px" nowrap="nowrap"  rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar"  >序号</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar ">节点名称</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar">人员设置</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar">常用语设置</td>
						<td colspan="4" style="text-align: center;font-weight: bold;" class="panel-toolbar">流程事件</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar">流程规则</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar">表单设置</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar">操作按钮</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;" class="panel-toolbar">催办设置</td>
						<td rowspan="2" style="text-align: center;font-weight: bold;"  class="panel-toolbar">手机设置</td>
					</tr>
					<tr>
						<td nowrap="nowrap" style="text-align: center;font-weight: bold;" class="panel-toolbar">前置</td>
						<td nowrap="nowrap" style="text-align: center;font-weight: bold;" class="panel-toolbar">后置</td>
						<td nowrap="nowrap" style="text-align: center;font-weight: bold;"class="panel-toolbar">分配</td>
					    <td nowrap="nowrap" style="text-align: center;font-weight: bold;"class="panel-toolbar">开始</br>结束</td>
					</tr>
					<tr>
						<td>-</td>
						<td>全局设置</td>
						<td><span class="normal-set">-</span></td>
						<td>
							<c:choose>
								<c:when test="${globalApprovalMap['global']}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set" >×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td>
							<c:choose>
								<c:when test="${formMap['global']}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
					</tr>
					<tr>
						<td>-</td>
						<td>开始节点</br>(${startFlowNode.nodeName})</td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td>
							<c:choose>
								<c:when test="${startScriptMap[startFlowNode.nodeId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<td><span class="normal-set">-</span></td>
						<td>
							<c:choose>
								<c:when test="${formMap['start']}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> </td>
						<td>
							<c:choose>
								<c:when test="${buttonMap[startFlowNode.nodeId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
					</tr>
					<c:forEach items="${endFlowNodeList}" var="endFlowNode">
					<tr>
						<td>-</td>
						<td>结束节点</br>(${endFlowNode.nodeName})</td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td>
							<c:choose>
								<c:when test="${endScriptMap[endFlowNode.nodeId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>	
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
						<td><span class="normal-set">-</span></td>
					</tr>
					</c:forEach>
					<c:forEach items="${nodeSetList}" var="nodeSet" varStatus="i">
					<tr>
						<td>${i.count}</td>
						<td>${nodeSet.nodeName}</br>(${nodeSet.nodeId})</td>
						
						<!-- 人员设置 -->
						<td>
							<c:choose>
								<c:when test="${nodeUserMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<!-- 常用语 -->
						<td>
							<c:choose>
								<c:when test="${taskApprovalItemsMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						
						<!-- 流程事件-前置脚本 -->
						<td>	
							<c:choose>
								<c:when test="${preScriptMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<!-- 流程事件-后置脚本 -->
						<td>	
							<c:choose>
								<c:when test="${afterScriptMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						
						<!-- 流程事件-分配脚本 -->
						<td>	
							<c:choose>
								<c:when test="${assignScriptMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<td>
							<span class="normal-set">-</span>
						</td>
						<!-- 流程规则 -->
						<td>	
							<c:choose>
								<c:when test="${nodeRulesMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						<!-- 流程表单 -->
						<td>	
							<c:choose>
								<c:when test="${bpmFormMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						
						<!-- 操作按钮-->
						<td>	
							<c:choose>
								<c:when test="${nodeButtonMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						
						<!-- 催办设置-->
						<td>	
							<c:choose>
								<c:when test="${taskReminderMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
						
						<!-- 手机设置-->
						<td>	
							<c:choose>
								<c:when test="${mobileSetMap[nodeSet.setId]}">
									<span class="green-set">√</span>
								</c:when>
								<c:otherwise>
									<span class="red-set">×</span>
								</c:otherwise>
							</c:choose> 
						</td>
					</tr>
				</c:forEach>
			</table>
			<div style="height: 40px"></div>
		</form>
			
		</div>
	</div>
	</div>
</body>
</html>