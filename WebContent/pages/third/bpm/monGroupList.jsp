<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>监控分组管理列表</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/MonGroupAuthDialog.js" ></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">监控分组管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>编辑</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">分组名称:</span><input type="text" name="Q_name_SL"  class="inputText" value="${param['Q_name_SL']}" />
						<span class="label">权限:</span>
						<select name="Q_grade_N">
							<option value="" >所有</option>
							<option value="1" <c:if test="${param['Q_grade_N'] == 1}">selected</c:if>>查看标题</option>
							<option value="2" <c:if test="${param['Q_grade_N'] == 2}">selected</c:if>>查看流程实例信息</option>
							<option value="3" <c:if test="${param['Q_grade_N'] == 3}">selected</c:if>>可干预</option>
						</select>
						<span class="label">是否禁用:</span>
						<select name="Q_enabled_N">
							<option value="" >全选</option>
							<option value="1" <c:if test="${param['Q_enabled_N'] == 1}">selected</c:if>>启用</option>
							<option value="0" <c:if test="${param['Q_enabled_N'] == 0}">selected</c:if>>禁用</option>
						</select>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="monGroupList" id="monGroupItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${monGroupItem.id}">
				</display:column>
				<display:column property="name" title="分组名称" sortable="true" sortName="name"></display:column>
				<display:column  title="权限" sortable="true" sortName="grade">
					<c:choose>
						<c:when test="${monGroupItem.grade==1 }">查看标题</c:when>
						<c:when test="${monGroupItem.grade==2 }">查看流程实例明细</c:when>
						<c:when test="${monGroupItem.grade==3 }">可干预</c:when>
					</c:choose>
				</display:column>
				<display:column  title="是否禁用" sortable="true" sortName="enabled">
					<c:choose>
						<c:when test="${monGroupItem.enabled==1 }">启用</c:when>
						<c:when test="${monGroupItem.enabled==0 }">禁用</c:when>
					</c:choose>
				</display:column>
				<display:column property="creator" title="创建人" sortable="true" sortName="creator">
					<a href="${ctx }/platform/system/sysUser/get.ht?id=${ monGroupItem.creatorid}">${monGroupItem.creator }</a>
				</display:column>
				<display:column  title="创建时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${monGroupItem.createtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="line-height:21px;width:100px;">
					<a href="del.ht?id=${monGroupItem.id}" class="link del" >删除</a>
					<a href="edit.ht?id=${monGroupItem.id}" class="link edit" title="编辑">编辑</a>
					<a href="javascript:MonGroupAuthDialog({groupId:${monGroupItem.id}});" class="link auth" >授权</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="monGroupItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


