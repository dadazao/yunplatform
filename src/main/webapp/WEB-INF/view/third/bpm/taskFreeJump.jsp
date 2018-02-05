<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<table class="table-detail">
		<tr>
			<th width="15%">跳转节点</th>
			<td width="35%">
				<select id="destTask" name="destTask" onchange="changeDestTask(this)">
					<c:forEach items="${jumpNodeMap}" var="item">
						<optgroup label="${item.key}">
							<c:forEach items="${item.value}" var="node">
								<option value="${node.key}">${node.value}</option>
							</c:forEach>
						</optgroup>
					</c:forEach>
				</select>
			</td>
			<th width="15%">节点执行人</th>
			<td width="35%">
				<input type="hidden" id="lastDestTaskId" name="lastDestTaskId" value="">
				<span id="jumpUserDiv"></span>
				<a href="#" id="jumpUserLink" class="link grant" onclick="selectExeUsers(this)">选择..</a>
			</td>
		</tr>
	</table>