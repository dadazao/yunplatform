<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<form method="post" action="<c:url value='/pages/resource/table/list.action'/>" onsubmit="return navTabSearch(this);">
	<div align="center">
  	 	<table width="98%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td height="30" align="left" class="Input_Table_Label" width="20%">
					表名称
				</td>
				<td height="30" align="left" width="30%">
					<input id="tableZhName" type="text" name="table.tableZhName" value="${table.tableZhName}" />
				</td>
				<td width="15%" height="30" align="left" class="Input_Table_Label">
					拼音表名
				</td>
				<td height="30" align="left">
					<input type="text" name="table.tableName" value="${table.tableName}" />
				</td>
			</tr>
			<tr>							
				<td align="left" class="Input_Table_Label">
					表编码
				</td>
				<td align="left" >
					<input type="text" name="table.tableCode" value="${table.tableCode}" />
				</td>
				<td height="30" align="left" class="Input_Table_Label" >
					所属库
				</td>
				<td align="left">
					<input type="text" name="table.tableSchema" value="${table.tableSchema}" />
				</td>
			</tr>
			<tr>
				
				<td height="30" align="left" class="Input_Table_Label">
					表主键
				</td>
				<td align="left">
					<input type="text" name="table.primaryKey" value="${table.primaryKey}" />
				</td>
				<td height="30" align="left" class="Input_Table_Label">
					表类型
				</td>
				<td align="left">
					<select name="table.tableType" style="width: 135px;">
						<option value="-1">全部</option>
						<option value="0" >基本表</option>
						<option value="1" >中间表</option>
						<option value="2" >临时表</option>
						<option value="3" >系统配置表</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label">
					表关系类型
				</td>
				<td align="left">
					<select name="table.tableRelationType" style="width: 135px;">
						<option value="-1">全部</option>
						<option value="0" >无</option>
						<option value="1" >关系映射表</option>
						<option value="2" >关系嵌套表</option>
						<option value="3" >关系主从表</option>
					</select>
				</td>
				<td height="30" align="left" class="Input_Table_Label">
					包含视图
				</td>
				<td align="left">
					<select name="table.hasView" style="width: 135px;">
						<option value="-1">全部</option>
						<option value="1">包含</option>
						<option value="0">不包含</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label">
					包含外键
				</td>
				<td align="left">
					<select name="table.hasForeignKey" style="width: 135px;">
						<option value="-1">全部</option>
						<option value="1">包含</option>
						<option value="0">不包含</option>
					</select>
				</td>
				<td align="left" class="Input_Table_Label">
					包含索引
				</td>
				<td align="left">
					<select name="table.hasIndex" style="width: 135px;">
						<option value="-1">全部</option>
						<option value="1">包含</option>
						<option value="0">不包含</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label">功能说明</td>
				<td align="left" colspan="3">
					<input type="text" name="table.tableFunction" size="70" value="${table.tableFunction}" />
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

