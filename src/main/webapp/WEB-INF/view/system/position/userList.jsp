<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
ns.position.addUser = function(){
	$.pdialog.open("<%=basePath %>/pages/system/position/addUser.action?positionId="+$("#positionId").val(),"addUserDialog","添加用户",{width:800,height:500,mask:true,resizable:true});
}
//-->
</script>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/system/position/positionuserList.action">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	<input type="hidden" name="positionId" value="${positionId}" />
</form>

   	<form id="puserListForm" name="puserListForm">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
		<thead>
			<tr>
				<th class="thClass" width="5%"><input name="all" type="checkbox"  class="checkbox" onclick="dialogSelectAll(this,'selectedSubIDs')"/></th>
				<th class="thClass" width="5%">序号</th>
				<th class="thClass">用户</th>
				<th class="thClass">是否主岗位</th>
				<th class="thClass" width="10%">操作</th>
			</tr>
		</thead>
		<c:forEach items="${pageResult.content}" var="userPosition" varStatus="status">
	        <c:if test="${status.count%2==0}">
               	<tr target="id_column" rel="1" class='event'>
               </c:if>
               <c:if test="${status.count%2!=0}">
               	<tr target="id_column" rel="1">
               </c:if>
	             <td class="tdClass" width="5%"><input type="checkbox" class="checkbox"  name="selectedSubIDs" value="${userPosition.id}"></td>
	             <td class="tdClass" width="5%"><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
	             <td class="tdClass">${userPosition.sysUser.fullname}</td>
	             <td class="tdClass">
	             	<c:if test="${userPosition.zhugangwei==false}">否</c:if>
	             	<c:if test="${userPosition.zhugangwei==true}">是</c:if>
	             </td>
				 <td class="tdClass" width="10%">
					&nbsp;
					<a style="cursor: pointer;" href="#" onclick="ns.position.shezhiZhugang('${userPosition.id}')"><span><font color="blue">设为主岗位</font></span></a>
					&nbsp;
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
	<div class="pagination" targetType="dialog" rel="userTabList" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<div class="buttonPanel">
	<button type="button" class="listbutton" onclick="ns.position.addUser()">添加</button>
	<button type="button" class="listbutton" onclick="ns.position.deleteUser()">删除</button>
</div>