<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.user.deleteUserOrg = function(id){
		$.ajax({
			type:'post',
			url: "<%=basePath %>/pages/system/user/userdeleteUserOrg.action?userOrgId="+id,
			success: function(){
				dialogRefresh();
				alertMsg.info("删除成功!");
			}
		
		});
	}
	
	ns.user.setMainOrg = function(id){
		$.ajax({
			type:'post',
			url: "<%=basePath %>/pages/system/user/usersetMainOrg.action?userOrgId="+id,
			success: function(){
				
			}
		});
	}
	
	ns.user.setPrincipal = function(obj,id){
		$.ajax({
			type:'post',
			url: "<%=basePath %>/pages/system/user/usersetPrincipal.action?userOrgId="+id+"&status="+obj.checked,
			success: function(){
				
			}
		});
	}
//-->
</script>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/user/userselectedOrgList.action">
	<input type="hidden" name="userId" value="${userId}">
	<input type="hidden" name="queryType" value="${queryType}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
</form>
<form id="tabListForm" name="tabListForm">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
<tbody>
	<thead>
		<tr>
			<th class="thClass">机构名称</th>
			<th class="thClass">是否主机构</th>
			<th class="thClass">主要负责人</th>
			<th class="thClass" width="10%">操作</th>
		</tr>
	</thead>
	<c:forEach items="${pageResult.content}" var="userOrg" varStatus="status">
	     <c:if test="${status.count%2==0}">
	           	<tr target="id_column" rel="1" class='event'>
	           </c:if>
	           <c:if test="${status.count%2!=0}">
	           	<tr target="id_column" rel="1">
	           </c:if>
	          <td class="tdClass">${userOrg.sysOrg.orgName}</td>
	          <td class="tdClass">
	          	<c:if test="${userOrg.zhujigou==true}"><input type="radio" name="tmpMainOrgRadio" checked="checked" onclick="ns.user.setMainOrg('${userOrg.id}')"/></c:if>
	          	<c:if test="${userOrg.zhujigou==false}"><input type="radio" name="tmpMainOrgRadio" onclick="ns.user.setMainOrg('${userOrg.id}')"/></c:if>
	          </td>
	          <td class="tdClass">
	          	<c:if test="${userOrg.fuzeren==true}"><input type="checkbox" checked="checked" onclick="ns.user.setPrincipal(this,'${userOrg.id}')"/></c:if>
	          	<c:if test="${userOrg.fuzeren==false}"><input type="checkbox" onclick="ns.user.setPrincipal(this,'${userOrg.id}')"/></c:if>
	          </td>
	          <td class="tdClass">
	          	<table align="center">
		          	<tr>
		          		<td width="33%"></td>
			          	<td>
			          		<div onclick="ns.user.deleteUserOrg('${userOrg.id}')" style="cursor: pointer;width:12px;height:12px; background-image: url('images/jquery/ui-icons_cd0a0a_256x240.png');background-repeat: no-repeat;background-position: -99px -131px;"></div>
			          	</td>
			          	<td width="33%"></td>
		          	</tr>
	          	</table>
	          </td>
	     </tr>
	 </c:forEach>
	</tbody>	
</table>
</form>
<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<input type="hidden" name="numPerPage" value="20">
		<span>${pageResult.pageSize}条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="selectedOrgListDiv" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>