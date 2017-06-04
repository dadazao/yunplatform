<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
<!--
	ns.message.newSentMessage = function(){
		$.pdialog.open("<%=basePath%>/pages/platform/message/sentMessage/create.action","newSentMessageDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.message.deleteSentMessage = function(id){
		if(id){
			var url = "<%=basePath%>/pages/platform/message/sentMessage/delete.action?sentMessageIDs="+id;
		}else{
			var url = "<%=basePath%>/pages/platform/message/sentMessage/delete.action";
			var param = $("#tableForm").serialize();
			var url = url + "?" + param;
			var items = $("input[type='checkbox']:checked").length;
			if(items == 0){
				alertMsg.warn("请选择要删除的数据!");
				return;
			}
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.message.viewSentMessage = function(id) {
		$.pdialog.open("<%=basePath%>/pages/platform/message/sentMessage/view.action?id=" + id,"viewSentMessageDialog","明细",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function() {
		ns.common.showQuery('${queryType}');
		ns.common.mouseForButton();
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath%>/pages/platform/message/sentMessage/list.action?queryType=${queryType}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />		
		<input type="hidden" name="sentMessage.subject" value="${sentMessage.subject}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newSentMessage" name="newSentMessage" class="listbutton" onclick="ns.message.newSentMessage();" >发消息</button>
					<button type="button" id="deleteSentMessage" name="deleteSentMessage" class="listbutton" onclick="ns.message.deleteSentMessage();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/platform/message/sentMessage/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>标题</td>
						<td class="queryTd"><input name="sentMessage.subject" value="${sentMessage.subject}"/></td>
						<td><button type="button" class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'sentMessageIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center" width="40%">标题</th>
						<th align="center" width="10%">类型</th>
						<th align="center" width="10%">发送时间</th>
						<th align="center" width="20%">收件人</th>
						<th align="center" width="10%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="sentMessage" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="sentMessageIDs" value="${sentMessage.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${sentMessage.subject}</td>
		                    	<td align="center">${sentMessage.type}</td>
		                    	<td align="center"><fmt:formatDate value="${sentMessage.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                    	<td align="center" title="${sentMessage.receiver}">
		                    		<c:if test="${fn:length(sentMessage.receiver)>20}">${fn:substring(sentMessage.receiver,0,20)}...</c:if>
		                    		<c:if test="${fn:length(sentMessage.receiver)<=20}">${sentMessage.receiver}</c:if>
		                    	</td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.message.viewSentMessage('${sentMessage.id}');">查看</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.message.deleteSentMessage('${sentMessage.id}');">删除</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>