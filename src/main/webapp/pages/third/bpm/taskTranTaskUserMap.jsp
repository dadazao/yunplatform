<%
	//对应TaskController里的tranTaskUserMap方法，返回某个任务节点的所有跳转分支的任务节点及其执行人员列表
%>
<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<table class="table-grid">
		<thead>
		<tr>
			<th height="28">执行路径</th>
			<c:if test="${canChoicePath}">
				<th height="28">选择同步条件后的执行路径</th>
			</c:if>
			<th>目标任务</th>
		</tr>
		</thead>		
		<c:forEach items="${nodeTranUserList}" var="nodeTranUser" varStatus="i">
			<tr>
				<td height="28" width="18%" nowrap="nowrap">
					<c:if test="${selectPath==1 }">
						<input type="radio" name="destTask" value="${nodeTranUser.nodeId}" <c:if test="${i.count==1}">checked="checked"</c:if> />
					</c:if>
					${nodeTranUser.nodeName}<!-- 跳转的目标节点 -->
				</td>
				<c:if test="${canChoicePath}">
				<td>
					<c:forEach items="${nodeTranUser.nextPathMap}" var="nextPath">
							<div>
								<label><input type="checkbox" name="nextPathId" value="${nextPath.key}"/>
								${nextPath.value}</label>
							</div>
						</c:forEach>
					</td>
				</c:if>
				<td>
					<c:forEach items="${nodeTranUser.nodeUserMapSet}" var="nodeUserMap">
						<div>
							${nodeUserMap.nodeName}
							<input type="hidden" name="lastDestTaskId" value="${nodeUserMap.nodeId}"/>
							<span name="spanSelectUser">
							<c:forEach items="${nodeUserMap.taskExecutors}" var="executor">
								
								<input type="checkbox" name="${nodeUserMap.nodeId}_userId" checked="checked" value="${executor.type}^${executor.executeId}^${executor.executor}"/>&nbsp;${executor.executor}
							</c:forEach>
							</span>
							&nbsp;&nbsp;<a href="javascript:;" class="link grant" onclick="selExeUsers(this,'${nodeUserMap.nodeId}')">选择...</a>
						</div>
					</c:forEach>
				</td> 
			</tr>
		</c:forEach>
	</table>