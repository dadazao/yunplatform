<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
<!--
	//收取邮件
	ns.email.receiveInboxMessage = function(mailAccountId){
		$('#mailBoxListArea').loadUrl('<%=basePath %>/pages/platform/email/inboxMessage/receive.action?mailAccountId='+mailAccountId+'&email=${email}');
	}

	//查看收件
	ns.email.viewInboxMessage = function(id){
		navTab.openTab('viewInboxMessageTab'+id,'<%=basePath %>/pages/platform/email/inboxMessage/view.action?inboxMessage.id='+id,{title:'已收邮件'});
		$.ajaxSetup({global:false});
		$('#mailBoxListArea').loadUrl('<%=basePath %>/pages/platform/email/inboxMessage/inbox.action?queryType=${queryType}&email=${email}');
		$.ajaxSetup({global:true});
	}
	
	ns.email.deleteInboxMessage = function(id){
		if(id){
			var url = "<%=basePath%>/pages/platform/email/inboxMessage/putIntoDustbin.action?inboxMessageIDs="+id;
		}else{
			var items = $("#mailBoxListArea #tableForm input[type='checkbox']:checked").length;
			if(items == 0){alertMsg.warn("请选择要删除的数据!");return;}
			var url = "<%=basePath%>/pages/platform/email/inboxMessage/putIntoDustbin.action";
			var param = $("#mailBoxListArea #tableForm").serialize();
			var url = url + "?" + param;
		}
		alertMsg.confirm("删除后将进入垃圾箱，您确定吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	$(function() {
		ns.common.showQuery('${queryType}')
		ns.common.mouseForButton();
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath%>/pages/platform/email/inboxMessage/inbox.action?queryType=${queryType}&email=${email}">
		<input type="hidden" name="pageNum" value="${pageResult.currentPage}" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />
		<input type="hidden" name="inboxMessage.subject" value="${inboxMessage.subject}" />
		<input type="hidden" name="inboxMessage.from" value="${inboxMessage.from}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" class="listbutton" onclick="ns.email.newMailMessage();" >写邮件</button>
					<button type="button" class="listbutton" onclick="ns.email.receiveInboxMessage('${mailAccountId}');" >收取</button>				
					<button type="button" class="listbutton" onclick="ns.email.deleteInboxMessage();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return divSearch(this,'mailBoxListArea');"" action="<%=basePath%>/pages/platform/email/inboxMessage/inbox.action?queryType=0&email=${email}" method="post">
				<table class="searchContent">
					<tr>
						<td class="queryTd">主题	<input name="inboxMessage.subject" value="${inboxMessage.subject}"/></td>
						<td></td>
<%--						<td class="queryTd">--%>
<%--						接收时间&nbsp;&nbsp;从:<input readonly="true" type="text" id="dyncMapDate.${ce.formColumn.columnName}_advanceStart" name="dyncMapDate.${ce.formColumn.columnName}_start" value="${dyncMapDate[startdate]}" style="width: 80px;"/>--%>
<%--							<img onclick="WdatePicker({el:'dyncMapDate.${ce.formColumn.columnName}_advanceStart',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>--%>
<%--							到:<input readonly="true" type="text" id="dyncMapDate.${ce.formColumn.columnName}_advanceStart" name="dyncMapDate.${ce.formColumn.columnName}_start" value="${dyncMapDate[startdate]}" style="width: 80px;"/>--%>
<%--							<img onclick="WdatePicker({el:'dyncMapDate.${ce.formColumn.columnName}_advanceStart',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>--%>
<%--						</td>--%>
						<td  class="queryTd">发件人<input name="inboxMessage.from" value="${inboxMessage.from}" /></td>
						<td>
							<button type="button" class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'mailBoxListArea')">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" rel="mailBoxListArea" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'inboxMessageIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center" width="5%" >状态</th>
						<th align="center" width="20%">发件人</th>
						<th align="center" width="40%">主题</th>
						<th align="center" width="15%">接收时间</th>
						<th align="center" width="10%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="inboxMessage" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="inboxMessageIDs" value="${inboxMessage.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center" valign="middle">
		                    		<c:if test="${inboxMessage.read==false}"><div class="email-inbox-unreaded" style="height: 16px;line-height:26px;">&nbsp;</div></c:if>
		                    		<c:if test="${inboxMessage.read==true}"><div class="email-inbox-readed" style="height: 16px;line-height:26px;">&nbsp;</div></c:if>
		                    	</td>
		                    	<td><div style="text-align: left;">${inboxMessage.from}</div></td>
		                    	<td><div style="text-align: left;">${inboxMessage.subject}</div></td>
		                    	<td align="center"><fmt:formatDate value="${inboxMessage.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.email.viewInboxMessage('${inboxMessage.id}');">查看</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.email.deleteInboxMessage('${inboxMessage.id}');">删除</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>