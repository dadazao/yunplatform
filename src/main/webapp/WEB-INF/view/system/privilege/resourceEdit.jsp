<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
<!--
	$(function(){
		
	});
//-->
</script>
<form id="resourceFormId" name="resource" class="pageform required-validate">
	<input id="resourceId" type=hidden name="sysResource.id" value="${sysResource.id}"/>
	<input id="optCounter" type=hidden name="sysResource.optCounter" value="${sysResource.optCounter}"/>
	<input id="module" type=hidden name="sysResource.module" value="${sysResource.module}"/>
 	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		<tr>
			<td align="left" class="Input_Table_Label" width="10%">
				资源名称
			</td>
			<td align="left" width="40%">
				<input maxlength="50" type="text" name="sysResource.resourceName" value="${sysResource.resourceName}"  class="textInput required" style="width:180px;"/>
			</td>
			<td align="left" class="Input_Table_Label" width="10%">
				资源类型
			</td>
			<td align="left" width="40%">
				<select id="sysResourceType" name="sysResource.type" style="width: 186px">
 					<c:forEach items="${resourceTypes}" var="type">
						<option value="${type.value}" <c:if test="${type.value==sysResource.type }">selected="selected"</c:if>>${type.name}</option>
					</c:forEach>
 				</select>
			</td>
		</tr>
		<tr>
			<td align="left" class="Input_Table_Label" width="10%">
				资源url
			</td>
			<td align="left" colspan="3">
				<input type="text" name="sysResource.resourceUrl" value="${sysResource.resourceUrl}"  class="textInput required" style="width:640px;"/>
			</td>
		</tr>
		<tr>
			<td align="left" class="Input_Table_Label" width="10%">
				是否启用
			</td>
			<td align="left" colspan="3">
				<input type="radio" name="sysResource.enabled" value="1" <c:if test="${sysResource.enabled==1}">checked="checked"</c:if>/>是
				<input type="radio" name="sysResource.enabled" value="0" <c:if test="${sysResource.enabled==0}">checked="checked"</c:if>/>否
			</td>
		</tr>
		<tr>
			<td align="left" class="Input_Table_Label" width="10%">
				资源描述
			</td>
			<td colspan="3" align="left">
				<textarea name="sysResource.comment" rows="5" cols="103" class="textInput">${sysResource.comment}</textarea>
			</td>
		</tr>
	</table>
	<div align="right" style="padding-top: 5px;padding-bottom: 5px;">
		<input type="button" name="add" class="listbutton" value="添加" onclick="ns.privilege.addResource();"/>
		<input type="button" name="save" class="listbutton" value="保存" onclick="ns.privilege.updateResource();"/>
		<input type="button" name="delete" class="listbutton" value="删除" onclick="ns.privilege.deleteResource();"/>
		<input type="button" name="delete" class="listbutton" value="启用" onclick="ns.privilege.enableResource();"/>
		<input type="button" name="delete" class="listbutton" value="禁用" onclick="ns.privilege.disableResource();"/>
	</div>
</form>