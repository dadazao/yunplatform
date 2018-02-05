<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript" charset="utf-8">
	$(function(){
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		$(".tabsContent").height(getContentHeight()-100);
	});
	
	ns.bpm.nodeSummary = function(id) {
		$("#nodeSummaryDiv").load("<%=basePath %>/pages/third/bpm/bpmDefinition/nodeSummary.action?definitionId=" + id);
	}
	ns.bpm.nodeSet = function(id) {
		$("#nodeSetDiv").load("<%=basePath %>/pages/third/bpm/bpmDefinition/nodeSet.action?definitionId=" + id);
	}
	ns.bpm.userSet = function(id) {
		$("#userSetDiv").load("<%=basePath %>/pages/third/bpm/bpmDefinition/userSet.action?definitionId=" + id);
	}
	ns.bpm.formSet = function(id) {
		$("#formSetDiv").load("<%=basePath %>/pages/third/bpm/bpmNodeSet/list.action?definitionId=" + id);
	}
	ns.bpm.formButtonSet = function(id) {
		$("#formButtonSetDiv").load("<%=basePath %>/pages/third/bpm/bpmNodeButton/list.action?definitionId=" + id);
	}
	ns.bpm.instance = function(id) {
		$("#instanceDiv").loadUrl("<%=basePath %>/pages/third/bpm/bpmDefinition/instance.action?definitionId=" + id);
	}
	ns.bpm.versions = function(id) {
		$("#versionsDiv").loadUrl("<%=basePath %>/pages/third/bpm/bpmDefinition/versions.action?definitionId=" + id);
	}
	ns.bpm.param = function(id) {
		$("#paramDiv").loadUrl("<%=basePath %>/pages/third/bpm/bpmDefinition/otherParam.action?definitionId=" + id);
	}
</script>
</head>
<body>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
<!-- 					<button type="button" name="bpmnxml" class="listbutton" style="width:70px;" onclick="ns.bpm.bpmnxml();" >BPMN-XML</button> -->
<!-- 					<button type="button" name="designxml" class="listbutton" style="width:70px;" onclick="ns.bpm.designxml();" >DESIGN-XML</button> -->
					<a rel="10000000410002" target="navTab" title="流程定义" href="<%=basePath %>/pages/third/bpm/bpmDefinition/list.action"><button type="button" class="listbutton">返回</button></a>				
				</td></tr>
			</table>
		</div>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected">
						<a><span>流程明细</span></a>
					</li>
					<li	onclick="ns.bpm.nodeSummary(${definitionId})">
						<a><span>节点概要</span></a>
					</li>
					<li	onclick="ns.bpm.nodeSet(${definitionId})">
						<a><span>节点设置</span></a>
					</li>
					<li	id="userSetLi" onclick="ns.bpm.userSet(${definitionId})">
						<a><span>人员设置</span></a>
					</li>
					<li	onclick="ns.bpm.formSet(${definitionId})">
						<a><span>表单设置</span></a>
					</li>
					<li	onclick="ns.bpm.formButtonSet(${definitionId})">
						<a><span>操作按钮</span></a>
					</li>
<!--					<li	onclick="">-->
<!--						<a><span>变量管理</span></a>-->
<!--					</li>-->
					<li	onclick="ns.bpm.instance(${definitionId})">
						<a><span>流程实例</span></a>
					</li>
					<li	id="versionsLi" onclick="ns.bpm.versions(${definitionId})">
						<a><span>历史版本</span></a>
					</li>
					<li	onclick="ns.bpm.param(${definitionId})">
						<a><span>其他参数</span></a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div >
				<table width="100%" cellpadding="0" cellspacing="0" border="0" class="Input_Table">
					<tr>
						<td width="20%" align="right" class="Input_Table_Label" >
							分类
						</td>
						<td>
							${bpmDefinition.typeName}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							流程标题:
						</td>
						<td>
							${bpmDefinition.subject}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							流程定义Key:
						</td>
						<td>
							${bpmDefinition.defKey}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							任务标题生成规则:
						</td>
						<td>
							${bpmDefinition.taskNameRule}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							流程描述:
						</td>
						<td>
							${bpmDefinition.descp}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							创建人:
						</td>
						<td>
							<f:userName userId="${bpmDefinition.createBy}" />
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							创建时间:
						</td>
						<td>
							<fmt:formatDate value="${bpmDefinition.createtime}"	pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							更新人:
						</td>
						<td>
							<f:userName userId="${bpmDefinition.updateBy}" />
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							更新原因:
						</td>
						<td>
							${bpmDefinition.reason}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							更新时间:
						</td>
						<td>
							<fmt:formatDate value="${bpmDefinition.updatetime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							流程状态:
						</td>
						<td>
							<c:choose>
								<c:when test="${bpmDefinition.status==1}">
									<font color='green'>启用</font>
								</c:when>
								<c:when test="${bpmDefinition.status==2}">
									<font color='red'>禁用</font>
								</c:when>								
								<c:when test="${bpmDefinition.status==3}">
									<font color='red'>禁用(实例)</font>
								</c:when>
								<c:when test="${bpmDefinition.status==4}">
									<font color='green'>测试</font>
								</c:when>
								<c:otherwise>
									<font color='red'>未知</font>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							版本号:
						</td>
						<td>
							${bpmDefinition.versionNo}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							activiti流程定义ID:
						</td>
						<td>
							${bpmDefinition.actDefId}
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="Input_Table_Label">
							act流程定义Key:
						</td>
						<td>
							${bpmDefinition.actDefKey}
						</td>
					</tr>
				</table>
			</div>
			<div id="nodeSummaryDiv" style="background-color: #fff;"></div>
			<div id="nodeSetDiv" style="background-color: #fff;"></div>
			<div id="userSetDiv" style="background-color: #fff;"></div>
			<div id="formSetDiv" style="background-color: #fff;"></div>
			<div id="formButtonSetDiv" style="background-color: #fff;"></div>
			<div id="instanceDiv" style="background-color: #fff;"></div>
			<div id="versionsDiv" style="background-color: #fff;"></div>
			<div id="paramDiv" style="background-color: #fff;"></div>
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</body>
</html>
