<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div class="buttonPanel">
	<button type="button" class="listbutton" onclick="ns.desktop.editDesktopItem('${desktopItem.id}');">修改</button>
	<button type="button" class="listbuttonDisable" disabled="disabled">保存</button>
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
			<div layoutH="100">
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							栏目名称
						</td>
						<td align="left" width="90%">
							${desktopItem.name}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							栏目别名
						</td>
						<td align="left" width="90%">
							${desktopItem.alias}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							方法路径
						</td>
						<td align="left" width="90%">
							${desktopItem.methodUrl}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							模块路径
						</td>
						<td align="left" width="90%">
							${desktopItem.moduleUrl}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="100%" colspan="2">
							模板HTML：
						</td>
					</tr>
					<tr>
						<td align="left" colspan="2">
							<pre>${fn:escapeXml(desktopItem.templateHtml)}</pre>
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
