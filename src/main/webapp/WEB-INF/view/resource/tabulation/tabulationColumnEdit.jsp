<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	function showDownContent() {
		var type = $("#inputTypeId  option:selected").val();
		if(type==1) {//下拉框
			$("#selectDataTypeId").show();
			$("#selectDataTypeLfabel").show();
			$("#codeLabel").show();
			$("#codeId").show();
			$("#comBoxLabel").show();
			$("#comBoxId").show();
			$("#textBoxLabel").hide();
			$("#textBoxId").hide();
			showComBox();
		}else{
			$("#selectDataTypeId").hide();
			$("#selectDataTypeLabel").hide();
			$("#codeLabel").hide();
			$("#codeId").hide();
			$("#comBoxLabel").hide();
			$("#comBoxId").html("");
			$("#textBoxLabel").show();
			$("#textBoxId").show();
			
			$("#relationTableDivId").html("");
			$("#relationColumnDivId").html("");
		}
	}

	function selectDataType() {
		var type = $("#selectDataTypeId  option:selected").val();
		if(type==0) {
			$("#codeId").show();
			$("#codeLabel").show();
			$("#relationId").hide();
			$("#relationTableDivId").html("");
			$("#relationColumnDivId").html("");
		}else if(type ==1) {
			$("#codeId").hide();
			$("#codeLabel").hide();
			$("#relationId").show();
			$.ajaxSetup({async: false});
			showRelationTable();
			showRelationColumn();
			$.ajaxSetup({async: true});
		}
	}
	
	function showComBox() {
		var urlString = "<%=basePath %>/pages/resource/tabulationshowComBox.action?comBoxId=" + $("#comBoxValueId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#comBoxId").html(data);
			}
		});
	}
	
	function showRelationTable() {
		var urlString = "<%=basePath %>/pages/resource/tabulationshowRelationTable.action?table=" + $("#relationTableId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#relationTableDivId").html(data);
			}
		});
	}
	
	function showRelationColumn() {
		var urlString = "<%=basePath %>/pages/resource/tabulationshowRelationColumn.action?table=" + $("#relationTableSelectId option:selected").val() + "&column=" + $("#relationColumnId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#relationColumnDivId").html(data);
			}
		});
	}
	
	function showCodeDialog() {
		$.pdialog.open("<%=basePath %>/pages/resource/dictionaryTree.jsp?method=lookup", "selectDialog", "选择代码", {width:200,height:600,mask:true,resizable:true});
	}
	
	$(function(){
		showDownContent();
		selectDataType();
		ns.common.mouseForButton();
	});

//-->
</script>
<form id="tabulationColumnTabulationId" name="tabulationColumn" class="pagetabulation required-validate" method="post">
		<input id="fcId" type="hidden" name="tabulationColumn.id" value="${tabulationColumn.id}" />
 	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
				所属表：
			</td>
			<td height="30" align="left" width="40%">
				<input type="hidden" name="tabulationColumn.tableId" value="${tabulation.id}"/>
				${tabulation.tableZhName}
			</td>
 	 			<td height="30" align="left" class="Input_Table_Label" width="10%">字段名：</td>
 	 			<td height="30" align="left" width="40%">
 	 				<div id="columnId">
 	 				<select name="tabulationColumn.columnId" style="width:135px">
						<c:forEach items="${columns}" var="column">
							<option value="${column.id}" <c:if test="${column.id==tabulationColumn.columnId }">selected="selected"</c:if>>${column.columnZhName}</option>
						</c:forEach>
					</select>
					</div>
 	 			</td>
 	 		</tr>
		<tr>
			<td height="30" align="left" class="Input_Table_Label" width="10%">
				是否启用：
			</td>
			<td height="30" align="left" width="40%">
				<input type="radio" name="tabulationColumn.isUse" value="1" <c:if test="${tabulationColumn.isUse==1 }">checked="checked"</c:if> />是
				<input type="radio" name="tabulationColumn.isUse" value="0" <c:if test="${tabulationColumn.isUse==0 }">checked="checked"</c:if> />否
			</td>
			<td width="10%" height="30" align="left" class="Input_Table_Label">
				是否在列表中显示：
			</td>
			<td height="30" align="left" width="40%">
				<input type="radio" name="tabulationColumn.isShowInList" value="1" <c:if test="${tabulationColumn.isShowInList==1 }">checked="checked"</c:if> />是
				<input type="radio" name="tabulationColumn.isShowInList" value="0" <c:if test="${tabulationColumn.isShowInList==0 }">checked="checked"</c:if> />否
			</td>
		</tr>
		<tr>
			<td width="10%" height="30" align="left" class="Input_Table_Label">
				是否是查询条件：
			</td>
			<td height="30" align="left" width="40%">
				<input type="radio" name="tabulationColumn.isQuery" value="1" <c:if test="${tabulationColumn.isShowInList==1 }">checked="checked"</c:if> />是
				<input type="radio" name="tabulationColumn.isQuery" value="0" <c:if test="${tabulationColumn.isShowInList==0 }">checked="checked"</c:if> />否
			</td>
			<td width="10%" height="30" align="left" class="Input_Table_Label">
				是否是默认查询条件：
			</td>
			<td height="30" align="left" width="40%">
				<input type="radio" name="tabulationColumn.isDefaultQuery" value="1" <c:if test="${tabulationColumn.isDefaultQuery==1 }">checked="checked"</c:if> />是
				<input type="radio" name="tabulationColumn.isDefaultQuery" value="0" <c:if test="${tabulationColumn.isDefaultQuery==0 }">checked="checked"</c:if> />否
			</td>
		</tr>
		<tr>
			<td height="30" align="left" class="Input_Table_Label" width="10%">
				列表显示顺序：
			</td>
			<td height="30" align="left" width="40%" colspan="3">
				<input type="text" name="tabulationColumn.listOrder" value="${tabulationColumn.listOrder}" class="required"/><!-- <span class="star">*</span> -->
			</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" name="add" class="listbutton" value="添加" onclick="addTabulationColumn();">
		<input type="button" name="save" class="listbutton" value="保存" onclick="updateTabulationColumn();">
		<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteTabulationColumn();">
		<input type="button" name="close" class="listbutton" value="完成" onclick="completeTabulationColumn();">
	</div>
</form>
