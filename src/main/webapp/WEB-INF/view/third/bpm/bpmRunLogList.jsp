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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	ns.bpm.delRunLog = function(id) {
		var url;
		if(id != undefined) {
			url = "<%=basePath %>/pages/third/bpm/bpmRunLog/del.action?selectedIDs=" + id;
			
		}else{
			var param = $("#tableForm").serialize();
			var items = $("input[type='checkbox']:checked").length;
			if(items == 0){
				alertMsg.warn("请选择要删除的数据!");
				return;
			}
			url = __basePath + "/pages/third/bpm/bpmRunLog/del.action?" + param;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.bpm.viewRunLog = function(id) {
		var url = "<%=basePath %>/pages/third/bpm/bpmRunLog/view.action?runLogId=" + id;
		$.pdialog.open(url,"detailDialog", "详细信息", {
			width : 600,
			height : 480,
			mask : true,
			resizable : false
		});
	}
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/third/bpm/bpmRunLog/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="deleteDefinition" name="deleteDefinition" class="listbutton" onclick="ns.bpm.delRunLog();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/third/bpm/bpmRunLog/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>
							流程标题
						</td>
						<td class="queryTd">
							<input type="text" name="Q_processSubject_SL"  class="inputText"  value="${param['Q_processSubject_SL']}"/>
						</td>
						<td>
							用户
						</td>
						<td class="queryTd">
							<input type="text" name="Q_username_SL"  class="inputText"  value="${param['Q_username_SL']}"/>
						</td>
						<td>
							操作时间 从
						</td>
						<td class="queryTd">
							<input  type="text" name="Q_begincreatetime_DL"  class="inputText"  value="${param['Q_begincreatetime_DL']}"/>
							至
							</span><input  type="text" name="Q_endcreatetime_DG" class="inputText"  value="${param['Q_endcreatetime_DG']}"/>
						</td>
						<td>
							操作类型
						</td>
						<td class="queryTd">
							<select name="Q_operatortype_S" class="select">
								<option value="">全部</option>
								<option value="0" <c:if test="${param['Q_operatortype_S'] == 0}">selected</c:if>>启动流程</option>
								<option value="1" <c:if test="${param['Q_operatortype_S'] == 1}">selected</c:if>>交办</option>
								<option value="2" <c:if test="${param['Q_operatortype_S'] == 2}">selected</c:if>>追回</option>
								<option value="3" <c:if test="${param['Q_operatortype_S'] == 3}">selected</c:if>>删除流程实例</option>
								<option value="10" <c:if test="${param['Q_operatortype_S'] == 10}">selected</c:if>>删除任务</option>
								<option value="4" <c:if test="${param['Q_operatortype_S'] == 4}">selected</c:if>>同意(投票)</option>
								<option value="5" <c:if test="${param['Q_operatortype_S'] == 5}">selected</c:if>>反对(投票)</option>
								<option value="6" <c:if test="${param['Q_operatortype_S'] == 6}">selected</c:if>>弃权(投票)</option>
								<option value="7" <c:if test="${param['Q_operatortype_S'] == 7}">selected</c:if>>补签</option>
								<option value="8" <c:if test="${param['Q_operatortype_S'] == 8}">selected</c:if>>驳回</option>
								<option value="9" <c:if test="${param['Q_operatortype_S'] ==9}">selected</c:if>>驳回到发起人</option>
								<option value="11" <c:if test="${param['Q_operatortype_S'] == 11}">selected</c:if>>代理任务</option>
								<option value="13" <c:if test="${param['Q_operatortype_S'] == 13}">selected</c:if>>锁定任务</option>
								<option value="14" <c:if test="${param['Q_operatortype_S'] == 14}">selected</c:if>>任务解锁</option>
								<option value="15" <c:if test="${param['Q_operatortype_S'] == 15}">selected</c:if>>添加意见</option>
								<option value="16" <c:if test="${param['Q_operatortype_S'] == 16}">selected</c:if>>任务指派</option>
								<option value="17" <c:if test="${param['Q_operatortype_S'] == 17}">selected</c:if>>设定所有人</option>
								<option value="18" <c:if test="${param['Q_operatortype_S'] == 18}">selected</c:if>>结束任务</option>
							</select>
						</td>
						<td>
							<button type="button" class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button>
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
						<th align="center" width="25%">流程标题</th>
						<th align="center">用户</th>
						<th align="center">操作时间</th>
						<th align="center">操作类型</th>
						<th align="center" width="15%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="bpmRunLog" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${bpmRunLog.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${bpmRunLog.processSubject}</td>
		                    	<td align="center">${bpmRunLog.username}</td>
		                    	<td align="center"><fmt:formatDate value="${bpmRunLog.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                    	<td align="center">
		                    		<c:choose>
										<c:when test="${bpmRunLog.operatortype eq 0}"><span style="line-height:26px;color:green;">启动流程</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 1}"><span style="line-height:26px;color:green;">交办</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 2}"><span style="line-height:26px;color:red;">追回</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 3}"><span style="line-height:26px;color:red;">删除流程实例</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 4}"><span style="line-height:26px;color:green;">同意(投票)</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 5}"><span style="line-height:26px;color:red;">反对(投票)</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 6}"><span style="line-height:26px;color:green;">弃权(投票)</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 7}"><span style="line-height:26px;color:green;">补签</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 8}"><span style="line-height:26px;color:red;">驳回</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 9}"><span style="line-height:26px;color:red;">驳回到发起人</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 10}"><span style="line-height:26px;color:red;">删除任务</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 11}"><span style="line-height:26px;color:green;">代理任务</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 13}"><span style="line-height:26px;color:green;">锁定任务</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 14}"><span style="line-height:26px;color:green;">任务解锁</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 15}"><span style="line-height:26px;color:green;">添加意见</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 16}"><span style="line-height:26px;color:green;">任务指派</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 17}"><span style="line-height:26px;color:green;">设定所有人</span></c:when>
										<c:when test="${bpmRunLog.operatortype eq 18}"><span style="line-height:26px;color:green;">结束任务</span></c:when>
										<c:otherwise><span style="line-height:26px;color:red;">其他</span></c:otherwise>
									</c:choose>
		                    	</td>
		                    	<td align="center">
									<a onclick="ns.bpm.viewRunLog(${bpmRunLog.id})" style="cursor: pointer;color:blue;">明细</a>&nbsp;
		                    		<a onclick="ns.bpm.delRunLog(${bpmRunLog.id})" style="cursor: pointer;color:blue;">删除</a>								
		                    	</td>
		            	</tr>
		            </c:forEach>
		        </tbody>
		    </table>
		</div>		
	</form>	
</body>
</html>


