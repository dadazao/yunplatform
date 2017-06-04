<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=basePath %>/js/bpm/FlowUtil.js" ></script>
<script type="text/javascript">
<!--
	ns.bpm.onlineDesign = function(id){
		window.open('<%=basePath %>/pages/third/bpm/bpmDefinition/design.action?definitionId=' + id);
	}
	
	ns.bpm.deleteDefinition = function() {
		var urlString = "<%=basePath %>/pages/third/bpm/bpmDefinition/del.action";
		var param = $("#tableForm").serialize();
		var result = urlString + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
	}
	
	ns.bpm.deleteSingleDefinition = function(id) {
		var urlString = "<%=basePath %>/pages/third/bpm/bpmDefinition/del.action?definitionId=" + id;
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(urlString,refreshList);}});
	}
	
	ns.bpm.deleteHistoryDefinition = function(id,isOnlyVersion) {
		var urlString = "<%=basePath %>/pages/third/bpm/bpmDefinition/del.action?definitionId=" + id;
		if(isOnlyVersion == true) {
			urlString+="&isOnlyVersion=true";
		}
		var freshVersionsList = function(){
			$("#versionsLi").click();
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(urlString,freshVersionsList);}});
	}
	
	ns.bpm.detailDefinition = function(id){
		var url = "<%=basePath %>/pages/third/bpm/bpmDefinition/detail.action?definitionId=" + id;
		$.pdialog.open(url,"detailDialog", "详细设置", {
			width : 950,
			height : 650,
			mask : true,
			resizable : false
		});
	}
	
	ns.bpm.deployDefinition = function(id){
		var urlString = "<%=basePath %>/pages/third/bpm/bpmDefinition/deploy.action?definitionId=" + id;
		alertMsg.confirm("确定要发布流程吗?", {okCall:function(){ajaxTodo(urlString,refreshList);}});
	}
	
	ns.bpm.exportXml = function(){
		var url=__basePath + '/pages/third/bpm/bpmDefinition/conditionEdit.action';
		
		$.pdialog.open(url,"conditionEditDialog","流程节点人员规则设置",{width:900,height:640,mask:true,resizable:true});
	}
	
	function FlowRightDialog(id, type, defKey, isParent) {
		var url = __basePath + "/pages/third/bpm/bpmDefRight/list.action?id=" + id + "&type=" + type;
		if (typeof defKey != "undefined" && defKey != "") {
			url += "&defKey=" + defKey;
		}
		if (typeof isParent != "undefined") {
			url += "&isParent=" + isParent;
		}
		
		$.pdialog.open(url,"bpmDefRightDialog","流程权限设置",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function() {
		//判断是否显示高级查询
		ns.common.showQuery('${queryType}')
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/third/bpm/bpmDefinition/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />
		<input type="hidden" name="bpmDefinition.subject" value="${bpmDefinition.subject }" />		
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="onlineDesign" name="onlineDesign" class="listbutton" onclick="ns.bpm.onlineDesign(0);" style="width:100px;">在线流程设计</button>
					<button type="button" id="deleteDefinition" name="deleteDefinition" class="listbutton" onclick="ns.bpm.deleteDefinition();" >删除</button>				
					<button type="button" id="deleteDefinition" name="deleteDefinition" class="listbutton" onclick="refresh();" >刷新</button>
<!-- 					<button type="button" id="importXml" name="importXml" class="listbutton" onclick="ns.bpm.importXml();" >导入</button> -->
<!-- 					<button type="button" id="exportXml" name="exportXml" class="listbutton" onclick="ns.bpm.exportXml();" >导出</button> -->
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/third/bpm/bpmDefinition/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>
							标题
						</td>
						<td class="queryTd">
							<input name="bpmDefinition.subject" value="${bpmDefinition.subject}"/>
						</td>
						<td>
							<button type="button" class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button>
							<button type="button" class="listbutton" onclick="ns.common.showQuery(1);">高级查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="advanceQuery" align="center" class="pageHeader" style="display:none;">
		<fieldset class="queryFieldset" >
			<legend style="border:0px;">查询项</legend>
			<form id="advanceQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/third/bpm/bpmDefinition/list.action?queryType=1" method="post">
				<table width="98%"  class="queryTable" >
					<tr>
						<td class="advanceTd" width="5%">
							标题
						</td>
						<td class="advanceTd" width="15%">
							<input name="bpmDefinition.subject" value="${bpmDefinition.subject}"/>
						</td>
						<td class="advanceTd" width="5%">
							流程Key
						</td>
						<td class="advanceTd" width="15%">
							<input name="bpmDefinition.defKey" value="${bpmDefinition.defKey}" />
						</td>
						<td class="advanceTd" width="5%">
							版本号
						</td>
						<td class="advanceTd" width="15%">
							<select name="bpmDefinition.versionNo" class="inputText" style="width:100px;">
								<option value="-1">全部</option>
								<option <c:if test="${bpmDefinition.versionNo==1 }">selected</c:if> value="1">1</option>
								<option <c:if test="${bpmDefinition.versionNo==2 }">selected</c:if> value="2">2</option>
								<option <c:if test="${bpmDefinition.versionNo==3 }">selected</c:if> value="3">3</option>
								<option <c:if test="${bpmDefinition.versionNo==4 }">selected</c:if> value="4">4</option>
								<option <c:if test="${bpmDefinition.versionNo==5 }">selected</c:if> value="5">5</option>
								<option <c:if test="${bpmDefinition.versionNo==6 }">selected</c:if> value="6">6</option>
								<option <c:if test="${bpmDefinition.versionNo==7 }">selected</c:if> value="7">7</option>
								<option <c:if test="${bpmDefinition.versionNo==8 }">selected</c:if> value="8">8</option>
								<option <c:if test="${bpmDefinition.versionNo==9 }">selected</c:if> value="9">9</option>
								<option <c:if test="${bpmDefinition.versionNo==10 }">selected</c:if> value="10">10</option>
							</select>
						</td>
						<td class="advanceTd" width="5%">
							发布状态
						</td>
						<td class="advanceTd" width="15%">
							<select name="bpmDefinition.status" class="inputText" style="width:100px;">
								<option value="-1">全部</option>
								<option <c:if test="${bpmDefinition.status==0 }">selected</c:if> value="0">未发布</option>
								<option <c:if test="${bpmDefinition.status==1 }">selected</c:if> value="1">已发布</option>
								<option <c:if test="${bpmDefinition.status==2 }">selected</c:if> value="2">禁用</option>
								<option <c:if test="${bpmDefinition.status == 4}">selected</c:if> value="4">测试</option>
								<option <c:if test="${bpmDefinition.status == 2}">selected</c:if> value="2">禁用</option>
								<option <c:if test="${bpmDefinition.status == 3}">selected</c:if> value="3">禁用实例</option>
							</select>
						</td>
						<td class="advanceTd" width="5%">
							启用状态
						</td>
						<td class="advanceTd" width="15%">
							<select name="bpmDefinition.disableStatus" class="inputText" style="width:100px;">
								<option value="-1">全部</option>
								<option <c:if test="${bpmDefinition.disableStatus==0 }">selected</c:if> value="0">启用</option>
								<option <c:if test="${bpmDefinition.disableStatus==1 }">selected</c:if> value="1">禁用</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center" style="margin-top: 5px;">
					<input type="hidden" name="listId" value="${listId}"/>
					<button type="button" class="listbutton" onclick="ns.common.showQuery(0);">隐藏</button>
					<button type="button" class="listbutton" onclick="ns.common.query('advanceQueryForm');">查询</button>
				</div>
			</form>
		</fieldset>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'selectedIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center" width="25%">标题</th>
						<th align="center">分类</th>
						<th align="center">流程Key</th>
						<th align="center">版本</th>
						<th align="center">发布状态</th>
						<th align="center">创建时间</th>
						<th align="center" width="20%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="bpmDefinition" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${bpmDefinition.defId}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${bpmDefinition.subject}</td>
		                    	<td align="center">${bpmDefinition.typeName}</td>
		                    	<td align="center">${bpmDefinition.defKey}</td>
		                    	<td align="center">${bpmDefinition.versionNo}</td>
		                    	<td align="center">
		                    		<c:choose>
										<c:when test="${bpmDefinition.status eq 0}"><span style="color: red;height:26px;line-height:26px;">未发布</span></c:when>
										<c:when test="${bpmDefinition.status eq 1}"><span style="color: green;height:26px;line-height:26px;">已发布</span></c:when>
										<c:when test="${bpmDefinition.status eq 2}"><span style="color: red;height:26px;line-height:26px;">禁用</span></c:when>
										<c:when test="${bpmDefinition.status eq 3}"><span style="color: red;height:26px;line-height:26px;">禁用(实例)</span></c:when>
										<c:when test="${bpmDefinition.status eq 4}"><span style="color: green;height:26px;line-height:26px;">测试</span></c:when>
										<c:otherwise><span style="color: red;height:26px;line-height:26px;">未选择</span></c:otherwise>
									</c:choose>
		                    	</td>
		                    	<td align="center"><fmt:formatDate value="${bpmDefinition.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.bpm.deployDefinition(${bpmDefinition.defId});">发布</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.bpm.deleteSingleDefinition(${bpmDefinition.defId});">删除</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.bpm.onlineDesign(${bpmDefinition.defId})">设计</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" rel="10000000410002" target="navTab" title="流程定义设置" href="<%=basePath %>/pages/third/bpm/bpmDefinition/detail.action?definitionId=${bpmDefinition.defId}">设置</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="FlowRightDialog(${bpmDefinition.defId},0,'${bpmDefinition.defKey}')">授权</a>
		                    		<a style="cursor: pointer;color:blue;" onclick="FlowUtil.startFlow(${bpmDefinition.defId},'${bpmDefinition.actDefId}')">启动</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>