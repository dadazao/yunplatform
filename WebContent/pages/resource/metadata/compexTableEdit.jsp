<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	function tableDialogAjaxDone(json){
		$("#tableId").val(json.domainId);
		refreshList(json);
		loadTableColumnList();
	}
	function setPinyin() {
		var urlString = "<%=basePath %>/pages/resource/tablepinyin.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"tableZhName="+$("#tableZhName").val(),
			success: function(data){
				$('#pinyinId').html(data);
				showTableInput();
			}
		});
	}
	
	function publish() {
		var urlString = "<%=basePath %>/pages/resource/tablepublish.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tableId").val(),
			success: function(data){
				alertMsg.correct("操作成功");
			}
		});
	}
	
	function showTableInput() {
		if($("#tableNameSelect option:selected").val() == '-1'){
			$("#pinyinId").append("<input id='tableNameId' size='15' maxlength='50' class='textInput required' name='tableName' /><font id='tableNameRequired' color='red'>*</font>");
			$("#tableRequired").remove();
		}else{
			$("#tableNameId").remove();
			$("#tableNameRequired").remove();
			if($("#tableRequired").html() != "*") {
				$("#pinyinId").append("<font id='tableRequired' color='red'>*</font>")	
			}
		}
			
	}
	
	function addPrex(){
		var op = '${op}';
		if(op=='new') {
			$('#prefix').html("bus_");
		}
	}
	
	$(function(){
		$("#XG").attr("disabled","disabled");
		$("#SC").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		$("#SC").attr("class","listbuttonDisable");
		
		var op = '${op}';
		if(op=='new') {
			$("#commonColumnId").html('');
			//设置表前缀
			addPrex();
		}else if(op=='edit'){
			$("#commonColumnYes").attr("disabled","disabled");
			$("#commonColumnNo").attr("disabled","disabled");
			$("#tableNameId").attr("style","width:180px;");
			$("#tableNameId").attr("maxlength",28); 
		}
		
		var name = $("#tableNameId").val();
		if(name != "") {
			$("#tableNameId").show();
			$("#tableNameSelect").hide();
			$("#tableNameSelect").removeClass("required");
			$("#tableNameId").addClass("required");
		}
		
		if($("#tableId").val()!=""){
			$("#tableNameId").attr("readonly","readonly");
		}
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		loadTableColumnForm();
		loadTableColumnList();
		ns.common.mouseForButton();
	});
//-->
</script>
<div id="tableEdit">
	<div style="display:none;">
		<input id="domainSubmit" type="submit" value="tableSubmit"/>
		<input type="hidden" id="isFirstTab" value="true">
		<input id="formId" name="formId" value="${formId}"/>
		<input id="domainId" name="domainId" value="${domainId}"/>
		<input id="paramsId" name="params" value="${params}"/>
		<input id="mainTable" name="mainTable" value="${mainTable}"/>
	</div>
	<div  class="buttonPanel">
		<c:forEach items="${formButtons}" var="formButton" varStatus="status">
			<c:if test="${formButton.hasAuth=='0'}">
				<c:if test="${formButton.buttonType == '0'}">
					<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbutton" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;" onclick="eventCompex${formButton.funcName}();" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
				</c:if>
			</c:if>
			<c:if test="${formButton.hasAuth=='1'}">
				<c:if test="${formButton.buttonType == '0'}">
					<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbuttonDisable" disabled="disabled" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
				</c:if>
			</c:if>
		</c:forEach>
	</div>
		<div style="height:2px;"></div>
		<div class="tabs">
		      <div class="tabsHeader">
		            <div class="tabsHeaderContent">
		                  <ul>
		                        <li class="selected"><a><span>基本信息</span></a></li>
		                        <li class="selected"><a><span>字段信息</span></a></li>
		                  </ul>
		            </div>
		      </div>
		      <div class="tabsContent" >
			  		<div align="center">
			  			<form onkeydown="return enterNotSubmit(event);" method="post" action="<c:url value='/pages/resource/tablesave.action'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, tableDialogAjaxDone);">
			  			<div style="display:none;">
							<input name="tableSubmit" id="tableSubmit" type="submit">
							<input name="op" type="hidden" value="${op}">
						</div>
						<input id="tableId" type=hidden name="table.id" value="${table.id}"/>
				  	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
							<tr>
