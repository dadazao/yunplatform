<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src="js/tiny_mce/jquery.tinymce.js"></script>
<script type="text/javascript">
<!--
	$(function(){
		$('#tblContent').tinymce({
			script_url : 'js/tiny_mce/tiny_mce.js',
	
			// General options
			plugins : "formAndList,style,table," +
			"emotions,iespell,inlinepopups,insertdatetime,preview,searchreplace,print,contextmenu,paste," +
			"directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras",

			// Theme options
			theme_advanced_buttons2 : "formAndList,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
			theme_advanced_buttons1 : "tablecontrols,|,hr,removeformat,visualaid|,charmap,emotions,iespell,advhr|,ltr,rtl,|,fullscreen |search,replace,|,bullist,numlist,|outdent,indent|,undo,redo,|cleanup,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom",
			
			content_css : "js/tiny_mce/themes/advanced/skins/common.css",
			
			relative_urls: false
		});
		addPartitionPage();
		$('#yunDialog').attr('style', 'height: 580px;overflow-x:hidden;OVERFLOW-Y:auto;');
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		ns.common.mouseForButton();
	});
	
	function eventCompexTemplateBC(){
		var tableHtml = $('#tblContent').val();
		var i=0;
		var j=0;
		for(var m=0; m<tableHtml.split('</tr>').length; m++){
			j = tableHtml.split('</tr>')[m].split('</td>').length-1;
			if(i<j){
				i=j;
			}
		}
		$('#tblTds').val(i);
		$('#tblTrs').val(tableHtml.split('</tr>').length-1);
		$("#buttonSubmit").click();
	}
	
	function templateDialogAjaxDone(json) {
		$("#templateId").val(json.domainId);
		$("#domainId").val(json.domainId);
		refreshList(json);
	}
//-->
</script>
<div class="buttonPanel">
	<button onclick="eventCompexXG();" style="width:60px;heith:24px;" class="listbuttonDisable" name="b1" id="XG" type="button" disabled="disabled">修改</button>	
	<button onclick="eventCompexTemplateBC();" style="width:60px;heith:24px;" class="listbutton" name="b2" id="BC" type="button">保存</button>	
	<button onclick="eventCompexBDDY();" style="width:70px;heith:24px;" class="listbutton" name="b4" id="BDDY" type="button">打印预览</button>
	<button onclick="eventCompexFORMBZ();" style="width:60px;heith:24px;" class="listbutton" name="b3" id="FORMBZ" type="button">帮助</button>		
</div>
<div id="yunDialog">
	<div class="tabs" id="formTab">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected">
						<a><span>基本信息</span></a>
					</li>
				</ul>
			</div>
		</div>
		<div id="tabDivId" class="tabsContent">
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" id="templateFormID"
					method="post"
					action="<%=basePath%>/pages/resource/templatesaveComb.action"
					class="pageForm required-validate"
					onsubmit="return validateCallback(this, templateDialogAjaxDone);">
					<div style="display: none;">
						<input id="domainSubmit" type="submit" />
						<input type="hidden" id="isFirstTab" value="true">
						<input id="formId" name="formId" value="${formId}" />
						<input id="domainId" name="domainId" value="${sysTemplate.id}"/>
						<input id="paramsId" name="params" value="${params}" />
						<input id="mainTable" name="mainTable" value="${mainTable}" />
						<input id="tabId" name="tabId" value="157"/>
						<input id="buttonSubmit" type="submit" name="submit" />
						<input id="domainSubmit" type="hidden" value="buttonSubmit" />
						
						<input type="hidden" value="0" name="sysTemplate.tblTrs" id="tblTrs">
						<input type="hidden" value="${sysTemplate.id}" name="sysTemplate.id" id="templateId">
						<input type="hidden" value="0" name="sysTemplate.tblTds" id="tblTds">
						<input type="hidden" value="0" name="sysTemplate.tblTemplatetype" id="tblTemplatetype">
						<input type="hidden" value="1" name="sysTemplate.tblType" id="tblType">
						<input type="hidden" value="1" name="sysTemplate.tblDesignType" id="tblDesignType">
					</div>
				
					<table width="98%" cellspacing="0" cellpadding="2" border="0"
						class="Input_Table" align="left" id="initTable">
						<tr>
							<td height="30" align="left" class="Input_Table_Label"
								width="10%">
								模板名称
							</td>
							<td height="30" align="left" width="90%" colspan="3">
								<input value="${sysTemplate.tblTemplatechname}" name="sysTemplate.tblTemplatechname" id="tblTemplatechname" class="  textInput required" style="width:180px;height:15px;" maxlength="50">
							</td>							
						</tr>
						<tr>
							<td class="Input_Table_Label" align="left" width="10%">
								模板设计
							</td>
							<td align="left" width="90%" colspan="3">
								<textarea cols="120" rows="20" name="sysTemplate.tblContent" id="tblContent" class="textInput">${sysTemplate.tblContent}</textarea>
							</td>
						</tr>			
						<tr>
							<td class="Input_Table_Label" align="left" width="10%">
								<label>
									功能说明
								</label>
							</td>
							<td colspan="3" align="left" width="90%">
								<textarea cols="120" rows="6" name="sysTemplate.tblComment" id="tblComment" class="textInput required">${sysTemplate.tblComment}</textarea>
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