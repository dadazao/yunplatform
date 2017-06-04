<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	ns.${mainTable.parentClassName?uncap_first}.save${mainTable.variables.class} = function(){
		var domainId = $('#domainId').val();
		if(${mainTable.parentClassName?uncap_first}Id){
			var $form = $("#${mainTable.variables.class?uncap_first}Form");
			var url ='<%=basePath %>${mainTable.namespace}/${mainTable.variables.class?uncap_first}/save.action?${mainTable.parentClassName?uncap_first}Id='+domainId;
			$form.attr('action',url);
			$form.submit();
		}else{
			alertMsg.warn("请先保存主记录!");
		}
	}
//-->
</script>
<div>
	<form id="${mainTable.variables.class?uncap_first}Form" method="post" action="<%=basePath %>${mainTable.namespace}/${mainTable.variables.class?uncap_first}/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSingleDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
		<input id="${mainTable.variables.class?uncap_first}Id" type=hidden name="${mainTable.variables.class?uncap_first}.id" value="${'${'}${mainTable.variables.class?uncap_first}.id${'}'}"/>
		<input id="${mainTable.variables.class?uncap_first}OptCounter" type=hidden name="${mainTable.variables.class?uncap_first}.optCounter" value="${'${'}${mainTable.variables.class?uncap_first}.optCounter${'}'}"/>
  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		<#assign index = 0>
		<#list mainTable.formColumns as col>
			<#if col.edit>
			<#if index%2==0>
			<tr>
			</#if>
				<td align="left" class="Input_Table_Label" width="10%">
					${col.column.chName}
				</td>
				<#switch col.inputType>
					<#case 1>
				<td align="left" width="40%">
 		 			<input type="text" name="${mainTable.variables.class?uncap_first}.${col.column.colName}" value="${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}"  class="textInput <#if col.notNull>required</#if>" style="width:180px;"/>
				</td>
					<#break>
					<#case 2>
				<td align="left" width="40%">
					<select style="width:186px" name="${mainTable.variables.class?uncap_first}.${col.column.colName}" class="valid">
							<option value="-1">请选择</option>
					</select>
				</td>
					<#break>
					<#case 3>
						<#if col_index%2==0>
				<td colspan="3" align="left" with="90%">
		 			<textarea name="${mainTable.variables.class?uncap_first}.${col.column.colName}" class="textInput <#if col.notNull>required</#if>" cols="80" rows="5">${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}</textarea>
				</td>
						<#else>
				<td align="left" width="40%"></td>
						</#if>
					<#break>
					<#case 4>
				<td align="left" width="40%">
				<input readonly="true" type="text" id="${mainTable.variables.class?uncap_first}.${col.column.colName}" name="${mainTable.variables.class?uncap_first}.${col.column.colName}" value='<fmt:formatDate value="${'${'}${mainTable.variables.class?uncap_first}.${col.column.colName}${'}'}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 180px;"/>
				<img onclick="WdatePicker({el:'${mainTable.variables.class?uncap_first}.${col.column.colName}',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
				</td>
					<#break>
				</#switch>
			<#if index%2==1>
			</tr>
			</#if>
			<#assign index = index+1>
			</#if>
		</#list>
		</table>
	</form>
</div>	      	
