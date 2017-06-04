<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	function subTable() {
		var urlString = "<%=basePath %>/pages/resource/columnsubTable.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tableId option:selected").val(),
			success: function(data){
				$('#pinyinId').val(data+"_");
			}
		});
	}
	function showColumnInput() {
		if($("#columnNameSelect option:selected").val() == '-1'){
			$("#pinyinId").append("<input size='15' class='textInput' maxlength='50' id='columnNameId' name='columnName' /><font id='columnNameRequired' color='red'>*</font>");
			$("#columnRequired").remove();
		}else{
			$("#columnNameId").remove();
			$("#columnNameRequired").remove();
			if($("#columnRequired").html() != "*") {
				$("#pinyinId").append("<font id='columnRequired' color='red'>*</font>")	
			}
		}
			
	}
	
	function checkDBData(value){
		if(value==0 || value=='0'){
			$("#defaultValue").addClass("required");
		}else if(value==1 || value=='1'){
			$("#defaultValue").removeClass("required");
		}
	}
	
	var op="";
	$(function(){
		var name = $("#columnNameId").val();
		if(name != "") {
			$("#columnNameId").show();
			$("#columnNameSelect").hide();
			$("#columnNameSelect").removeClass("required");
			$("#columnNameId").addClass("required");
		}

		var edit = "${op}";
		if(edit == 'edit') {
			$("#tableSelectId").html("");
			$("#tableSelectId").hide();
			$("#prefix").html("");
			$("#BCBXZ").hide();
			$("#pinyinId").attr("maxlength",28);
		}else{
			$("#tableInputId").html("");
			$("#tableInputId").hide();
			$("#pinyinId").attr("style","width:157px;");
			$("#BC").hide();
		}
		
		
		checkDBData('${column.isNullable}');
		$("#tableId").combobox({size:21,required:'required'});
		$("#XG").attr("disabled","disabled");
		$("#SC").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		$("#SC").attr("class","listbuttonDisable");
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		ns.common.mouseForButton();
	});
	
//-->
</script>
<div style="display:none;">
	<input id="domainSubmit" type="submit" value="columnSubmit"/>
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
<form onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/resource/columnsave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div style="display:none;">
		<input name="tableSubmit" id="columnSubmit" type="submit">
	</div>
	<div style="height:2px;"></div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
	                        <li class="selected"><a><span>基本信息</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
				
					<input id="columnId" type=hidden name="column.id" value="${column.id}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								所属表
							</td>
							<td align="left" width="40%">
								<span id="tableSelectId" style="float:left;">
									<select id="tableId" name="column.tableId" style="width:186px" >
										<c:forEach items="${tables}" var="table">
											<option value="${table.id}" <c:if test="${column.tableId==table.id }">selected="selected"</c:if>>${table.tableZhName}</option>
										</c:forEach>
									</select>
								</span>
								<span id="tableInputId">
									<input style="width:180px;float:left;" type="text" disabled="disabled" value='<c:forEach items="${tables}" var="table"><c:if test="${column.tableId==table.id }">${table.tableZhName}</c:if></c:forEach>'>
									<input type="hidden" name="column.tableId" value='<c:forEach items="${tables}" var="table"><c:if test="${column.tableId==table.id }">${table.id}</c:if></c:forEach>'>
								</span>
								<input type="hidden" name="column.belongTable" value='<c:forEach items="${tables}" var="table"><c:if test="${column.tableId==table.id }">${table.tableName}</c:if></c:forEach>'>
								<!-- <span class="star">*</span> -->
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								字段拼音名
							</td>
							<td align="left">
								<span id="prefix" style="float: left;height:25px;line-height:25px;">tbl_</span>
								<input id="pinyinId" maxlength="24" type="text" name="column.columnName" value="${column.columnName}"  class="textInput required pinyin" style="width:180px;float:left;"/><!-- <span class="star">*</span> -->
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								字段中文名
							</td>
							<td align="left" width="40%">
								<input id="columnZhName" maxlength="50" type="text" name="column.columnZhName" value="${column.columnZhName}"  class="textInput required" style="width:180px;"/><!-- <span class="star">*</span> -->
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								字段英文名
							</td>
							<td align="left" width="40%">
								<input id="columnEnName" maxlength="50" type="text" name="column.columnEnName" value="${column.columnEnName}"  class="textInput required" style="width:180px;"/><!-- <span class="star">*</span> -->
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label">
								数据类型
							</td>
							<td align="left">
								<select name="column.dataType" style="width:186px">
									<c:forEach items="${dataTypeList}" var="dataType">
										<option value="${dataType.value}" <c:if test="${column.dataType==dataType.value }">selected="selected"</c:if>>${dataType.name}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								数据长度
							</td>
							<td align="left">
								<input type="text" maxlength='5' name="column.length" value="${column.length}"  style="width:180px" class="number"/>
							</td>
						</tr>
<%--						<tr>--%>
<%--							<td  align="left" class="Input_Table_Label">--%>
<%--								显示规则--%>
<%--							</td>--%>
<%--							<td align="left">--%>
<%--								<select name="column.showRule" style="width:186px">--%>
<%--									<c:forEach items="${showRuleList}" var="showRule">--%>
<%--										<option value="${showRule.value}" <c:if test="${column.showRule==showRule.value }">selected="selected"</c:if>>${showRule.name}</option>--%>
<%--									</c:forEach>--%>
<%--								</select>--%>
<%--							</td>--%>
<%--							<td align="left" class="Input_Table_Label">验证值</td>--%>
<%--							<td align="left"><input type="text" name="column.checkValue" value="${column.checkValue}" /></td>--%>
<%--						</tr>--%>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								是否空值
							</td>
							<td align="left" width="40%">
								<input type="radio" name="column.isNullable" onclick="checkDBData(1)" value="1" <c:if test="${column.isNullable==1 }">checked="checked"</c:if> />是
								<input type="radio" name="column.isNullable" onclick="checkDBData(0)" value="0" <c:if test="${column.isNullable==0 }">checked="checked"</c:if> />否
							</td>
							<td align="left" class="Input_Table_Label">
								数据库默认值
							</td>
							<td align="left">
								<input id="defaultValue" type="text" maxlength='50' name="column.defaultValue" value="${column.defaultValue}"  style="width:180px"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								功能说明
							</td>
							<td colspan="3" align="left">
								<textarea name="column.comment" rows="5" cols="103" class="textInput">${column.comment}</textarea><!-- <span class="star">*</span> -->
							</td>
						</tr>
						<tr>
							<td  align="left" class="Input_Table_Label">
								备注
							</td>
						
							<td colspan="3" align="left">
								<textarea name="column.remark" class="textInput" rows="5" cols="103">${column.remark}</textarea>
							</td>
						</tr>
					</table>
			</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div>
	</div>
</form>
