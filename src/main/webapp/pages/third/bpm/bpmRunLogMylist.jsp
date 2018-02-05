
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的流程运行日志管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">我的流程运行日志管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">							
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>							
				</div>	
			</div>
			<div class="panel-search">
					<form id="searchForm" method="post" action="mylist.ht">
							<ul class="row">
							<li>
								<span class="label">流程标题:</span><input type="text" name="Q_processSubject_SL"  class="inputText"  value="${param['Q_processSubject_SL']}"/>
							</li>
							<li>
								<span class="label">操作类型:</span>
								<select name="Q_operatortype_S" class="select" value="${param['Q_operatortype_S']}">
									<option value="">--全部--</option>
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
							</li>
							<div class="row_date">
							<li>
								<span class="label">操作时间 从:</span> <input  name="Q_begincreatetime_DL"  class="inputText date" value="${param['Q_begincreatetime_DL']}"/>
							</li>
							<li>
								<span class="label">至: </span><input  name="Q_endcreatetime_DG" class="inputText date"  value="${param['Q_endcreatetime_DG']}"/>										
							</li>
							</div>
							</ul>
					</form>
			</div>		
		</div>
		</div>
		<div class="panel-body">
				<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="bpmRunLogList" id="bpmRunLogItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${bpmRunLogItem.id}">
					</display:column>
					<display:column property="processSubject" title="流程标题" style="width:150px;" sortable="true" sortName="processSubject" maxLength="15"></display:column>
					<display:column property="username" title="用户名称" style="width:150px;" sortable="true" sortName="username"></display:column>
					<display:column  title="操作时间" sortable="true" style="width:170px;" sortName="createtime">
						<fmt:formatDate value="${bpmRunLogItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="操作类型" sortable="true" style="width:150px;" sortName="operatortype">
						<c:choose>
							<c:when test="${bpmRunLogItem.operatortype eq 0}"><span class="green">启动流程</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 1}"><span class="green">交办</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 2}"><span class="red">追回</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 3}"><span class="red">删除流程实例</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 4}"><span class="green">同意(投票)</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 5}"><span class="red">反对(投票)</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 6}"><span class="green">弃权(投票)</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 7}"><span class="green">补签</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 8}"><span class="red">驳回</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 9}"><span class="red">驳回到发起人</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 10}"><span class="red">删除任务</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 11}"><span class="green">代理任务</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 13}"><span class="green">锁定任务</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 14}"><span class="green">任务解锁</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 15}"><span class="green">添加意见</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 16}"><span class="green">任务指派</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 17}"><span class="green">设定所有人</span></c:when>
							<c:when test="${bpmRunLogItem.operatortype eq 18}"><span class="green">结束任务</span></c:when>
							<c:otherwise><span class="red">其他</span></c:otherwise>
						</c:choose>
					</display:column>
					<display:column property="memo" title="备注" style="width:270px;" sortable="true" sortName="memo" maxLength="30"></display:column>
					<display:column title="管理" media="html" style="width:80px">																
						<a href="get.ht?id=${bpmRunLogItem.id}" class="link detail">明细</a>
					</display:column>
				</display:table>
				<hotent:paging tableId="bpmRunLogItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


