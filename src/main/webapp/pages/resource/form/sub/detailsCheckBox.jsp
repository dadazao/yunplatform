<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
$(function(){
	selectDataType();
});
//-->
</script>
<div id="compId">
<form id="tempForm" method="post" action="<%=basePath %>/pages/resource/formsaveColumnDetails.action" class="pageForm required-validate" onsubmit="return validateCallback(this, detailDialogAjaxDone);">
<input type="hidden" name="formId" value="${formId}" />
<input type="hidden" name="formColumn.id" value="${formColumn.id }"/>
<input type="hidden" name="currentInputType" value="${currentInputType}">
<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
	<tr>
		<td class="Input_Table_Label" align="center" colspan="4">
			<label style="font-weight: bold;font-size: 12px;">
				复选框属性
			</label>
		</td>
	</tr>
	<tr>
		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				选择复选框
			</label>
		</td>
		<td align="left" colspan="3">
			<select name="formColumn.compexId" style="width:175px">
				<c:forEach items="${checkBoxes }" var="checkBox">
					<option value="${checkBox.id}" <c:if test="${formColumn.compexId == checkBox.id}">selected="selected"</c:if>>${checkBox.tbl_compname}</option>				
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr id="selectDataTypeId">
			 <td height="30" class="Input_Table_Label" width="15%" align="left"><label id="selectDataTypeLable" >数据类型</label></td>
		     <td width="35%" align="left">
		     	<select id="selectDataTypeId" name="formColumn.selectDataType" style="width:175px" onchange="selectDataType();">
		     		<option value="0" <c:if test="${formColumn.selectDataType == 0}">selected="selected"</c:if>>代码表</option>
<%--		     		<option value="1" <c:if test="${formColumn.selectDataType == 1}">selected="selected"</c:if>>关系表</option>--%>
		     	</select></td>
		     <td class="Input_Table_Label" width="12%" align="left"><label id="codeLabel">代码</label></td>
		     <td width="38%" align="left" height="30">
		     	<div id="codeId" >
		     		<input style="float:left;" id="parentName" class="textInput" type="text" name="formColumn.codeParentName" value="${formColumn.codeParentName}" readonly="true" onclick="showCodeDialog();" />
					<input id="parentId" name="formColumn.codeParentId"	value="${formColumn.codeParentId}" type="hidden" />
					<font style="float:left;" color="red">*</font>
					<span style="float:left;">&nbsp;&nbsp;</span>
					<button type="button" style="width:44px;height:20px;" class="listbutton" onclick="showCodeDialog();">选择</button>
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
</table>
<br/>
<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
	<tr>
		<td class="Input_Table_Label" align="center" colspan="4">
			<label style="font-weight: bold;font-size: 12px;">
				公共属性
			</label>
		</td>
	</tr>
	<tr>
		<td class="Input_Table_Label" align="left" width="15%">
			<label>
				高级查询
			</label>
		</td>
		<td width="35%">
			<input type="radio" name="formColumn.isQuery" value="1" <c:if test="${formColumn.isQuery==1 }">checked="checked"</c:if> />是
			<input type="radio" name="formColumn.isQuery" value="0" <c:if test="${formColumn.isQuery==0 }">checked="checked"</c:if> />否
		</td>
		<td class="Input_Table_Label" align="left" width="15%">
			<label>
				支持排序
			</label>
		</td>
		<td width="35%" colspan="3">
			<input type="radio" name="formColumn.supportOrder" value="1" <c:if test="${formColumn.supportOrder==1 }">checked="checked"</c:if> />是
			<input type="radio" name="formColumn.supportOrder" value="0" <c:if test="${formColumn.supportOrder==0 }">checked="checked"</c:if> />否
		</td>
	</tr>
</table>
<table width="100%" border="0">
	<tr>
		<td align="right">
			<c:if test="${op!='view'}">
				<button type="submit" class="listbutton" value="确定">
					确定
				</button>
				&nbsp;
				<button type="button" class="listbutton" value="取消" onclick="closeDetailWin()">
					取消
				</button>
			</c:if>
		</td>
	</tr>
</table>
</form>
</div>