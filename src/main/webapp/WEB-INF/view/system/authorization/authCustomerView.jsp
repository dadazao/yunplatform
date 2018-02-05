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
		
		$("#tabDivId").append('<div id="subTab" onclick=""></div>');
		$("#subTab").loadUrl('<%=basePath %>/pages/system/authCustomerHadware/tab.action');
	});
//--> 
</script>
<div class="buttonPanel">
	<button type="button" id="editAuthCustomer" name="editAuthCustomer" class="listbutton" onclick="ns.system.editAuthCustomer('${authCustomer.id}');">修改</button>
	<button type="button" id="saveAuthCustomer" name="saveAuthCustomer" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${authCustomer.id}"/>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
					<li><a><span>硬件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							客户名称
						</td>
						<td align="left" width="40%">
							${authCustomer.name}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							联系人
						</td>
						<td align="left">
							${authCustomer.linkman}&nbsp;
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							手机
						</td>
						<td align="left" width="40%">
							${authCustomer.mobile}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							邮箱
						</td>
						<td align="left">
							${authCustomer.email}&nbsp;
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							公司电话
						</td>
						<td align="left" width="40%">
							${authCustomer.tel}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							公司地址
						</td>
						<td align="left">
							${authCustomer.address}&nbsp;
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