<!-- 								<td align="left" class="Input_Table_Label">所属分组</td> -->
<!-- 								<td align="left" valign="middle"> -->
<!-- 									<select id="groupId" name="table.group" style="width:186px" onchange="addPrex();"> -->
<!-- 										<c:forEach items="${groupList}" var="group"> -->
<!-- 											<option value="${group.value}" <c:if test="${table.group==group.value }">selected="selected"</c:if>>${group.name}</option> -->
<!-- 										</c:forEach> -->
<!-- 									</select> -->
<!-- 								</td> -->
								<td align="left" class="Input_Table_Label" width="10%">
									表中文名
								</td>
								<td align="left" width="40%">
									<input id="tableZhName" maxlength="50" type="text" name="table.tableZhName"  value="${table.tableZhName}" class="required" style="width:180px"/><!-- <span class="star">*</span> -->
								</td>	
								<td width="10%" align="left" class="Input_Table_Label">
									表拼音名
								</td>
								<td align="left" width="40%">
									<span id="prefix" style="float:left;height:25px;line-height:25px;"/>
									<input id="tableNameId" style="width:150px" type="text" class="required pinyin" maxlength="24" name="table.tableName"  value="${table.tableName}"/>
									<!-- <span class="star">*</span> -->
								</td>								
							</tr>
							<tr>	
								<td align="left" class="Input_Table_Label" width="10%">
									表英文名
								</td>
								<td align="left" width="40%">
									<input id="tableEnName" maxlength="50" type="text" name="table.tableEnName"  value="${table.tableEnName}" class="required" style="width:180px"/><!-- <span class="star">*</span> -->
								</td>
								<td  align="left" class="Input_Table_Label">
									公有字段
								</td>
								<td align="left" colspan="3">
									<input id="commonColumnYes" type="radio" name="table.hasCommonColumn" value="1" <c:if test="${table.hasCommonColumn==1 }">checked="checked"</c:if> />包含
									<input id="commonColumnNo" type="radio" name="table.hasCommonColumn" value="0" <c:if test="${table.hasCommonColumn==0 }">checked="checked"</c:if> />不包含
									<div id="commonColumnId">
										<input type="hidden" name="table.hasCommonColumn" value="${table.hasCommonColumn}"/>
									</div>
								</td>	
							</tr>
							<tr>
								<td align="left" class="Input_Table_Label">
									类型
								</td>
								<td align="left">
									<select name="table.tableType" style="width:186px">
										<c:forEach items="${tableTypeList}" var="tableType">
											<option value="${tableType.value}" <c:if test="${table.tableType==tableType.value }">selected="selected"</c:if>>${tableType.name}</option>
										</c:forEach>
									</select>
								</td>		
								<td align="left" class="Input_Table_Label" >
									所属库
								</td>
								<td align="left" >
									<select name="table.tableSchema" style="width:186px">
										<c:forEach items="${tableSchemaList}" var="tableSchema">
											<option value="${tableSchema.value}" <c:if test="${table.tableSchema==tableSchema.value }">selected="selected"</c:if>>${tableSchema.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td align="left" class="Input_Table_Label">功能说明</td>
								<td align="left" valign="middle" colspan="3">
									<textarea  rows="8" cols="103" name="table.tableFunction" class="textInput">${table.tableFunction}</textarea>
									<!-- <span class="star">*</span> -->	
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td  align="left" class="Input_Table_Label">备注</td> -->
<!-- 								<td  align="left" colspan="3"> -->
<!-- 									<textarea  rows="4" cols="103" name="table.remark">${table.remark}</textarea> -->
<!-- 								</td> -->
<!-- 							</tr>	 -->
						</table>
						</form>
				</div>
				<div>
					<div id="table_column_form_div" align="center"></div>
					<div id="table_column_list_div" align="center"></div>
				</div>
			</div>
			<!-- Tab结束层 -->
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
</div>

