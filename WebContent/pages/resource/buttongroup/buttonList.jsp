<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<script type="text/javascript">
<!--
$(function(){
	
})
function showButtonInfo(ID,buttonID,displayOrder,eStatus,dStatus){
  $("#buttonAndGroupID").attr("value",ID);	
  $("#buttonID").attr("value",buttonID);	
  $("#buttonDisplayOrder").attr("value",displayOrder);	
  $("#buttonEnabledStatus").attr("value",eStatus);	
  $("#buttonDisplayStatus").attr("value",dStatus);	
}
function selectAll(obj, cName) {
	var checkboxs = document.getElementsByName(cName);
	for ( var i = 0; i < checkboxs.length; i++) {
		checkboxs[i].checked = obj.checked;
	}
}
//-->
</script>
<form id="pagerForm" name="pagerForm"
	action="<%=basePath %>/pages/resource/buttonAndGrouplist"
	onsubmit="return divSearch(this,'divlist');" method="post">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="buttonGroupID" value="${buttonGroupID}" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
</form>
<div class="panelBar">
	<div class="pages">
		<span>显示${pageResult.pageSize}条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="divlist"
		totalCount="${pageResult.totalCount}"
		numPerPage="${pageResult.pageSize}"
		pageNumShown="${pageResult.pageCountShow}"
		currentPage="${pageResult.currentPage}"></div>
</div>
<form id="buttonAndGroupForm" name="buttonAndGroupForm" method="post">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
	<tbody>
		<thead>
			<tr>
				<th class="thClass" width="5%">
					<input type="checkbox" class="checkbox"  class="checkboxCtrl" group="checkboxID" onclick="selectAll(this,'checkboxAndGroupIDs')" />
				</th>
				<th class="thClass" width="5%">
					序号
				</th>
				<th class="thClass">
					按钮名称
				</th>
	<%--			<th>--%>
	<%--				启用状态--%>
	<%--			</th>--%>
				<th class="thClass">
					显示状态
				</th>
				<th class="thClass">
					显示次序
				</th>
				<th class="thClass" width="10%">
					操作
				</th>
			</tr>
		</thead>
		<c:forEach items="${pageResult.content}" var="buttonAndGroup"
			varStatus="status">
					<c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
				<td class="tdClass" align="center">
					<input type="checkbox" class="checkbox"  id="checkboxAndGroupIDs" name="checkboxAndGroupIDs" value="${buttonAndGroup.id}"/>
				</td>
				<td class="tdClass">
					${(pageResult.currentPage-1) * pageResult.pageSize + status.count}
				</td>
				<td class="tdClass">
					${buttonAndGroup.buttonName}
				</td>
<%--				<td align="center">--%>
<%--					${buttonAndGroup.buttonEnabledStatusName}--%>
<%--				</td>--%>
				<td class="tdClass">
					${buttonAndGroup.buttonDisplayStatusName}
				</td>
				<td class="tdClass">
					${buttonAndGroup.buttonDisplayOrder}
				</td>
				<td class="tdClass">
					<a href="javascript:;"
						onClick="showButtonInfo('${buttonAndGroup.id}','${buttonAndGroup.buttonID}',${buttonAndGroup.buttonDisplayOrder},${buttonAndGroup.buttonEnabledStatus},${buttonAndGroup.buttonDisplayStatus})">
						维护 </a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</form>
