<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<div>
	  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
				<tr>
					<td class="Input_Table_Label" width="20%">流程运行ID:</td>
					<td>${bpmRunLog.runid}</td>
				</tr>
				<tr>
					<td class="Input_Table_Label" width="20%">流程标题:</td>
					<td>${bpmRunLog.processSubject}</td>
				</tr>
				<tr>
					<td class="Input_Table_Label" width="20%">用户名称:</td>
					<td>${bpmRunLog.username}</td>
				</tr>
				<tr>
					<td class="Input_Table_Label" width="20%">操作时间:</td>
					<td><fmt:formatDate value="${bpmRunLog.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td class="Input_Table_Label" width="20%">操作类型:</td>
					<td>
						<c:choose>
							<c:when test="${bpmRunLog.operatortype eq 0}"><span style="line-height:26px;color:green;">启动流程</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 1}"><span style="line-height:26px;color:green;">交办</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 2}"><span style="line-height:26px;color:red;">追回</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 3}"><span style="line-height:26px;color:red;">删除流程实例</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 4}"><span style="line-height:26px;color:green;">同意(投票)</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 5}"><span style="line-height:26px;color:red;">反对(投票)</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 6}"><span style="line-height:26px;color:green;">弃权(投票)</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 7}"><span style="line-height:26px;color:green;">补签</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 8}"><span style="line-height:26px;color:red;">驳回</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 9}"><span style="line-height:26px;color:red;">驳回到发起人</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 10}"><span style="line-height:26px;color:red;">删除任务</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 11}"><span style="line-height:26px;color:green;">代理任务</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 13}"><span style="line-height:26px;color:green;">锁定任务</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 14}"><span style="line-height:26px;color:green;">任务解锁</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 15}"><span style="line-height:26px;color:green;">添加意见</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 16}"><span style="line-height:26px;color:green;">任务指派</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 17}"><span style="line-height:26px;color:green;">设定所有人</span></c:when>
							<c:when test="${bpmRunLog.operatortype eq 18}"><span style="line-height:26px;color:green;">结束任务</span></c:when>
							<c:otherwise><span style="line-height:26px;color:red;">其他</span></c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="Input_Table_Label" width="20%">备注:</td>
					<td>${bpmRunLog.memo}</td>
				</tr>
			</table>
		</div>	      	
