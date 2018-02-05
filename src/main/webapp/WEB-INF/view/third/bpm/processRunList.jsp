<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	ns.bpm.delProcessRun = function(id) {
		var url;
		if(id != undefined) {
			url = "<%=basePath %>/pages/third/bpm/processRun/del.action?selectedIDs=" + id;
		}else{
			var param = $("#tableForm").serialize();
			var items = $("input[type='checkbox']:checked").length;
			if(items == 0){
				alertMsg.warn("请选择要删除的数据!");
				return;
			}
			url = __basePath + "/pages/third/bpm/processRun/del.action?" + param;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.bpm.logProcessRun = function(id) {
		var url = "<%=basePath %>/pages/third/bpm/bpmRunLog/list.action?runId=" + id;
		navTab.openTab('10000002310000', url,{title:"流程日志"});
	}
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/third/bpm/processRun/list.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="exportXml" name="exportXml" class="listbutton" onclick="ns.bpm.delProcessRun();" >删除</button>
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/third/bpm/processRun/list.action" method="post">
				<table class="searchContent">
					<tr>
						<td>
							<span class="label">流程定义名称&nbsp;</span><input type="text" name="Q_processName_SL"  class="inputText" value="${param['Q_processName_SL']}"/>&nbsp;&nbsp;
							<span class="label">流程实例标题&nbsp;</span><input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/>&nbsp;&nbsp;
							<span class="label">创建时间从&nbsp;</span> <input  type="text" name="Q_begincreatetime_DL"  class="inputText" value="${param['Q_begincreatetime_DL']}"/>&nbsp;&nbsp;
							<span class="label">至&nbsp;</span><input  type="text" name="Q_endcreatetime_DG" class="inputText" value="${param['Q_endcreatetime_DG']}">&nbsp;&nbsp;
							<span class="label">状态&nbsp;</span>
							<select name="Q_status_SN" value="${param['Q_status_SN']}">
								<option value="">所有</option>
								<option value="1" <c:if test="${param['Q_status_SN'] == 1}">selected</c:if>>正在运行</option>
								<option value="2" <c:if test="${param['Q_status_SN'] == 2}">selected</c:if>>结束</option>
							</select>&nbsp;&nbsp;
						</td>
						<td>
							<button type="button" class="listbutton" disabled onclick="ns.common.query('defaultQueryForm');">查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
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
						<th align="center" width="25%">流程实例标题</th>
						<th align="center">流程定义名称</th>
						<th align="center">创建人</th>
						<th align="center">创建时间</th>
						<th align="center">结束时间</th>
						<th align="center">持续时间</th>
						<th align="center">状态</th>
						<th align="center" width="15%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="processRunItem" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${processRunItem.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${processRunItem.subject}</td>
		                    	<td align="center">${processRunItem.processName}</td>
		                    	<td align="center">${processRunItem.creator}</td>
		                    	<td align="center"><fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                    	<td align="center"><fmt:formatDate value="${processRunItem.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>			                    	
		                    	<td align="center">${processRunItem.duration}</td>
		                    	<td align="center">
		                    		<c:choose>
										<c:when test="${processRunItem.status==1}">
											<span style="color: green;height:26px;line-height:26px;">正在运行</span>
										</c:when>
										<c:when test="${processRunItem.status==2}">
											<span style="color: red;height:26px;line-height:26px;">结束</span>
										</c:when>
										<c:when test="${processRunItem.status==3}">
											<span style="color: brown;height:26px;line-height:26px;">人工结束</span>
										</c:when>
									</c:choose>
								</td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.bpm.delProcessRun(${processRunItem.id})"  >删除</a>&nbsp;
									<a style="cursor: pointer;color:blue;" onclick="ns.bpm.logProcessRun(${processRunItem.id})" >操作日志</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>

