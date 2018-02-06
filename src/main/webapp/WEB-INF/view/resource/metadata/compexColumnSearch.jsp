<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form method="post" action="<c:url value='/pages/resource/column/list.action'/>" onsubmit="return navTabSearch(this);">
	<div align="center">
 	 <table width="98%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		<tr>
			<td align="left" class="Input_Table_Label" width="20%">
				所属表
			</td>
			<td align="left" width="30%">
				<select name="column.tableId" style="width: 135px;">
					<option value="-1">全部</option>
					<c:forEach items="${tables}" var="table">
						<option value="${table.id}">${table.tableZhName}</option>
					</c:forEach>
				</select>
			</td>
			<td align="left" class="Input_Table_Label" width="20%">
				字段名称
			</td>
			<td align="left" width="30%">
				<input id="columnZhName" type="text" name="column.columnZhName" value="${column.columnZhName}" />
			</td>
		</tr>
		<tr>
			<td height="30" align="left" class="Input_Table_Label">
				拼音名称
			</td>
			<td align="left">
				<input id="pinyinId" type="text" name="column.columnName" value="${column.columnName}" />
			</td>
			<td height="30" align="left" class="Input_Table_Label">
				数据类型
			</td>
			<td align="left">
				<select name="column.dataType" style="width: 135px;">
					<option value="-1">全部</option>
					<option value="varchar" >varchar</option>
					<option value="int" >int</option>
					<option value="bigint" >bigint</option>
					<option value="timestamp" >timestamp</option>
					<option value="date" >date</option>
				</select>
			</td>
			
		</tr>
		<tr>
			<td height="30" align="left" class="Input_Table_Label">
				数据长度
			</td>
			<td align="left">
				<input type="text" name="column.length" value="${column.length}" />
			</td>
			<td align="left" class="Input_Table_Label">
				默认值
			</td>
			<td align="left">
				<input type="text" name="column.defaultValue" value="${column.defaultValue}" />
			</td>
		</tr>
<%--		<tr>--%>
<%--			<td height="30" align="left" class="Input_Table_Label">--%>
<%--				显示规则--%>
<%--			</td>--%>
<%--			<td align="left">--%>
<%--				<input type="text" name="column.showRule" value="${olumn.showRule}" />--%>
<%--			</td>--%>
<%--			<td align="left" class="Input_Table_Label">验证值</td>--%>
<%--			<td align="left">--%>
<%--				<input type="text" name="column.checkValue" value="${column.checkValue}" />--%>
<%--			</td>--%>
<%--		</tr>--%>
		<tr>
			<td align="left" class="Input_Table_Label">
				是否空值
			</td>
			<td align="left">
				<select name="column.isNullable" style="width: 135px;">
					<option value="-1">全部</option>
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
			</td>
			<td align="left" class="Input_Table_Label">
				功能说明
			</td>
			<td align="left">
				<input type="text" name="column.comment" value="${column.comment}" />
			</td>
		</tr>
		</table>
		<br><br>
		<div>
			<button type="submit" class="listbutton">查询</button>
			<button type="button" class="listbutton close">取消</button>
		</div>
	</div>
</form>
