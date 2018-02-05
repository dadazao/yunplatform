<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	ns.message.sendMessage = function() {
		navTab.openTab('10000003210002', "<%=basePath%>/pages/platform/message/sentMessage/create.action",{title:'发消息'});
	}
	
	ns.message.deleteReceivedMessage = function(id){
		var url = '';
		if(id){
			url = "<%=basePath%>/pages/platform/message/receivedMessage/delete.action?receivedMessageIDs="+id;
		}else{
			var items = $("input[type='checkbox']:checked").length;
			if(items == 0){
				alertMsg.warn("请选择要删除的数据!");
				return;
			}
			url = "<%=basePath%>/pages/platform/message/receivedMessage/delete.action?"+$("#tableForm").serialize();
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	ns.message.viewReceivedMessage = function(id) {
		$.ajaxSetup({async:false});
		$.pdialog.open("<%=basePath%>/pages/platform/message/receivedMessage/view.action?id=" + id,"viewReceivedMessageDialog","明细",{width:950,height:650,mask:true,resizable:true});
		$.ajaxSetup({global:false});
		refresh();
		$.ajaxSetup({global:true,async:true}); //import to set async true..
	}
	
	ns.message.replyMessage = function(id){
		$.pdialog.open('<%=basePath%>/pages/platform/message/receivedMessage/replyui.action?id='+id,"replyMessageDialog","回复消息",{width:950,height:650,mask:true,resizable:true});
	}
	
	ns.message.initTypeSelect = function(defaultValue){
		$.post('<%=basePath%>/pages/resource/dictionarysCompexdics.action?parentId=10000002020000',null,function(data){
			var _html = '';
			$.each(data,function(index,item){
				if(item.key==defaultValue){
					_html+='<option value="'+item.key+'" selected="selected">'+item.value+'</option>';
				}else{
					_html+='<option value="'+item.key+'">'+item.value+'</option>';
				}
			});
			$('#receivedMessageTypeSelect').empty().html(_html);
		},'json');
	}
	
	$(function() {
		ns.common.showQuery('${queryType}');
		ns.common.mouseForButton();
		ns.message.initTypeSelect('${receivedMessage.type}');
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath%>/pages/platform/message/receivedMessage/list.action?queryType=${queryType}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />		
		<input type="hidden" name="receivedMessage.subject" value="${receivedMessage.subject}" />
		<input type="hidden" name="receivedMessage.type" value="${receivedMessage.type}" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" class="listbutton" onclick="ns.message.sendMessage();" >发消息</button>
					<button type="button" class="listbutton" onclick="ns.message.deleteReceivedMessage();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath%>/pages/platform/message/receivedMessage/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr>
						<td>标题</td>
						<td class="queryTd"><input name="receivedMessage.subject" value="${receivedMessage.subject}"/></td>
						<td>消息类型</td>
						<td class="queryTd">
							<select id="receivedMessageTypeSelect" name="receivedMessage.type" style="width:160px;">
								<option value="-1">请选择</option>
							</select>
						</td>
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
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'receivedMessageIDs')"/></th>
						<th align="center" width="5%" >序号</th>
						<th align="center" width="40%">标题</th>
						<th align="center" width="10%">发信人</th>
						<th align="center" width="10%">类型</th>
						<th align="center" width="10%">发送时间</th>
						<th align="center" width="10%">状态</th>
						<th align="center" width="10%" >管理</th>
					</tr>
				</thead>
				<tbody>
		            <c:forEach items="${pageResult.content}" var="receivedMessage" varStatus="status">
		                <c:if test="${status.count%2==0}">
		                	<tr target="id_column" rel="1" class='event'>
		                </c:if>
		                <c:if test="${status.count%2!=0}">
		                	<tr target="id_column" rel="1">
		                </c:if>
		                    	<td align="center"><input type="checkbox" class="checkbox"  name="receivedMessageIDs" value="${receivedMessage.id}"/></td>
		                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
		                    	<td align="center">${receivedMessage.subject}</td>
		                    	<td align="center">${receivedMessage.senderName}</td>
		                    	<td align="center">${receivedMessage.type}</td>
		                    	<td align="center"><fmt:formatDate value="${receivedMessage.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                    	<td align="center"><c:if test="${receivedMessage.read==true}">已读</c:if><c:if test="${receivedMessage.read!=true}">未读</c:if></td>
		                    	<td align="center">
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.message.viewReceivedMessage('${receivedMessage.id}');">查看</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.message.replyMessage('${receivedMessage.id}');">回复</a>&nbsp;
		                    		<a style="cursor: pointer;color:blue;" onclick="ns.message.deleteReceivedMessage('${receivedMessage.id}');">删除</a>
		                    	</td>
		                    </tr>
		            </c:forEach>
				</tbody>	
			</table>
		</div>
	</form>
</body>
</html>