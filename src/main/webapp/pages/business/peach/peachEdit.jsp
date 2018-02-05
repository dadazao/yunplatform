<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	//保存实体
	ns.peach.savePeach = function(){
		$("#peachForm").submit();
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
	
	$(function(){
	});
	
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editPeach" name="editPeach" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="savePeach" name="savePeach" class="listbutton" onclick="ns.peach.savePeach();">保存</button>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected" onclick="ns.common.changeSaveAction('savePeach','ns.peach.savePeach')"><a><span>基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="peachForm" method="post" action="<%=basePath %>/pages/business/peach/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="peachId" type=hidden name="peach.id" value="${peach.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${peach.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								价格
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="peach.price" value="${peach.price}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								功效
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="peach.gongxiao" value="${peach.gongxiao}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								品种
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="peach.pinzhong" value="${peach.pinzhong}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								成熟时间
							</td>
							<td align="left" width="40%">
							<input readonly="true" type="text" id="peach.maturetime" name="peach.maturetime" value='<fmt:formatDate value="${peach.maturetime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 180px;"/>
							<img onclick="WdatePicker({el:'peach.maturetime',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
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