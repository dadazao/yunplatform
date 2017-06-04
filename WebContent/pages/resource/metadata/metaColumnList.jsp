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
</head>

<body>
	<div style="overflow: scroll;width:940px;height:630px;">
	    <table id="metaColumnEditTable" width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
			<tbody>
				<thead>
					<tr>
						<th class="thClass" >序号</th>
						<th class="thClass" >字段名称</th>
						<th class="thClass" >拼音名称</th>
						<th class="thClass" >数据类型</th>
						<th class="thClass" >唯一标识</th>
						<th class="thClass" >元数据类型</th>
						<th class="thClass" >是否必选</th>
						<th class="thClass" >是否重复</th>
						<th class="thClass" >值域</th>
						<th class="thClass" >默认值</th>
					</tr>
				</thead>
				<c:forEach items="${pageResult.content}" var="column" varStatus="status">
			        <c:if test="${status.count%2==0}">
		               <tr target="id_column" rel="1" class='event'>
		               </c:if>
		               <c:if test="${status.count%2!=0}">
		               <tr target="id_column" rel="1">
		               </c:if>
			             <td class="tdClass" ><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
			             <td class="tdClass" >${column['tbl_columnZhName']}</td>
			             <td class="tdClass" >${column['tbl_columnName']}</td>
			             <td class="tdClass" >${column['tbl_dataType']}</td>
			             <td class="tdClass" >${column['tbl_columnCode']}</td>
			             <td class="tdClass" >
			             	<c:if test="${column['tbl_metaType']==1 }">简约型</c:if> 
							<c:if test="${column['tbl_metaType']==0 }">复合型</c:if>
			             </td>
			             <td class="tdClass" >
			             	<c:if test="${column['tbl_isRequired']==1 }">是</c:if> 
							<c:if test="${column['tbl_isRequired']==0 }">否</c:if>
						</td>
			            <td class="tdClass" >
							<c:if test="${column['tbl_isRepeat']==1 }">是</c:if> 
							<c:if test="${column['tbl_isRepeat']==0 }">否</c:if>
						</td>
			             <td class="tdClass" >${column['tbl_min']}~${column['tbl_max']}</td>
			             <td class="tdClass" >${column['tbl_defaultValue']}</td>
			        </tr>
			    </c:forEach>
			</tbody>	
		</table>
	</div>
</body>
</html>