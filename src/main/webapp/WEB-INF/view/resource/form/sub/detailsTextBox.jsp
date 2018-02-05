<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
$(function(){
	var defaultValue = '${formColumn.defaultValue}';
	if(defaultValue=='%username%'||defaultValue=='%orgname%'){
		$("#defaultsel").val(defaultValue);
	}else{
		$("#defaultsel").val('');
	}
	initDefaultValue();
});

function initDefaultValue(){
	var defaultValue = $("#defaultsel option:selected").val();
	if(defaultValue==''){
		if('${formColumn.defaultValue}'=='%username%'||'${formColumn.defaultValue}'=='%orgname%'){
			$("#defaultValue").val('');
		}else{
			$("#defaultValue").val('${formColumn.defaultValue}');
		}
		$("#defaultValue").show();
	}else{
		$("#defaultValue").val(defaultValue);
		$("#defaultValue").hide();
	}
}
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
				文本框属性
			</label>
		</td>
	</tr>
	<tr>
		<td class="Input_Table_Label" align="left" width="15%">
			<label>
				校验方式
			</label>
		</td>
		<td align="left" width="35%">
			<select name="formColumn.validate" style="width: 175px">
				<c:forEach items="${validates}" var="validate">
					<option value="${validate.value}"
						<c:if test="${formColumn.validate==validate.value }">selected="selected"</c:if>>
						${validate.name}
					</option>
				</c:forEach>
			</select>
		</td>
		<td class="Input_Table_Label" align="left" width="12%">
			<label>
				默认值
			</label>
		</td>
		<td align="left" width="38%">
			<select id="defaultsel" onchange="initDefaultValue()">
				<option value="">自定义</option>
				<option value="%username%">当前用户</option>
				<option value="%orgname%">当前部门</option>
			</select>
			<input style="width: 120px;" type="text" id="defaultValue" name="formColumn.defaultValue" value="${formColumn.defaultValue}">
		</td>
	</tr>
	<tr id="textBoxId">
		<td class="Input_Table_Label" align="left">
			<label>
				选择文本框
			</label>
		</td>
		<td align="left">
			<select name="formColumn.compexId" style="width: 175px">
				<c:forEach items="${components}" var="textBox">
					<option value="${textBox.id}"
						<c:if test="${formColumn.compexId==textBox.id }">selected="selected"</c:if>>
						${textBox.textBoxName}
					</option>
				</c:forEach>
			</select>
		</td>
		<td class="Input_Table_Label" align="left">
			<label>
				允许重复
			</label>
		</td>
		<td>
			<input type="radio" name="formColumn.canReply" value="1" <c:if test="${formColumn.canReply==1 }">checked="checked"</c:if> />是
			<input type="radio" name="formColumn.canReply" value="0" <c:if test="${formColumn.canReply==0 }">checked="checked"</c:if> />否
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
				是否链接
			</label>
		</td>
		<td width="35%">
			<input type="radio" name="formColumn.isLinkView" value="1" <c:if test="${formColumn.isLinkView==1 }">checked="checked"</c:if> />是
			<input type="radio" name="formColumn.isLinkView" value="0" <c:if test="${formColumn.isLinkView==0 }">checked="checked"</c:if> />否
		</td>
	</tr>
	<tr>
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