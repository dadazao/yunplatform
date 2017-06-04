<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	ns.system.saveAuthProduct = function(){
		$("#authProductForm").submit();
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editAuthProduct" name="editAuthProduct" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveAuthProduct" name="saveAuthProduct" class="listbutton" onclick="ns.system.saveAuthProduct();">保存</button>
</div>
<div id="yunDialog">
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
				<form id="authProductForm" method="post" action="<%=basePath%>/pages/system/authProduct/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="authProductId" type=hidden name="authProduct.id" value="${authProduct.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${authProduct.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								产品名称
							</td>
							<td align="left" width="40%">
								<input maxlength="50" type="text" name="authProduct.name" value="${authProduct.name}"  class="textInput required" style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								版本号
							</td>
							<td align="left">
								<input maxlength="10" type="text" name="authProduct.version" value="${authProduct.version}"  class="textInput number" style="width:180px;"/>
							</td>
						</tr>
					</table>
				</form>
			</div>	      	
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>