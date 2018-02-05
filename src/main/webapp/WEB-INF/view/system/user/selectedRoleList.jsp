<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.user.deleteUserRole = function(id){
		$.ajax({
			type:'post',
			url: "<%=basePath %>/pages/system/user/userdeleteUserRole.action?userRoleId="+id,
			success: function(){
				dialogRefresh();
				alertMsg.info("删除成功!");
			}
		
		});
	}
//-->
</script>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/user/userselectedRoleList.action">
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
			<th class="thClass">角色名称</th>
			<th class="thClass" width="10%">操作</th>
		</tr>
	</thead>
	<c:forEach items="${pageResult.content}" var="userRole" varStatus="status">
	     <c:if test="${status.count%2==0}">
	           	<tr target="id_column" rel="1" class='event'>
	           </c:if>
	           <c:if test="${status.count%2!=0}">
	           	<tr target="id_column" rel="1">
	           </c:if>
	          <td class="tdClass">${userRole.sysRole.roleName}</td>
	          <td class="tdClass">
	          	<table align="center">
		          	<tr>
		          		<td width="33%"></td>
			          	<td>
			          		<div onclick="ns.user.deleteUserRole('${userRole.id}')" style="cursor: pointer;width:12px;height:12px; background-image: url('images/jquery/ui-icons_cd0a0a_256x240.png');background-repeat: no-repeat;background-position: -99px -131px;"></div>
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
	<div class="pagination" targetType="dialog" rel="selectedRoleListDiv" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>