<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path+ "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
	function viewMeta(version,id){
		$.pdialog.open("<%=basePath %>/pages/resource/table/metaHistory.action?version="+version + "&id=" + id,"ysDialog","元数据元素",{width:950,height:650,mask:true,resizable:true});
	}
</script>

</head>

<body>
	<div style="overflow: scroll;width:790px;height:590px;">
	    <table id="metaColumnEditTable" width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
			<tbody>
				<thead>
					<tr>
						<th class="thClass" >版本号</th>
						<th class="thClass" >操作</th>
					</tr>
				</thead>
				<c:forEach items="${pageResult.content}" var="column" varStatus="status">
			        <c:if test="${status.count%2==0}">
		               <tr target="id_column" rel="1" class='event'>
		               </c:if>
		               <c:if test="${status.count%2!=0}">
		               <tr target="id_column" rel="1">
		               </c:if>
			             <td class="tdClass" >${column['tbl_version']}</td>
			             <td class="tdClass" ><a style="cursor: pointer;color:blue;" onclick=viewMeta('${column["tbl_version"]}','${column["tbl_tableId"]}')>查看</a></td>
			        </tr>
			    </c:forEach>
			</tbody>	
		</table>
	</div>
</body>
</html>