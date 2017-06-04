<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path +"/";
%>
<script type="text/javascript">
	//删除实体
	ns.${mainTable.parentClassName?uncap_first}.deleteSub${mainTable.variables.class} = function(){
		var items = $("#${mainTable.variables.class?uncap_first}SubListForm input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return false;
		}
		var url = "<%=basePath%>/${mainTable.namespace}/${mainTable.variables.class?uncap_first}/delete.action?"+$("#${mainTable.variables.class?uncap_first}SubListForm").serialize();
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	//保存实体
	ns.${mainTable.parentClassName?uncap_first}.saveSub${mainTable.variables.class} = function(){
		var domainId = $("#domainId").val();
		if(domainId){
			var $form = $("#${mainTable.variables.class?uncap_first}SubEditForm");
			var url = '<%=basePath %>${mainTable.namespace}/${mainTable.variables.class?uncap_first}/save.action?${mainTable.parentClassName?uncap_first}Id='+domainId;
			$form.attr("action",url);
			$('#pagerForm input[name="${mainTable.parentClassName?uncap_first}Id"]').val(domainId);
			$form.submit();
		}else{
			alertMsg.warn("请先保存主记录");
		}
	}
	
	//添加实体
	ns.${mainTable.parentClassName?uncap_first}.addSub${mainTable.variables.class} = function(){
		var domainId = $("#domainId").val();
		if(domainId){
			var $form = $("#${mainTable.variables.class?uncap_first}SubEditForm");
			var url = '<%=basePath %>${mainTable.namespace}/${mainTable.variables.class?uncap_first}/save.action?${mainTable.parentClassName?uncap_first}Id='+domainId;
			$form.attr("action",url);
			$('#pagerForm input[name="${mainTable.parentClassName?uncap_first}Id"]').val(domainId);
			$form.submit();
		}else{
			alertMsg.warn("请先保存主记录");
		}
	}
</script>
<div id="${mainTable.variables.class?uncap_first}SubEditDiv">
	<form id="${mainTable.variables.class?uncap_first}SubEditForm" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, refreshList);" novalidate="novalidate">
		<div style="display: none">
			<input type="hidden" name="${mainTable.variables.class?uncap_first}.id" value="<#noparse>${</#noparse>${mainTable.variables.class?uncap_first}.id<#noparse>}</#noparse>" />
			<input type="hidden" name="${mainTable.variables.class?uncap_first}.optCounter" value="<#noparse>${</#noparse>${mainTable.variables.class?uncap_first}.optCounter<#noparse>}</#noparse>" />
		</div>
		<table class="Input_Table" width="100%" cellspacing="0" cellpadding="2" border="0">
			<tbody>
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
								<#if index%2==0>
						<td align="left" with="90%">
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
			</tbody>
		</table>
	</form>
	<div style="margin-top:5px;" align="right">
		<button onclick="ns.${mainTable.parentClassName?uncap_first}.addSub${mainTable.variables.class}();" style="width:60px;height:24px;" class="listbutton"  type="button">
			添加
		</button>
		<button onclick="ns.${mainTable.parentClassName?uncap_first}.saveSub${mainTable.variables.class}();" style="width:60px;height:24px;" class="listbutton"  type="button">
			保存
		</button>
		<button onclick="ns.${mainTable.parentClassName?uncap_first}.deleteSub${mainTable.variables.class}();" style="width:60px;height:24px;" class="listbutton"  type="button">
			删除
		</button>
	</div>
</div>
