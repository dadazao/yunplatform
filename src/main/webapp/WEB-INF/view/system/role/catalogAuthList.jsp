<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
ns.role.addInAuthList = function(obj,id,name){
	if(obj.checked){
		var flag = true;
		$.each($("div[belong='hasauthId']"),function (entryIndex,entry){
			if( $(entry).html()== id){
				flag = false;
			}
		});
		if(flag){
			$("#selectAuthList").append("<tr id='"+id+"'><td height='14px' align='center'>"+name+"<div style='display:none' belong='hasauthId'>"+id+"</div></td><td height='14px' align='center'><div onclick='ns.role.deleteTr(this)' style='cursor: pointer;width:12px;height:12px; background-image: url(\"<%=basePath%>/themes/css/images/ui-icons_cd0a0a_256x240.png\");background-repeat: no-repeat;background-position: -99px -131px;'></div></td></tr>");
		}
	}else{
		$("#"+id).remove();
	}
}

ns.role.selfSelectAll = function(obj, cName) {
	var checkboxs = $("input[name='"+cName+"']", $.pdialog.getCurrent());
	if(checkboxs.length == 0 ){
		alertMsg.warn("当前列表无数据!");
		$(obj).attr('checked', false);
		return false;
	}
	for ( var i = 0; i < checkboxs.length; i++) {
		checkboxs[i].checked = obj.checked;
		ns.role.addInAuthList(checkboxs[i],$(checkboxs[i]).val(),$(checkboxs[i]).attr("title"));
	}
}

ns.role.deleteTr = function(entry){
	$(entry).parent().parent().remove();
}
//-->
</script>
<form id="catalogAuthListForm" name="catalogAuthListForm">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
<tbody>
	<thead>
		<tr>
			<th class="thClass" width="5%"><input name="all" type="checkbox"  class="checkbox" onclick="ns.role.selfSelectAll(this,'selectedSubIDs')"/></th>
			<th class="thClass">权限名称</th>
		</tr>
	</thead>
	<c:forEach items="${catalogAuthList}" var="catalogAuth" varStatus="status">
	     <c:if test="${status.count%2==0}">
	     <tr target="id_column" rel="1" class='event'>
	     </c:if>
	     <c:if test="${status.count%2!=0}">
	     <tr target="id_column" rel="1">
	     </c:if>
	          <td class="tdClass" width="5%"><input <c:if test="${catalogAuth.selected==true }">checked</c:if> onclick="ns.role.addInAuthList(this,'${catalogAuth.id}','${catalogAuth.privilegeName}')" name="selectedSubIDs" type="checkbox" class="checkbox" value="${catalogAuth.id}" title="${catalogAuth.privilegeName}"></td>
	          <td class="tdClass" title="${catalogAuth.comment}">${catalogAuth.privilegeName}</td>
	     </tr>
	 </c:forEach>
	</tbody>	
</table>
</form>
