<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<tr belong="dynamic">
	<td style="width: 10%; height: 30px;" align="left"
		class="Input_Table_Label">
		&nbsp;树名称
	</td>
	<td style="width: 40%; height: 30px;" align="left" colspan="1">
		<input id="tree" type="text" name="mgrTree.treename"
			value="${mgrTree.treename}" class="required"
			style="width: 180px; height: 15px;" />
		<!-- <span class="star">*</span> -->
	</td>
	<td style="width: 10%; height: 30px;" align="left"
		class="Input_Table_Label">
		&nbsp;所属类型
	</td>
	<td align="left" style="width: 40%; height: 30px;">
		<select name="mgrTree.type"	style="width: 186px;" onchange="getColumn();">
			<c:forEach items="${treeTypes}" var="type">
				<option value="${type.value}" <c:if test="${mgrTree.type==type.value }">selected="selected"</c:if>>${type.name}</option>
			</c:forEach>
		</select>
	</td>
</tr>
<tr belong="dynamic">
	<td style="width: 10%; height: 30px;" align="left"
		class="Input_Table_Label">
		&nbsp;所属表
	</td>
	<td align="left" style="width: 40%; height: 30px;">
		<select id="tables" name="mgrTree.tableId" style="width: 186px;"
			onchange="getColumn();">
			<c:forEach items="${tables}" var="table">
				<option value="${table.id}"
					<c:if test="${mgrTree.tableId==table.id }">selected="selected"</c:if>>
					${table.tableZhName}
				</option>
			</c:forEach>
		</select>
	</td>
	<td style="width: 10%; height: 30px;" align="left"
		class="Input_Table_Label">
		&nbsp;显示字段
	</td>
	<td align="left" style="width: 40%; height: 30px;">
		<div id="nameColumnId">
			<select id="disColumnId" name="mgrTree.disColumnId"
				style="width: 186px;">
				<c:forEach items="${columnList}" var="column">
					<option value="${column.id}"
						<c:if test="${mgrTree.disColumnId==column.id}">selected="selected"</c:if>>
						${column.columnZhName}
					</option>
				</c:forEach>
			</select>
		</div>
	</td>
</tr>
<tr belong="dynamic">
	<td style="width: 10%; height: 30px;" align="left"
		class="Input_Table_Label">
		&nbsp;顺序字段
	</td>
	<td align="left" style="width: 40%; height: 30px;">
		<div id="paiXu">
			<select id="orderColumnId" name="mgrTree.orderColumnId"
				style="width: 186px;">
				<c:forEach items="${columnList}" var="column">
					<option value="${column.id}"
						<c:if test="${mgrTree.orderColumnId==column.id }">selected="selected"</c:if>>
						${column.columnZhName}
					</option>
				</c:forEach>
			</select>
		</div>
	</td>
	<td align="left" style="width: 10%; height: 30px;"
		class="Input_Table_Label">
		&nbsp;父节点字段
	</td>
	<td align="left" style="width: 40%; height: 30px;">
		<div id="parentTreeName">
			<select id="parentColumnId" name="mgrTree.parentColumnId"
				style="width: 186px;">
				<c:forEach items="${columnList}" var="column">
					<option value="${column.id}"
						<c:if test="${mgrTree.parentColumnId==column.id}">selected="selected"</c:if>>
						${column.columnZhName}
					</option>
				</c:forEach>
			</select>
		</div>
	</td>
</tr>
<tr belong="dynamic">
	<td style="width: 10%; height: 30px;" align="left"
		class="Input_Table_Label">
		&nbsp;根节点
	</td>
	<td align="left" style="width: 40%; height: 30px;" colspan="3">
		<table cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td align="left"
					style="height: 30px; background-color: #FFFFFF; border-bottom: #CECCCD 0px solid; border-right: #CECCCD 0px solid;">
					<input id="parentName" type="text" name="mgrTree.rootZhName"
						value="${mgrTree.rootZhName}" readonly="true"
						class='required' />
					<!-- <span class="star">*</span> -->
					<input id="parentId" name="mgrTree.rootId"
						value="${mgrTree.rootId}" type="hidden" />
				</td>
				<td
					style="height: 30px; background-color: #FFFFFF; border-bottom: #CECCCD 0px solid; border-right: #CECCCD 0px solid;">
					<button type="button" style="width: 44px; height: 20px;"
						class="listbutton" onclick="selectTreeRoot();">
						选择
					</button>
					</a>
				</td>
			</tr>
		</table>
	</td>
</tr>