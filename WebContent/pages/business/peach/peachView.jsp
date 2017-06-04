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
	<button type="button" id="editPeach" name="editPeach" class="listbutton" onclick="ns.peach.editPeach('${peach.id}');">修改</button>
	<button type="button" id="savePeach" name="savePeach" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${peach.id}"/>
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
							价格
						</td>
						<td align="left" width="40%">
							${peach.price}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							功效
						</td>
						<td align="left" width="40%">
							${peach.gongxiao}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							品种
						</td>
						<td align="left" width="40%">
							${peach.pinzhong}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							成熟时间
						</td>
						<td align="left" width="40%">
							<fmt:formatDate value="${peach.maturetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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