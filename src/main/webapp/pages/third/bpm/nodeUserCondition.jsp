<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div align="right" style="height:25px;margin-top: 5px;">
	<button class="listbutton" type="button" id="btnSearch" onclick="conditionDialog('table_${nodeUserMap.nodeId}')">添加</button>
	<button class="listbutton" type="button" id="btnDel" onclick="delRows('table_${nodeUserMap.nodeId}');">删除</button>
	<button class="listbutton" type="button" id="btnSaveGroupNo" onclick="saveGroupNo('${nodeUserMap.nodeId}')" style="width:80px;">保存批次号</button>
</div>
<table style="width:100%" id="table_${nodeUserMap.nodeId}" class="tableClass">
	<thead>
		<tr>
			<th style="" class="thClass" width="50" nowrap="nowrap">选择</th>
			<th class="thClass" width="*" nowrap="nowrap">条件名称</th>
			<th class="thClass" width="*" nowrap="nowrap">条件</th>
			<th class="thClass" nowrap="nowrap" width="80">批次号</th>
		</tr>
	</thead>
	<tbody class="datat">
			<input type="hidden" name="setId" value="${nodeUserMap.setId}"/>
			<input type="hidden" name="definitionId" value="${definitionId}"/>
			<input type="hidden" name="nodeId" value="${nodeUserMap.nodeId}"/>
	<c:choose>
		<c:when test="${fn:length(nodeUserMap.bpmUserConditionList)>0}">
			<c:forEach items="${nodeUserMap.bpmUserConditionList}" var="conditionNode" varStatus="cnt">
				<tr>
					<td class="tdClass">
							<input type='checkbox' name='nodeUserCk' onchange="changeCheck(this)"/>					
							<input type="hidden" name="conditionId" value="${conditionNode.id}"/>		
							<input type="hidden" name="sn" value="${conditionNode.sn}"/>					
							<c:if test="${nodeTag!=null}">
								<input type="hidden" name="nodeTag" value="${nodeTag}"/>
							</c:if>
					</td>
					<td class="tdClass">
							${conditionNode.conditionname }
					</td>
					<td class="tdClass">
							<textarea name="listCmpNames" style="width:90%;font-size: 13px;" rows="1" readonly="readonly">${conditionNode.conditionShow}</textarea>
					</td>
					<td class="tdClass">
						<div style="width: 80px">
							<input name="groupNo" style="width:70px;" class="inputText" ivalue ="${conditionNode.groupNo}"  value="${conditionNode.groupNo}" />
						</div>
					</td>
				</tr>
			</c:forEach>
		</c:when>
	</c:choose>
	</tbody>
</table>