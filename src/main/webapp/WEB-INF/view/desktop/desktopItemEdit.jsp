<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	ns.desktop.saveDesktopItem = function(){
		$('#desktopItemForm').submit();
	}
//-->
</script>
<div class="buttonPanel">
	<button type="button" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" class="listbutton" onclick="ns.desktop.saveDesktopItem();">保存</button>
</div>
<div id="desktopItemDialog">
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
				<form id="desktopItemForm" method="post" action="<%=basePath%>/pages/platform/desktop/desktopItem/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="desktopItemId" type=hidden name="desktopItem.id" value="${desktopItem.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${desktopItem.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								栏目名称
							</td>
							<td align="left" width="90%">
								<input type="text" name="desktopItem.name" value="${desktopItem.name}"  class="textInput required" style="width:400px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								栏目别名
							</td>
							<td align="left" width="90%">
								<input type="text" name="desktopItem.alias" value="${desktopItem.alias}"  class="textInput required" style="width:400px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								方法路径
							</td>
							<td align="left" width="90%">
								<input type="text" name="desktopItem.methodUrl" value="${desktopItem.methodUrl}"  class="textInput" style="width:400px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								模块路径
							</td>
							<td align="left" width="90%">
								<input type="text" name="desktopItem.moduleUrl" value="${desktopItem.moduleUrl}"  class="textInput required" style="width:400px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="100%" colspan="2">
								模板HTML：
							</td>
						</tr>
						<tr>
							<td align="left" width="100%" colspan="2">
								<textarea cols="140" rows="20" name="desktopItem.templateHtml">${fn:escapeXml(desktopItem.templateHtml)}</textarea>
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
