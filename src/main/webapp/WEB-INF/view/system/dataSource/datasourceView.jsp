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
	});
//--> 
</script>
<div class="buttonPanel">
	<button type="button" id="editDataSource" name="editDataSource" class="listbutton" onclick="ns.dataSource.editDataSource('${dataSourcePojo.id}');">修改</button>
	<button type="button" id="saveDataSource" name="saveDataSource" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${dataSourcePojo.id}"/>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							数据源名称
						</td>
						<td align="left" width="40%">
		 		 			${dataSourcePojo.dsName}
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							数据库类型
						</td>
						<td align="left" width="40%">
							<c:forEach items="${dbTypes}" var="code">
								<c:if test="${dataSourcePojo.dsType==code.value}">${code.name}</c:if>
							</c:forEach>
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							数据库驱动
						</td>
						<td align="left" width="40%">
		 		 			${dataSourcePojo.dsDriver}
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							URL
						</td>
						<td align="left" width="40%">
		 		 			${dataSourcePojo.dsUrl}
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							用户名
						</td>
						<td align="left" width="40%">
		 		 			${dataSourcePojo.dsUser}
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							密码
						</td>
						<td align="left" width="40%">
		 		 			${dataSourcePojo.dsPasswd}
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							连接编码
						</td>
						<td align="left" width="40%">
		 		 			<c:forEach items="${dbEncodings}" var="code">
								<c:if test="${dataSourcePojo.dsEncoding==code.value}">${code.name}</c:if>
							</c:forEach>
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							功能说明
						</td>
						<td align="left" width="40%">
		 		 			${dataSourcePojo.comment}
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td align="left" class="Input_Table_Label" width="10%">
							备注
						</td>
						<td align="left" width="40%">
		 		 			${dataSourcePojo.remark}
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