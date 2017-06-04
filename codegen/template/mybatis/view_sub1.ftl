<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//--> 
</script>
	<div>
  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		<#assign index=0>
		<#list mainTable.formColumns as col>
			<#if col.view>
				<#if index%2==0>
			<tr>
				</#if>
				<td align="left" class="Input_Table_Label" width="10%">
					${col.column.chName}
				</td>
				<td align="left" width="40%">
				<#if col.inputType==4>
					<fmt:formatDate value="${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}" pattern="yyyy-MM-dd HH:mm:ss"/>
				<#else>
					${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}
				</#if>
				</td>
				<#if index%2==1>
			</tr>
				</#if>
			<#assign index=index+1>
			</#if>
		</#list>
		</table>
	</div>
