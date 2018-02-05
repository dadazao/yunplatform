<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
				$("#tabDivId").append('<div id="detailTab"></div>');
		$("#detailTab").loadUrl('<%=basePath %>/pages/business/employee/detail/view.action?employeeId='+$('#domainId').val());
		$("#tabDivId").append('<div id="projectTab"></div>');
		$("#projectTab").loadUrl('<%=basePath %>/pages/business/employee/projectTab.jsp');
	});
		//--> 
</script>
<div class="buttonPanel">
	<button type="button" id="editEmployee" name="editEmployee" class="listbutton" onclick="ns.employee.editEmployee('${employee.id}');">修改</button>
	<button type="button" id="saveEmployee" name="saveEmployee" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${employee.id}"/>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
					<li><a><span>员工详细信息表</span></a></li>
					<li><a><span>项目表</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							姓名
						</td>
						<td align="left" width="40%">
							${employee.name}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							编号
						</td>
						<td align="left" width="40%">
							${employee.code}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							生日
						</td>
						<td align="left" width="40%">
							<fmt:formatDate value="${employee.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							手机
						</td>
						<td align="left" width="40%">
							${employee.phone}
						</td>
					</tr>
				</table>
			</div>	      	
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>