<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		enableListOrder(${formColumn.isShowInList});
	});
	
	function enableListOrder(isShow){
		if(isShow==0){
			$("#listOrderFont").hide();
			$("#listOrderId").attr("disabled","disabled");
			$("#listOrderId").removeAttr("class");
		}else if(isShow==1){
			$("#listOrderFont").show();
			$("#listOrderId").removeAttr("disabled");
			$("#listOrderId").attr("class","required number");
		}
	}
	
//-->
</script>
<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 		<tr>
 			<td width="15%" height="30" align="left" class="Input_Table_Label">所属表</td>
			<td height="30" align="left" width="35%">
				<select id="tableId" name="formColumn.tableId" style="width:175px" onchange="showColumn();">
					<c:forEach items="${tables}" var="table">
						<option value="${table.id}" <c:if test="${table.id==formColumn.tableId }">selected="selected"</c:if>>${table.tableZhName}</option>
					</c:forEach>
				</select>
			</td>
 			<td height="30" align="left" class="Input_Table_Label" width="15%">字段名</td>
 			<td height="30" align="left" width="35%">
	 				<div id="columnId">
	 				<select name="formColumn.columnId" style="width:175px">
					<c:forEach items="${columns}" var="column">
						<option value="${column.id}" <c:if test="${column.id==formColumn.columnId }">selected="selected"</c:if>>${column.columnZhName}</option>
					</c:forEach>
				</select>
				</div>
 			</td>
 		</tr>
	   	<tr id="relationId" style="display:none;">
		     <td height="30" class="Input_Table_Label"><label>关系表</label></td>
		     <td>
		     	<div id="relationTableDivId"></div>
		     	<input id="relationTableId" type="hidden" value="${formColumn.relationTable}" />
		     </td>
		     <td class="Input_Table_Label"><label>字段</label></td>
		     <td>
		     	<div id="relationColumnDivId"></div>
		     	<input id="relationColumnId" type="hidden" value="${formColumn.relationColumn}" />
		     </td>
	   	</tr>
	   	<tr>
	   		<td width="15%" height="30" align="left" class="Input_Table_Label">
				是否在列表中显示
			</td>
			<td height="30" align="left" width="35%">
				<input type="radio" name="formColumn.isShowInList" onclick="enableListOrder(1)" value="1" <c:if test="${formColumn.isShowInList==1 }">checked="checked"</c:if> />是
				<input type="radio" name="formColumn.isShowInList" onclick="enableListOrder(0)" value="0" <c:if test="${formColumn.isShowInList==0 }">checked="checked"</c:if> />否
			</td>
			<td height="30" align="left" class="Input_Table_Label" width="15%">
				列表显示顺序
			</td>
			<td height="30" align="left" width="35%" colspan="3">
				<input style="width: 170px;" type="text" id="listOrderId" name="formColumn.listOrder" value="${formColumn.listOrder}" class="required number"/><font id="listOrderFont" color="red">*</font>
			</td>
	   	</tr>
	</table>