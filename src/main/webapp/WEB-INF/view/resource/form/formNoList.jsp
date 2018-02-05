<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	function fillDiv(){
		$("#tempParam").html($("#tempForm").serialize());
		return true;
	}

	function showDetailsPage(obj){
		var saveInputType='${formColumn.inputType}';
		var currentInputType=$("#inputTypeId  option:selected").val();
		if(saveInputType==currentInputType){
			obj.href="<%=basePath %>/pages/resource/form/showDetailPage.action?type="+currentInputType+"&id="+$("#fcId").val();
		}else{
			obj.href="<%=basePath %>/pages/resource/form/showDetailPage.action?type="+currentInputType;
		}
	}
	
	$(function(){
		enableFormOrder();
	});
	
	function enableFormOrder(){
		var isEdit=$("input[name='formColumn.isEdit']:checked").val();
		var isView=$("input[name='formColumn.isView']:checked").val();
		if(isEdit==0&&isView==0){
			$("#formOrderFont").hide();
			$("#formOrderId").attr("disabled","disabled");
			$("#formOrderId").removeAttr("class");
		}else{
			$("#formOrderFont").show();
			$("#formOrderId").removeAttr("disabled");
			$("#formOrderId").attr("class","required number");
		}
	}
//-->
</script>
<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 		<tr>
 			<td width="15%" height="30" align="left" class="Input_Table_Label">
			所属表
			</td>
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
<%--		<tr id="textBoxId">--%>
<%--			<td class="Input_Table_Label" align="left">--%>
<%--				<label>选择文本框</label>--%>
<%--		    </td>--%>
<%--		    <td align="left">--%>
<%--		     	<select name="formColumn.textBoxId" style="width:175px">--%>
<%--					<c:forEach items="${textBoxs}" var="textBox">--%>
<%--						<option value="${textBox.id}" <c:if test="${formColumn.textBoxId==textBox.id }">selected="selected"</c:if>>${textBox.textBoxName}</option>--%>
<%--					</c:forEach>--%>
<%--				</select>--%>
<%--		     </td>--%>
<%--		     <td class="Input_Table_Label" align="left">--%>
<%--				<label >校验</label>--%>
<%--		     </td>--%>
<%--		     <td align="left">--%>
<%--		     	<select name="formColumn.validate" style="width:175px">--%>
<%--					<c:forEach items="${validates}" var="validate">--%>
<%--						<option value="${validate.value}" <c:if test="${formColumn.validate==validate.value }">selected="selected"</c:if>>${validate.name}</option>--%>
<%--					</c:forEach>--%>
<%--				</select>--%>
<%--		     </td>--%>
<%--		</tr>--%>
		<tr>
<%--			<td height="30" align="left" class="Input_Table_Label" width="15%">--%>
<%--				是否启用--%>
<%--			</td>--%>
<%--			<td height="30" align="left" width="35%">--%>
<%--				<input type="radio" name="formColumn.isUse" value="1" <c:if test="${formColumn.isUse==1 }">checked="checked"</c:if> />是--%>
<%--				<input type="radio" name="formColumn.isUse" value="0" <c:if test="${formColumn.isUse==0 }">checked="checked"</c:if> />否--%>
<%--			</td>--%>
			<td height="30" align="left" class="Input_Table_Label" width="15%">
				表单显示顺序
			</td>
			<td height="30" align="left" width="35%" colspan="3">
				<input style="width: 170px;" type="text" id="formOrderId" name="formColumn.formOrder" value="${formColumn.formOrder}" class="required number"/><font id="formOrderFont" color="red">*</font>
			</td>
		</tr>
		<tr>
	   		<td width="15%" height="30" align="left" class="Input_Table_Label">
				是否编辑
			</td>
			<td height="30" align="left" width="35%">
				<input type="radio" name="formColumn.isEdit" onclick="enableFormOrder()" value="1" <c:if test="${formColumn.isEdit==1 }">checked="checked"</c:if> />是
				<input type="radio" name="formColumn.isEdit" onclick="enableFormOrder()" value="0" <c:if test="${formColumn.isEdit==0 }">checked="checked"</c:if> />否
			</td>
			<td height="30" align="left" class="Input_Table_Label" width="15%">
				是否查看
			</td>
			<td height="30" align="left" width="35%" colspan="3">
				<input type="radio" name="formColumn.isView" onclick="enableFormOrder()" value="1" <c:if test="${formColumn.isView==1 }">checked="checked"</c:if> />是
				<input type="radio" name="formColumn.isView" onclick="enableFormOrder()" value="0" <c:if test="${formColumn.isView==0 }">checked="checked"</c:if> />否
			</td>
	   	</tr>
