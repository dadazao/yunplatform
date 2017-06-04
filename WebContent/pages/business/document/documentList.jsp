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
	//新建实体
	ns.document.newDocument = function(){
		$.pdialog.open("<%=basePath%>/pages/business/document/add.action","newDocumentDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
	
	//删除实体
	ns.document.deleteDocument = function(){
		var url = "<%=basePath%>/pages/business/document/delete.action";
		var param = $("#tableForm").serialize();
		var url = url + "?" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,refreshList);}});
	}
	
	//查看实体明细
	ns.document.viewDocument = function(id) {
		$.pdialog.open("<%=basePath%>/pages/business/document/view.action?document.id=" + id,"viewDocumentDialog","明细",{width:950,height:650,mask:true,resizable:true});
	}
	
	//修改实体
	ns.document.editDocument = function(id){
		$.pdialog.closeCurrent();
		$.pdialog.open("<%=basePath%>/pages/business/document/edit.action?document.id=" + id,"editDocumentDialog","修改",{width:950,height:650,mask:true,resizable:true});
	}
	
	$(function() {
		//判断是否显示高级查询
		ns.common.showQuery('${queryType}')
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//-->
</script>
</head>
<body>	
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/business/document/list.action?queryType=${queryType}">
<!--		<input type="hidden" name="status" value="${param.status}">-->
<!--		<input type="hidden" name="orderField" value="${param.orderField}" />-->
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="document.type" value="${document.type}"/>
		<input type="hidden" name="document.date" value="${document.date}"/>
		<input type="hidden" name="document.wenhao" value="${document.wenhao}"/>
		<input type="hidden" name="document.miji" value="${document.miji}"/>
		<input type="hidden" name="document.title" value="${document.title}"/>
		<input type="hidden" name="document.keyword" value="${document.keyword}"/>
		<input type="hidden" name="document.zhusong" value="${document.zhusong}"/>
		<input type="hidden" name="document.chaosong" value="${document.chaosong}"/>
		<input type="hidden" name="document.department" value="${document.department}"/>
		<input type="hidden" name="document.drafter" value="${document.drafter}"/>
		<input type="hidden" name="document.flow" value="${document.flow}"/>
		<input type="hidden" name="document.content" value="${document.content}"/>
		<input type="hidden" name="document.flowstate" value="${document.flowstate}"/>
		<input type="hidden" name="document.filetime" value="${document.filetime}"/>
		<input type="hidden" name="document.curapprover" value="${document.curapprover}"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr><td>
					<button type="button" id="newDocument" name="newDocument" class="listbutton" onclick="ns.document.newDocument();" >新建</button>
					<button type="button" id="deleteDocument" name="deleteDocument" class="listbutton" onclick="ns.document.deleteDocument();" >删除</button>				
				</td></tr>
			</table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<form id="defaultQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/business/document/list.action?queryType=0" method="post">
				<table class="searchContent">
					<tr><td>
						<td>发文类型</td>
						<td class="queryTd">
							<input type="text" name="document.type" value="${document.type}"  class="textInput" style="width:180px;"/>
						</td>
						<td>日期</td>
						<td class="queryTd">
							<input readonly="true" type="text" id="defaultQuerydocument.date" name="document.date" value='<fmt:formatDate value="${document.date}" pattern="yyyy-MM-dd"/>' style="width: 140px;"/>
							<img onclick="WdatePicker({el:'defaultQuerydocument.date',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
						</td>
						<td>文号</td>
						<td class="queryTd">
							<input type="text" name="document.wenhao" value="${document.wenhao}"  class="textInput" style="width:180px;"/>
						</td>
						<td>密级</td>
						<td class="queryTd">
							<input type="text" name="document.miji" value="${document.miji}"  class="textInput" style="width:180px;"/>
						</td>
						<td>标题</td>
						<td class="queryTd">
							<input type="text" name="document.title" value="${document.title}"  class="textInput" style="width:180px;"/>
						</td>
						<td>主题词</td>
						<td class="queryTd">
							<input type="text" name="document.keyword" value="${document.keyword}"  class="textInput" style="width:180px;"/>
						</td>
						<td>主送</td>
						<td class="queryTd">
							<input type="text" name="document.zhusong" value="${document.zhusong}"  class="textInput" style="width:180px;"/>
						</td>
						<td>抄送</td>
						<td class="queryTd">
							<input type="text" name="document.chaosong" value="${document.chaosong}"  class="textInput" style="width:180px;"/>
						</td>
						<td>拟稿部门</td>
						<td class="queryTd">
							<input type="text" name="document.department" value="${document.department}"  class="textInput" style="width:180px;"/>
						</td>
						<td>拟稿人</td>
						<td class="queryTd">
							<input type="text" name="document.drafter" value="${document.drafter}"  class="textInput" style="width:180px;"/>
						</td>
						<td>办理流程</td>
						<td class="queryTd">
							<input type="text" name="document.flow" value="${document.flow}"  class="textInput" style="width:180px;"/>
						</td>
						<td>正文</td>
						<td class="queryTd">
							<input type="text" name="document.content" value="${document.content}"  class="textInput" style="width:180px;"/>
						</td>
						<td>总状态</td>
						<td class="queryTd">
							<input type="text" name="document.flowstate" value="${document.flowstate}"  class="textInput" style="width:180px;"/>
						</td>
						<td>归档时间</td>
						<td class="queryTd">
							<input readonly="true" type="text" id="defaultQuerydocument.filetime" name="document.filetime" value='<fmt:formatDate value="${document.filetime}" pattern="yyyy-MM-dd"/>' style="width: 140px;"/>
							<img onclick="WdatePicker({el:'defaultQuerydocument.filetime',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
						</td>
						<td>当前审批人</td>
						<td class="queryTd">
							<input type="text" name="document.curapprover" value="${document.curapprover}"  class="textInput" style="width:180px;"/>
						</td>
						<td>
							<button type="button" class="listbutton" onclick="ns.common.query('defaultQueryForm');">查询</button>
							<button type="button" class="listbutton" onclick="ns.common.showQuery(1);">高级查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="advanceQuery" align="center" class="pageHeader" style="display:none;">
		<fieldset class="queryFieldset" >
			<legend style="border:0px;">查询项</legend>
			<form id="advanceQueryForm" onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/business/document/list.action?queryType=1" method="post">
				<table width="98%"  class="queryTable" >
					<tr>
						<td class="advanceTd">高级查询字段名</td><td class="advanceTd">高级查询字段值	</td>
					</tr>
				</table>
				<div align="center" style="margin-top: 5px;">
					<input type="hidden" name="listId" value="${listId}"/>
					<button type="button" class="listbutton" onclick="ns.common.showQuery(0);">隐藏</button>
					<button type="button" class="listbutton" onclick="ns.common.query('advanceQueryForm');">查询</button>
				</div>
			</form>
		</fieldset>
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
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="tableForm" name="tableForm" method="post">
		<div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<th align="center" width="5%" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'documentIDs')"/></th>
						<th align="center" width="5%">序号</th>
							<th align="center">发文类型</th>
							<th align="center">日期</th>
							<th align="center">文号</th>
							<th align="center">密级</th>
							<th align="center">标题</th>
							<th align="center">主题词</th>
							<th align="center">主送</th>
							<th align="center">抄送</th>
							<th align="center">拟稿部门</th>
							<th align="center">拟稿人</th>
							<th align="center">办理流程</th>
							<th align="center">正文</th>
							<th align="center">总状态</th>
							<th align="center">归档时间</th>
							<th align="center">当前审批人</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody>
	            <c:forEach items="${pageResult.content}" var="document" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
                    	<td align="center"><input type="checkbox" class="checkbox"  name="documentIDs" value="${document.id}"/></td>
						<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
						<td align="center">${document.type}</td>
						<td align="center"><fmt:formatDate value="${document.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td align="center">${document.wenhao}</td>
						<td align="center">${document.miji}</td>
						<td align="center">${document.title}</td>
						<td align="center">${document.keyword}</td>
						<td align="center">${document.zhusong}</td>
						<td align="center">${document.chaosong}</td>
						<td align="center">${document.department}</td>
						<td align="center">${document.drafter}</td>
						<td align="center">${document.flow}</td>
						<td align="center">${document.content}</td>
						<td align="center">${document.flowstate}</td>
						<td align="center"><fmt:formatDate value="${document.filetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td align="center">${document.curapprover}</td>
                    	<td align="center">
                    		<a style="cursor: pointer;color:blue;" onclick="ns.document.viewDocument('${document.id}');">明细</a>&nbsp;
                    		<a style="cursor: pointer;color:blue;" onclick="ns.document.editDocument('${document.id}');">编辑</a>
                    	</td>
                    </tr>
	            </c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>