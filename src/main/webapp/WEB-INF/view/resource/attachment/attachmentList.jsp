<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	ns.attachment.viewDocument = function(filepath){
		$.pdialog.open("<%=basePath%>/pages/resource/flexPaper/open.action?filePathAndName="+filepath,"lbdyDialog","查看文件",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.attachment.attachmentDelete = function(){
		var url = "<%=basePath%>/pages/resource/attachment/attachmentdelete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/attachment/attachmentattachmentList.action">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" /> 
		<input type="hidden" name="module" value="${module}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<button type="button" class="listbutton" onclick="ns.attachment.attachmentDelete();">删除</button>
			</td></tr></table>
		</div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<table class="table" width="100%">
			<thead>
				<tr>
					<th width="5%" align="center"><input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/></th>
					<th width="5%" align="center">序号</th>
					<th width="30%" align="center">文件名称</th>
					<th align="center">文件类型</th>
					<th align="center">文件大小</th>
					<th align="center">创建人</th>
					<th align="center">创建时间</th>
					<th width="5%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="attachment" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
		                    <td align="center" width="5%"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${attachment.id}"></td>
		                    <td width="5%">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    <td>${attachment.fileName}</td>
		                    <td>${attachment.fileExt}</td>
		                    <td>${attachment.fileSize}</td>
		                    <td>${attachment.createUser}</td>
		                    <td>${attachment.createDate}</td>
		                    <td>
								<a style="cursor: pointer;color: blue;" onclick="eventCompexCKWJ('${attachment.id}')">下载</a>
								<c:if test="${attachment.fileExt=='.doc'||attachment.fileExt=='.xls'||attachment.fileExt=='.ppt'||attachment.fileExt=='.docx'||attachment.fileExt=='.xlsx'||attachment.fileExt=='.pptx'}">
									<a style="cursor: pointer;color: blue;" onclick="ns.attachment.viewDocument('${attachment.filePath}')">查看</a>
								</c:if>
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
			<span>20条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" rel="listId" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
</body>
</html>