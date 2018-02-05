<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div align="right" style="height:30px;">
	<button class="listbutton" type="button" id="btnSearch"  onclick="addUserSetRow('${nodeTag}')">添加</button>
	<button class="listbutton" type="button" id="btnSearch"  onclick="delRows('${nodeId}');">删除</button>
</div> 
<table style="width:100%" id="table_${nodeTag}" class="tableClass">								
	<tr>
		<th class="thClass" width="5%" nowrap="nowrap">选择</th>
		<th class="thClass" width="15%" nowrap="nowrap">用户类型</th>
		<th class="thClass" width="40%" nowrap="nowrap">用户来自</th>
		<c:if test="${nodeId != '' }">
			<th class="thClass" width="10%" nowrap="nowrap">抽取用户</th>
		</c:if>
		<th class="thClass" nowrap="nowrap" width="15%">运算类型</th>
	</tr>
	<tbody class="datat">
	<c:choose>
		<c:when test="${fn:length(nodeUserMap.nodeUserList)==0}">
			<tr>
				<td class="tdClass" nowrap="nowrap" height="28">
					<input type='checkbox' name='nodeUserCk'/>
					<input type="hidden" name="nodeUserId" value=""/>
					<input type="hidden" name="nodeId" value="${nodeTag}"/>
					<input type="hidden" name="assignUseType" value="${userNode.assignUseType}"/>	
				</td>
				<td class="tdClass" style="padding:10px;">
					<select id="flow_assignType" name="assignType" class="select" onchange="assignTypeChange(this);">
						<c:forEach items="${userSetTypes}" var="item">
							<option value="${item.key}" <c:if test="${item.key==1}">selected="selected"</c:if> >${item.value}</option>
						</c:forEach>							
					</select>
				</td>
				<td class="tdClass" style="padding:10px;">
					<input type="hidden" id="cmpIds" name="cmpIds" value=""/>
					<textarea name="cmpNames" style="width:70%" rows="2" readonly="readonly"></textarea>
					<button type="button"  class="listbutton" onclick="selectCmp(this);">选择</button>
				</td>
				<c:if test="${nodeId != '' }">
					<td class="tdClass" style="padding:10px;">
						<c:choose>
							<c:when test="${isMultiInstance}">
								<select name="extractUser">
									<option value="0" <c:if test="${userNode.extractUser eq 0}">selected="selected"</c:if> >不抽取</option>
									<option value="1" <c:if test="${userNode.extractUser eq 1}">selected="selected"</c:if>>抽取</option>
									<option value="2" <c:if test="${userNode.extractUser eq 2}">selected="selected"</c:if>>二级抽取</option>
									<option value="3" <c:if test="${userNode.extractUser eq 3}">selected="selected"</c:if>>用户组合</option>
								</select>
							</c:when>
							<c:otherwise>
								<select name="extractUser">
									<option value="0" <c:if test="${userNode.extractUser eq 0}">selected="selected"</c:if>>不抽取</option>
									<option value="1" <c:if test="${userNode.extractUser eq 1}">selected="selected"</c:if>>抽取</option>
								</select>
							</c:otherwise>
						</c:choose>
						
					</td>
				</c:if>
				<td class="tdClass" style="padding:10px;">
					<select name="compType">
						<option value="0">或运算</option>
						<option value="1">与运算</option>
						<option value="2">排除</option>
					</select>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach items="${nodeUserMap.nodeUserList}" var="userNode" varStatus="cnt">
				<tr id="${nodeUserMap.nodeId}_${cnt.count}">
					<td  nowrap="nowrap" height="28">
						<input type='checkbox' name='nodeUserCk'/> ${cnt.count}
						<input type="hidden" name="nodeUserId" value="${userNode.nodeUserId}"/>
						<input type="hidden" name="nodeId" value="${userNode.nodeId}"/>
						<input type="hidden" name="assignUseType" value="${userNode.assignUseType}"/>	
						<input type="hidden" name="formIdentity" value="${bpmUserCondition.formIdentity}"/>											 	
						<c:choose>
							<c:when test="${userNode.assignUseType==0}">参与流程</c:when>
							<c:when test="${userNode.assignUseType==1}">接收通知</c:when>														
						</c:choose>
					</td>
					<td>
						<input type="hidden" name="assignType" value="${userNode.assignType}"/>
						<span>
							${userSetTypes[userNode.assignType]}
						</span>
					</td>
					<td>
						<input type="hidden" name="cmpIds" value='${userNode.cmpIds}'/>
						<c:choose>
							<c:when test="${userNode.assignType==0 
											or userNode.assignType==9 
											or userNode.assignType==11 
											or userNode.assignType==13
											or userNode.assignType==14
											or userNode.assignType==15}">
									<span>${userSetTypes[userNode.assignType]}</span>
									<textarea name="cmpNames" style="width:80%;display:none;" rows="3" class="textarea">${userNode.cmpNames}</textarea>
									<a class="button" onclick="selectCmp(this);" style="display:none;">
										<span>选择...</span>
									</a>
							</c:when>
							
							<c:otherwise>
								<textarea name="cmpNames" readonly="readonly" style="width:80%;visibility:visible" rows="3" class="textarea">${userNode.cmpNames}</textarea>
								<a class="button" onclick="selectCmp(this);" style="visibility:visible"><span>选择...</span></a>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<a onclick="move('table_${nodeTag}','up','${cnt.count}')"><img src="<%=basePath %>/images/icon/moveup.png"></a>
						<a onclick="move('table_${nodeTag}','down','${cnt.count}')"><img src="<%=basePath %>/images/icon/movedown.png"></a>
					</td>
					<td>
						<select name="compType">
							<option value="0" <c:if test="${userNode.compType==0}">selected</c:if> >或运算</option>
							<option value="1" <c:if test="${userNode.compType==1}">selected</c:if> >与运算</option>
							<option value="2" <c:if test="${userNode.compType==2}">selected</c:if> >排除</option>
						</select>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	</tbody>
</table>					