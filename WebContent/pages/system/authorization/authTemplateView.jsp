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
<div class="buttonPanel">
	<button type="button" id="editAuthTemplate" name="editAuthTemplate" class="listbutton" onclick="ns.system.editAuthTemplate('${authTemplate.id}');">修改</button>
	<button type="button" id="saveAuthTemplate" name="saveAuthTemplate" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${authTemplate.id}"/>
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
							产品名称
						</td>
						<td align="left">
							${authProduct.name}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							模版名称
						</td>
						<td align="left" width="40%">
							${authTemplate.name}&nbsp;
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							授权类型
						</td>
						<td align="left" width="40%">
							<c:if test="${authTemplate.authtype == 'CLOUD'}">
								云授权&nbsp;
							</c:if>
							<c:if test="${authTemplate.authtype == 'NATIVE'}">
								本地授权&nbsp;
							</c:if>
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							有效期
						</td>
						<td align="left">
							${authTemplate.validdays}&nbsp;
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							起始日期
						</td>
						<td align="left" width="40%">
							<fmt:formatDate value="${authTemplate.startdate}" pattern="yyyy-MM-dd"/>&nbsp;
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							结束日期
						</td>
						<td align="left">
							<fmt:formatDate value="${authTemplate.enddate}" pattern="yyyy-MM-dd"/>&nbsp;
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