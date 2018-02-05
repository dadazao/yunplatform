<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>ss
<div id="compId">
<form id="tempForm" method="post" action="<%=basePath %>/pages/resource/formsaveColumnDetails.action" class="pageForm required-validate" onsubmit="return validateCallback(this, detailDialogAjaxDone);">
<input type="hidden" name="formId" value="${formId}" />
<input type="hidden" name="formColumn.id" value="${formColumn.id }"/>
<input type="hidden" name="currentInputType" value="${currentInputType}">
<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
	<tr>
		<td class="Input_Table_Label" align="center" colspan="4">
			<label style="font-weight: bold;font-size: 12px;">
				日期组件属性
			</label>
		</td>
	</tr>
	<tr id="textBoxId">
		<td class="Input_Table_Label" align="left" width="15%">
			<label>
				选择日期组件
			</label>
		</td>
		<td align="left" colspan="3">
			<select name="formColumn.compexId" style="width: 175px">
				<c:forEach items="${dates}" var="date">
					<option value="${date.id}"
						<c:if test="${formColumn.compexId==date.id }">selected="selected"</c:if>>
						${date.dateName}
					</option>
				</c:forEach>
			</select>
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
		<td width="35%">
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