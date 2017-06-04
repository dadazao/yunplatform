 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="tabulationDetailsTabulationId" name="tabulationDetails" class="pagetabulation required-validate" method="post">
		<input id="fdId" type="hidden" name="tabulation.id" value="${tabulation.id}" />
 	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 	 		<tr>
  	 			<td width="15%" height="30" align="left" class="Input_Table_Label">
					是否显示【选择】列
				</td>
				<td height="30" align="left" width="35%">
					<input type="radio" name="tabulation.isSelect" value="1" <c:if test="${tabulation.isSelect=='1' }">checked="checked"</c:if> />是
					<input type="radio" name="tabulation.isSelect" value="0" <c:if test="${tabulation.isSelect=='0' }">checked="checked"</c:if> />否
				</td>
				<td width="15%" height="30" align="left" class="Input_Table_Label">
					是否显示【序号】列
				</td>
				<td height="30" align="left" width="35%">
					<input type="radio" name="tabulation.isNumber" value="1" <c:if test="${tabulation.isNumber=='1' }">checked="checked"</c:if> />是
					<input type="radio" name="tabulation.isNumber" value="0" <c:if test="${tabulation.isNumber=='0' }">checked="checked"</c:if> />否
				</td>
				
  	 		</tr>
  	 		<tr>
  	 			
				<td width="15%" height="30" align="left" class="Input_Table_Label">
					是否显示【操作】列
				</td>
				<td height="30" align="left" width="35%">
					<input type="radio" name="tabulation.isModify" value="1" <c:if test="${tabulation.isModify=='1' }">checked="checked"</c:if> />是
					<input type="radio" name="tabulation.isModify" value="0" <c:if test="${tabulation.isModify=='0' }">checked="checked"</c:if> />否
				</td>
<%--				<td width="15%" height="30" align="left" class="Input_Table_Label">--%>
<%--					【操作】列按钮名称--%>
<%--				</td>--%>
<%--				<td height="30" align="left" width="35%">--%>
<%--					<input class="textInput" type="text" name="tabulation.modifyName" value='${tabulation.modifyName==undefined?"维护":tabulation.modifyName}'/>--%>
<%--				</td>--%>
				<td width="15%" height="30" align="left" class="Input_Table_Label">
					列表组件
				</td>
				<td height="30" align="left" width="35%">
					<select name="tabulation.listControlId" style="width:135px">
						<c:forEach items="${listControls}" var="listControl">
							<option value="${listControl.id}" <c:if test="${listControl.id==tabulation.listControlId }">selected="selected"</c:if>>${listControl.listControlName}</option>
						</c:forEach>
					</select>
				</td>
  	 		</tr>
	</table>
	<div align="right">
		<input type="button" name="save" class="listbutton" value="保存" onclick="updateTabulationDetails();">
	</div>
</form>
