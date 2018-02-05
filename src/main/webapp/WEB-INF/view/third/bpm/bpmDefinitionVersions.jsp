<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" ><input name="all" type="checkbox"  class="checkbox" onclick="ns.common.selectAll(this,'selectedIDs')"/></th>
				<th align="center">序号</th>
				<th align="center" width="15%">标题</th>
				<th align="center" width="15%">流程定义Key</th>
				<th align="center">分类</th>
				<th align="center">版本号</th>
				<th align="center">修改原因</th>
				<th align="center">创建时间</th>
				<th align="center">修改时间</th>
				<th align="center" width="15%" >管理</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${bpmDefinitionList}" var="bpmDefinitionItem" varStatus="status">
                <c:if test="${status.count%2==0}">
                	<tr target="id_column" rel="1" class='event'>
                </c:if>
                <c:if test="${status.count%2!=0}">
                	<tr target="id_column" rel="1">
                </c:if>
                    	<td align="center"><input type="checkbox" class="checkbox"  name="selectedIDs" value="${bpmDefinitionItem.defId}"/></td>
                    	<td align="center">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
                    	<td align="center">${bpmDefinitionItem.subject}</td>
                    	<td align="center">${bpmDefinitionItem.defKey}</td>
                    	<td align="center">${bpmDefinitionItem.typeName}</td>
                    	<td align="center">${bpmDefinitionItem.versionNo}</td>
                    	<td align="center">${bpmDefinitionItem.reason}</td>
                    	<td align="center"><fmt:formatDate value="${bpmDefinitionItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    	<td align="center"><fmt:formatDate value="${bpmDefinitionItem.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    	<td align="center">
                    		<a style="cursor: pointer;color:blue;" onclick="ns.bpm.deleteHistoryDefinition(${bpmDefinitionItem.defId},true);">删除</a>&nbsp;
                    		<a style="cursor: pointer;color:blue;" onclick="ns.bpm.onlineDesign(${bpmDefinitionItem.id})">查看设计</a>&nbsp;
                    	</td>
                    </tr>
            </c:forEach>
		</tbody>	
	</table>
</div>

