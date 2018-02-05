 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
$(function(){
	var defaultValue = '${tabulationQuery.columnValue}';
	if(defaultValue=='%username%'||defaultValue=='%orgname%'){
		$("#defaultsel").val(defaultValue);
	}else{
		$("#defaultsel").val('');
	}
	initDefaultValue();
	ns.common.mouseForButton();
});

function initDefaultValue(){
	var defaultValue = $("#defaultsel option:selected").val();
	if(defaultValue==''){
		if('${tabulationQuery.columnValue}'=='%username%'||'${tabulationQuery.columnValue}'=='%orgname%'){
			$("#defaultValue").val('');
		}else{
			$("#defaultValue").val('${tabulationQuery.columnValue}');
		}
		$("#defaultValue").show();
	}else{
		$("#defaultValue").val(defaultValue);
		$("#defaultValue").hide();
	}
}

//防止SQL注入
function AntiSqlValid(oField )
{
    re= /select|update|delete|exec|count|’|"|=|;|>|<|%/i;
    if (re.test(oField.value) )
    {
    	alertMsg.warn('请不要输入特殊字符和sql关键字！');
	    oField.value = "";
	    oField.focus();
	    return false;
    }
}
//-->
</script>
<form id="tabulationQueryTabulationId" name="tabulationQuery" class="pagetabulation required-validate" method="post">
	<input id="tqId" type="hidden" name="tabulationQuery.id" value="${tabulationQuery.id}" />
 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 		<tr>
 			<td width="10%" height="30" align="left" class="Input_Table_Label">
			所属表
			</td>
			<td height="30" align="left" width="40%">
				<select id="tableId" name="tabulationQuery.tableId" style="width:186px" onchange="showQueryColumn();" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
					<c:forEach items="${tables}" var="table">
						<option value="${table.id}" <c:if test="${table.id==tabulationQuery.tableId }">selected="selected"</c:if>>${table.tableZhName}</option>
					</c:forEach>
				</select>
			</td>
			<td height="30" align="left" class="Input_Table_Label" width="10%">关系</td>
 			<td height="30" align="left" width="40%">
 				<select name="tabulationQuery.relation" style="width:186px" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
 					<option value="1">and</option>
 					<option value="2">or</option>
				</select>
 			</td>
 		</tr>
 		<tr>
 			<td height="30" align="left" class="Input_Table_Label" width="10%">字段</td>
	 			<td height="30" align="left" width="40%">
	 				<div id="queryColumnId">
	 				<select name="tabulationQuery.columnId" style="width:186px" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
					<c:forEach items="${columns}" var="column">
						<option value="${column.id}" <c:if test="${column.id==tabulationQuery.columnId }">selected="selected"</c:if>>${column.columnZhName}</option>
					</c:forEach>
				</select>
				</div>
 			</td>
 			<td width="10%" height="30" align="left" class="Input_Table_Label">
			条件
			</td>
			<td height="30" align="left" width="40%">
				<select name="tabulationQuery.condition" style="width:186px" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
					<option value="1" <c:if test="${tabulationQuery.condition=='1'}">selected="selected"</c:if>>=</option>
					<option value="0" <c:if test="${tabulationQuery.condition=='0'}">selected="selected"</c:if>>!=</option>
					<option value="2" <c:if test="${tabulationQuery.condition=='2'}">selected="selected"</c:if>>></option>
					<option value="3" <c:if test="${tabulationQuery.condition=='3'}">selected="selected"</c:if>><</option>
					<option value="4" <c:if test="${tabulationQuery.condition=='4'}">selected="selected"</c:if>>like</option>
				</select>
			</td>
 			
 		</tr>
		<tr>
			<td height="30" align="left" class="Input_Table_Label" width="10%">
				字段值
			</td>
			<td height="30" align="left" width="40%">
				<select id="defaultsel" onchange="initDefaultValue()" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
					<option value="">自定义</option>
					<option value="%username%">当前用户</option>
					<option value="%orgname%">当前部门</option>
				</select>
				<input type="text" style="width: 180px;" onblur="AntiSqlValid(this)" id="defaultValue" name="tabulationQuery.columnValue" value="${tabulationQuery.columnValue}" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
			</td>
			<td height="30" align="left" class="Input_Table_Label" width="10%">
				顺序
			</td>
			<td height="30" align="left" width="40%">
				<input type="text" style="width: 180px;" name="tabulationQuery.order" maxlength="3" class="textInput required number" value="${tabulationQuery.order}"/>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="border: 0px;" align="left">
							<!-- <span class="star">*</span> -->
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div align="right" style="padding-top: 5px;padding-bottom: 5px;">
		<c:choose>
			<c:when test="${op=='view'}">
				<input type="button" name="add" class="listbuttonDisable" disabled="disabled" value="添加" onclick="addTabulationQuery();">
				<input type="button" name="save" class="listbuttonDisable" disabled="disabled" value="保存" onclick="updateTabulationQuery();">
				<input type="button" name="delete" class="listbuttonDisable" disabled="disabled" value="删除" onclick="deleteTabulationQuery();">
			</c:when>
			<c:otherwise>
				<input type="button" name="add" class="listbutton" value="添加" onclick="addTabulationQuery();">
				<input type="button" name="save" class="listbutton" value="保存" onclick="updateTabulationQuery();">
				<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteTabulationQuery();">
			</c:otherwise>
		</c:choose>
	</div>
</form>
