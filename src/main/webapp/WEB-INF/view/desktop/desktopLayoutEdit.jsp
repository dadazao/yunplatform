<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	ns.desktop.saveDesktopLayout = function(){
		var $form =$('#desktopLayoutForm');
		if($form.valid()){
			var widths='';
			var submitable = true;
			$('#desktopLayoutWidthDiv').children('input').each(function(){
				var value = $(this).val();
				if(isNaN(parseInt(value,10))){
					submitable = false;
				}
				widths+=$(this).val()+',';
			});
			if(!submitable){
				alertMsg.warn("列宽只能是数字");
				return false;
			}
			widths = widths.substring(0,widths.length-1);
			$('#desktopLayoutColumnWidths').val(widths);
			$form.submit();
		}
	}
	
	ns.desktop.chooseColumnNums = function(num){
		if(num==1){
			$('#desktopLayoutWidthDiv').empty().html('<input type="text" class="textInput required digits" style="width: 80px;"/>');
		}else if(num==2){
			$('#desktopLayoutWidthDiv').empty().html('<input type="text" class="textInput required digits" style="width: 80px;"/><input type="text" class="textInput required digits" style="width: 80px;"/>');
		}else if(num==3){
			$('#desktopLayoutWidthDiv').empty().html('<input type="text" class="textInput required digits" style="width: 80px;"/><input type="text" class="textInput required digits" style="width: 80px;"/><input type="text" class="textInput required digits" style="width: 80px;"/>');
		}
	}
</script>
<div class="buttonPanel">
	<button type="button" id="saveCar" name="saveCar" class="listbutton" onclick="ns.desktop.saveDesktopLayout();">保存</button>
</div>
<div id="desktopLayoutDialog">
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
				<form id="desktopLayoutForm" method="post" action="<%=basePath%>/pages/platform/desktop/desktopLayout/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="desktopLayoutId" type=hidden name="desktopLayout.id" value="${desktopLayout.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${desktopLayout.id}" />
					<input type="hidden" name="desktopLayout.deflt" value="${desktopLayout.deflt}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								布局名
							</td>
							<td align="left" width="40%">
								<input type="text" name="desktopLayout.name" value="${desktopLayout.name}"  class="textInput required" style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								列数
							</td>
							<td align="left" width="40%">
								<input type="radio" name="desktopLayout.columnNum" value="1" <c:if test="${desktopLayout.columnNum==1||desktopLayout.columnNum==0}">checked="checked"</c:if> onclick="ns.desktop.chooseColumnNums(1);"/>一列
								<input type="radio" name="desktopLayout.columnNum" value="2" <c:if test="${desktopLayout.columnNum==2}">checked="checked"</c:if> onclick="ns.desktop.chooseColumnNums(2);"/>二列
								<input type="radio" name="desktopLayout.columnNum" value="3" <c:if test="${desktopLayout.columnNum==3}">checked="checked"</c:if> onclick="ns.desktop.chooseColumnNums(3);"/>三列
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								宽度(百分比)
							</td>
							<td align="left" width="40%">
								<input id="desktopLayoutColumnWidths" type="hidden" name="desktopLayout.columnWidths" value="${desktopLayout.columnWidths}"/>
								<div id="desktopLayoutWidthDiv">
									<c:if test="${fn:length(desktopLayout.columnWidths)>0}">
										<c:forEach items="${fn:split(desktopLayout.columnWidths,',')}" var="columnWidth">
											<input type="text" class="textInput required digits" style="width: 80px;" value="${columnWidth}"/>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(desktopLayout.columnWidths)<=0}">
										<input type="text" class="textInput required digits" style="width: 80px;"/>
									</c:if>
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								备注
							</td>
							<td align="left">
								<textarea rows="8" cols="80">${desktopLayout.description}</textarea>
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
