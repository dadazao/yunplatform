<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src="js/tiny_mce/jquery.tinymce.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$('#tblContent').tinymce({
			// Location of TinyMCE script
			script_url : 'js/tiny_mce/tiny_mce_src.js',
	
			theme : "advanced",
			// General options
			plugins : "labelAndValue,style,table," +
			"emotions,iespell,inlinepopups,insertdatetime,preview,searchreplace,print,contextmenu,paste," +
			"directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras",
	
			// Theme options
			theme_advanced_buttons2 : "labelAndValue,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
			theme_advanced_buttons1 : "tablecontrols,|,hr,removeformat,visualaid|,charmap,emotions,iespell,advhr|,ltr,rtl,|,fullscreen |search,replace,|,bullist,numlist,|outdent,indent|,undo,redo,|cleanup,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom",
			
			content_css : "js/tiny_mce/themes/advanced/skins/common.css",
			
			relative_urls: false
		});
		$('#yunDialog_template').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
		ns.common.mouseForButton();
	})
	
	function mobanyongtuChange(){
		$.ajax( {
			type : 'post',
			async : false,
			dataType : 'json',
			url : '<%=basePath %>/pages/resource/templatecontent.action?templateId=' + $("#tblMobanyongtu").val(),
			success : function(data) {
				$('#tblContent').val(data.content);
			}
		});
	}
	
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
		$("#templatebuttonSubmit").click();
	}
	
	function templateDialogAjaxDone(json) {
		$("#templateId").val(json.domainId);
		refreshList(json);
		loadTab("${tabop}");
		$.pdialog.close("newTemplateDialog");
	}
</script>
<div class="buttonPanel">
	<input type="button" class="listbutton" value="保存" onclick="eventCompexTemplateBC()"/>
</div>
<div id="yunDialog_template">
	<div class="tabs" id="formTab">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="selected">
						<a><span>基本信息</span></a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" id="templateFormID"
					method="post"
					action="<%=basePath%>/pages/resource/templatesaveBase.action"
					class="pageForm required-validate"
					onsubmit="return validateCallback(this, templateDialogAjaxDone);">
					<div style="display: none;">
						<input id="domainSubmit" type="submit" />
						<input type="hidden" id="isFirstTab" value="true">
						<input id="formId" name="formId" value="${formId}" />
						<input id="domainId" name="domainId" value="${domainId}" />
						<input id="paramsId" name="params" value="${params}" />
						<input id="mainTable" name="mainTable" value="${mainTable}" />
						<input id="tabId" name="tabId" value="157"/>
						<input id="templatebuttonSubmit" type="submit" name="submit" />
						<input id="domainSubmit" type="hidden" value="buttonSubmit" />
						
						<input type="hidden" value="0" name="sysTemplate.tblTrs" id="tblTrs">
						<input type="hidden" value="${sysTemplate.id}" name="sysTemplate.id" id="templateId">
						<input type="hidden" value="0" name="sysTemplate.tblTds" id="tblTds">
						<input type="hidden" value="0" name="sysTemplate.tblType" id="tblType">
						<input type="hidden" value="1" name="sysTemplate.tblDesignType" id="tblDesignType">
					</div>
				
					<table width="98%" cellspacing="0" cellpadding="2" border="0"
						class="Input_Table" align="left" id="initTable">
						<tr>
							<td height="30" align="left" class="Input_Table_Label"
								width="10%">
								所属分组
							</td>
							<td height="30" align="left" width="40%">
								<select style="width: 186px;" name="sysTemplate.tblSystemteam">
									<option value="bus"
										<c:if test="${'bus'==sysTemplate.tblSystemteam }">selected="selected"</c:if>>
										业务类
									</option>
									<option value="sys"
										<c:if test="${'sys'==sysTemplate.tblSystemteam }">selected="selected"</c:if>>
										系统类
									</option>
									<option value="tes"
										<c:if test="${'tes'==sysTemplate.tblSystemteam }">selected="selected"</c:if>>
										测试类
									</option>
								</select>
							</td>
							<td height="30" align="left" class="Input_Table_Label"
								width="10%">
								模板名称
							</td>
							<td height="30" align="left" width="40%">
								<input value="${sysTemplate.tblTemplatechname}" name="sysTemplate.tblTemplatechname" id="tblTemplatechname" class="  textInput required" style="width:180px;height:15px;" maxlength="50">
								<font color="red">*</font>
							</td>							
						</tr>

						<tr>
							<td height="30" align="left" class="Input_Table_Label"
								width="10%">
								类型
							</td>
							<td height="30" align="left" width="40%">
								<select style="width:148px;" name="sysTemplate.tblTemplatetype" id="tblTemplatetype" class="valid"><option value="0">操作区模板</option></select>
							</td>
							<td class="Input_Table_Label" align="left" width="10%">
								继承模板
							</td>
							<td align="left" width="40%">
								<select style="width:148px;" name="sysTemplate.tblMobanyongtu" id="tblMobanyongtu" class="valid" onchange="mobanyongtuChange(this)">
									<c:forEach items="${templateLibaryList}" var="library">
										<option value='${library.id}' <c:if test="${sysTemplate.tblMobanyongtu==library.id}">selected</c:if>>${library.templateChName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="Input_Table_Label" align="left" width="10%">
								模板设计
							</td>
							<td align="left" width="90%" colspan="3">
								<textarea id="tblContent" name="sysTemplate.tblContent" rows="35" cols="80" class="tinymce">${sysTemplate.tblContent}</textarea>
							</td>
						</tr>			
						<tr>
							<td class="Input_Table_Label" align="left" width="10%">
								<label>
									功能说明
								</label>
							</td>
							<td colspan="3" align="left" width="90%">
								<textarea cols="120" rows="5" name="sysTemplate.tblComment" id="tblComment" class="textInput">${sysTemplate.tblComment}</textarea>
							</td>
						</tr>
						<tr>
							<td class="Input_Table_Label" align="left" width="10%">
								<label>
									备注
								</label>
							</td>
							<td colspan="3" align="left" width="90%">
								<textarea cols="120" rows="5" name="sysTemplate.tblRemark" id="tblRemark" class="textInput">${sysTemplate.tblRemark}</textarea>
							</td>
						</tr>
					</table>
				</form>
				<br/><br/><br/>
			</div>
			<div>
				<div id="tabId" align="center">
				</div>
				<div id="tabListId" align="center">
				</div>
			</div>
			<div>
				<div id="buttonId" align="center">
				</div>
				<div id="buttonListId" align="center">
				</div>
			</div>
			<div>
				<div id="formColumnId" align="center">
				</div>
				<div id="formColumnListId" align="center">
				</div>
			</div>
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>