<%--		<tr id="selectDataTypeId" style="display:none;">--%>
<%--			 <td height="30" class="Input_Table_Label" width="15%" align="left"><label id="selectDataTypeLable" >数据类型</label></td>--%>
<%--		     <td width="35%" align="left">--%>
<%--		     	<select id="selectDataTypeId" name="formColumn.selectDataType" style="width:175px" onchange="selectDataType();">--%>
<%--		     		<option value="0" <c:if test="${formColumn.selectDataType == 0}">selected="selected"</c:if>>代码表</option>--%>
<%--		     		<option value="1" <c:if test="${formColumn.selectDataType == 1}">selected="selected"</c:if>>关系表</option>--%>
<%--		     	</select></td>--%>
<%--		     <td class="Input_Table_Label" width="15%" align="left"><label id="codeLabel">代码</label></td>--%>
<%--		     <td width="35%" align="left">--%>
<%--		     	<div id="codeId">--%>
<%--		     		<input id="parentName" class="textInput" type="text" name="formColumn.codeParentName" value="${formColumn.codeParentName}" readonly="true" onclick="showCodeDialog();" />--%>
<%--					<input id="parentId" name="formColumn.codeParentId"	value="${formColumn.codeParentId}" type="hidden" />--%>
<%--					<!-- <span class="star">*</span> -->--%>
<%--					<a onclick="showCodeDialog();"><span>选择</span></a>--%>
<%--				</div>--%>
<%--		     </td>--%>
<%--	   	</tr>--%>
<%--	   	<tr id="relationId" style="display:none;">--%>
<%--		     <td height="30" class="Input_Table_Label"><label>关系表</label></td>--%>
<%--		     <td>--%>
<%--		     	<div id="relationTableDivId"></div>--%>
<%--		     	<input id="relationTableId" type="hidden" value="${formColumn.relationTable}" />--%>
<%--		     </td>--%>
<%--		     <td class="Input_Table_Label"><label>字段</label></td>--%>
<%--		     <td>--%>
<%--		     	<div id="relationColumnDivId"></div>--%>
<%--		     	<input id="relationColumnId" type="hidden" value="${formColumn.relationColumn}" />--%>
<%--		     </td>--%>
<%--	   	</tr>--%>
<%--	   	<tr id="defaultvalueId">--%>
<%--			<td height="30" class="Input_Table_Label" width="15%" align="left">默认值</td>--%>
<%--			<td colspan="3" height="30" align="left" width="35%">--%>
<%--				<input style="width: 170px;" type="text" id="defaultValue" name="formColumn.defaultValue" value="${formColumn.defaultValue}">--%>
<%--			</td>--%>
<%--		</tr>--%>
		<tr>
			<td height="30" align="left" class="Input_Table_Label" width="15%">
				录入类型
			</td>
			<td height="30" align="left" colspan="3">
				<table cellspacing="0" cellpadding="0" border="0" align="left" >
					<tr>
						<td style="border: none;">
							<select id="inputTypeId" name="formColumn.inputType" style="width:175px;float:left;">
								<c:forEach items="${inputTypeList}" var="inputType">
									<option value="${inputType.value}" <c:if test="${formColumn.inputType==inputType.value }">selected="selected"</c:if>>${inputType.name}</option>
								</c:forEach>
							</select>
						</td>
						<td style="border: none;">
							<a class="button" onclick="showDetailsPage(this)" target="dialog" mask="true" drawable="false" close="fillDiv" rel="formColumnDetailContentDiv"><span>详细设置</span></a>
						</td>
					</tr>
				</table>
			</td>
<%--			<td class="Input_Table_Label" align="left">--%>
<%--				<label id="requiredLabel">是否必填</label>--%>
<%--				<label id="comBoxLabel" style="display:none;">选择下拉框</label>--%>
<%--				<label id="textFieldLabel" style="display:none;">选择文本域</label>--%>
<%--				<label id="dateFieldLabel" style="display:none;">日期组件</label>--%>
<%--				<label id="radioLabel" style="display:none;">选择单选框</label>--%>
<%--				<label id="checkBoxLabel" style="display:none;">选择复选框</label>--%>
<%--				<label id="searchComBoxLabel" style="display:none;">选择搜索下拉框</label>--%>
<%--				<label id="treeLabel" style="display:none;">选择树</label>--%>
<%--				<label id="editorLabel" style="display:none;">选择文本编辑器</label>--%>
<%--			</td>--%>
<%--		    <td align="left">--%>
<%--		    	<span id="requiredValue">--%>
<%--		    		<input type="radio" name="formColumn.required" value="1" <c:if test="${formColumn.required==1 }">checked="checked"</c:if>/>是--%>
<%--		    		<input type="radio" name="formColumn.required" value="0" <c:if test="${formColumn.required==0 }">checked="checked"</c:if>/>否--%>
<%--		    	</span>--%>
<%--				<span id="comBoxId"/>--%>
<%--				<input id="comBoxValueId" type="hidden" value="${formColumn.comBoxId}">--%>
<%--				<span id="textFieldId"/>--%>
<%--				<input id="textFieldValueId" type="hidden" value="${formColumn.textFieldId}">--%>
<%--				<span id="dateDivId">--%>
<%--					--%>
<%--				</span>--%>
<%--				<input id="dateId" type="hidden" value="${formColumn.dateId}"/>--%>
<%--				--%>
<%--				<span id="radioDivId"/>--%>
<%--				<input id="radioId" type="hidden" value="${formColumn.radioId}"/>--%>
<%--				--%>
<%--				<span id="checkBoxDivId"/>--%>
<%--				<input id="checkBoxId" type="hidden" value="${formColumn.checkBoxId}"/>--%>
<%--				--%>
<%--				<span id="searchComBoxDivId"/>--%>
<%--				<input id="searchComBoxId" type="hidden" value="${formColumn.searchComBoxId}">--%>
<%--				--%>
<%--				<span id="treeDivId"/>--%>
<%--				<input id="treeId" type="hidden" value="${formColumn.treeId}">--%>
<%--				--%>
<%--				<span id="editorDivId"/>--%>
<%--				<input id="editorId" type="hidden" value="${formColumn.editorId}">--%>
<%--		     </td>--%>
		</tr>
	</table>
	<div id="tempParam" style="display: none;"></div>
	<div id="formColumnDetailContentDiv"></div>