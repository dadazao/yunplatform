<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	ns.system.saveAuthKey = function(){
		$("#authKeyForm").submit();
	}
	
	//产品模版联动
	ns.system.findTemplatesByPId = function(){
		var pid = $("#editAuthProductId").val();
		$.ajax({
			url:__basePath+"/pages/system/authKey/findTemplatesByPId.action",
			dataType:"json",
			data:{authProductId:pid},
			success:function(data){
				var shtml = "<option value='' enddate='' startdate='' validdays='' ></option>";
				$.each(data,function (index, elem) { 
					shtml += "<option value='"+elem.id+"' validdays='"+elem.validdays+"' startdate='"+elem.startdatestr+"' enddate='"+elem.enddatestr+"' >"+elem.name+"</option>";
				});
				$("#editAuthTemplateId").html(shtml);
			}
		});
		
	}
	
	//模版有效期、激活起始日期、激活截止日期带入文本框
	ns.system.templateforinput= function(){
		var validdays = $("#editAuthTemplateId option:selected").attr("validdays");
		var startdate = $("#editAuthTemplateId option:selected").attr("startdate");
		var enddate = $("#editAuthTemplateId option:selected").attr("enddate");
		$("#validdaysId").val(validdays);		
		$("#startdateId").val(startdate);		
		$("#enddateId").val(enddate);
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
		
		$("#editAuthProductId").combobox();
		$("#editAuthCustomerId").combobox();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="saveAuthKey" name="saveAuthKey" class="listbutton" onclick="ns.system.saveAuthKey();">保存</button>
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
				<form id="authKeyForm" method="post" action="<%=basePath%>/pages/system/authKey/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="authKeyId" type=hidden name="authKey.id" value="${authKey.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${authKey.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								产品
							</td>
							<td align="left" width="40%">
								<select id="editAuthProductId" name="authKey.productid" onchange="ns.system.findTemplatesByPId();"
									style="width: 186px" class="textInput required">
									<option value="" ></option>
									<c:forEach items="${authProducts}" var="bean">
										<option value="${bean.id}" >${bean.name}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								模版
							</td>
							<td align="left">
								<select id="editAuthTemplateId" name="authKey.templateid" onchange="ns.system.templateforinput();"
									style="width: 186px" class="textInput required">
									<option value="" ></option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								客户
							</td>
							<td align="left" width="40%">
								<select id="editAuthCustomerId" name="authKey.customerid"
									style="width: 186px;" class="textInput required">
									<option value="" ></option>
									<c:forEach items="${authCustomers}" var="bean">
										<option value="${bean.id}" >${bean.name}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								有效期
							</td>
							<td align="left">
								<input id="validdaysId" maxlength="10" type="text" name="authKey.validdays" value="${authKey.validdays}"  class="textInput number required " style="width:60px;"/>&nbsp;&nbsp;天
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								激活起始日期
							</td>
							<td align="left" >
								<input id="startdateId" type="text" id="startdateId" name="authKey.startdate" value='<fmt:formatDate value="${authKey.startdate}" pattern="yyyy-MM-dd"/>' readonly="true" class="date" style="width:135px;"/>
								<img onclick="WdatePicker({el:'startdateId',dateFmt:'yyyy-MM-dd'})" src="<%=basePath %>js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								激活截止日期
							</td>
							<td align="left" >
								<input id="enddateId" type="text" id="enddateId" name="authKey.enddate" value='<fmt:formatDate value="${authKey.enddate}" pattern="yyyy-MM-dd"/>' readonly="true" class="date" style="width:135px;"/>
								<img onclick="WdatePicker({el:'enddateId',dateFmt:'yyyy-MM-dd'})" src="<%=basePath %>js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;"/>
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