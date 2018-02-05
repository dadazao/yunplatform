<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/tld/buttontag" prefix="customButtonTag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>

	<body>
		<div class="panelBar">
	<div class="pages">
		<span>显示20条，共0条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="divlist"
		totalCount="${pageResult.totalCount}"
		numPerPage="${pageResult.pageSize}"
		pageNumShown="${pageResult.pageCountShow}"
		currentPage="${pageResult.currentPage}"></div>
</div>
<table class="table" width="100%">
	<thead>
		<tr align=center>
			<th width="5%">
				<label style="float: center">
					<input type="checkbox" class="checkbox"  class="checkboxCtrl" group="checkboxID"
						onclick="selectAll(this,'checkboxAndGroupIDs')" />
			</th>
			<th width="5%">
				序号
			</th>
			<th width="50%">
				使用位置
			</th>
			<th width="30%">
				使用时间
			</th>
			<th width="10%">
				操作
			</th>
		</tr>
	</thead>
</table>
