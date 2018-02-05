<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${authKey.id}"/>
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
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								产品
							</td>
							<td align="left" width="40%">
								${authProductname}&nbsp;
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								模版
							</td>
							<td align="left">
								${authKey.templatename}&nbsp;
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								客户
							</td>
							<td align="left" width="40%">
								${authKey.customername}&nbsp;
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								有效期
							</td>
							<td align="left">
								${authKey.validdays}天&nbsp;
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								激活起始日期
							</td>
							<td align="left" >
								<fmt:formatDate value="${authKey.startdate}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								激活截止日期
							</td>
							<td align="left" >
								<fmt:formatDate value="${authKey.enddate}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;
							</td>
						</tr>
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