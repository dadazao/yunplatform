<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<script type="text/javascript">
<!--
function showDownText() {
	if($("#inputTypeId").val() == '2'){
		$("#downTextId").show();
		$("#codeId").show();
	}else{
		$("#downTextId").hide();
		$("#codeId").hide();
		$("#mainSubId").hide();
	}
}
function showTree() {
	if($("#downTextId").val() == '1'){
		$("#mainSubId").show();
		$("#codeId").hide();
	}
}
function save() {
	$.ajaxSetup({async: false});
	$("#buttontabulationIdFormID").submit();
	$.ajaxSetup({async: true});
	//loadTabulationDetails();
}
function selfDialogAjaxDone(json) {
	var tabulationId = json.domainId;
	$("#tabulationId").val(tabulationId);
	loadTabulationQuery();
	//navTab.reload("", {nav: json.navTabId});
	//DWZ.ajaxDone(json,refreshList);
	refreshList(json);
}

function getCode(){
	var urlString = "<%=basePath %>/pages/resource/tabulationgetCode.action?tabulationName="+$("#tabulationName").val();
		$.ajax({
			type:'post',
			data:$("#buttontabulationIdFormID").serialize(),
			url: urlString,
			success: function(data){
				$("#code").val(data);
			}
		});
}

$(function(){
	loadButton("edit");
	loadButtonList("edit");
	//loadTabulationDetails();
	//loadtabulationColumn();
	//loadtabulationColumnList();
	loadTabulationOpt("edit");
	loadTabulationOptList("edit");
	loadTabulationQuery("edit");
	loadTabulationQueryList("edit");
	$("#_tabulationFormId").combobox({size:21});
	//将修改按钮置灰
	$("#XG").attr("disabled","disabled");
	$("#XG").attr("class","listbuttonDisable");
	
	//新增情况：将【删除】按钮设置为：不可用（disabled）
	var op = '${op}';
	if( op != null && "new" == op ){
		$("#SC").attr("disabled","disabled");
		$("#SC").attr("class","listbuttonDisable");
	}
	$('#yunDialog').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
	ns.common.mouseForButton();
	
	formbzUrl = "<%=basePath %>/pages/resource/tabulationshowFormHelp.action";
});

//-->
</script>
<div id="yunDialog">
 	 <div  class="buttonPanel">
<%--		<button type="button" id="buttonXG" name="buttonXG" class="listbutton"  <c:if test="${op=='new' || op=='edit'}">disabled="disabled"</c:if> onClick="modify();">修 改</button>--%>
<%--		<button type="button" id="buttonBC" name="buttonBC" class="listbutton"  onclick="save();">保 存</button>--%>
<%--		<button type="button" id="buttonSC" name="buttonSC" class="listbutton" >删 除</button>--%>
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
<div class="tabs">
      <div class="tabsHeader">
            <div class="tabsHeaderContent">
                  <ul>
                  	<li class="selected"><a><span>基本信息</span></a></li>
                  	<li><a><span>按钮信息</span></a></li>
                    <li><a><span>操作信息</span></a></li>
                    <li><a><span>筛选条件</span></a></li>
                  </ul>
            </div>
      </div>
      <div class="tabsContent" >
      	<div align="center">
      		<form id="buttontabulationIdFormID" onkeydown="return enterNotSubmit(event);" method="post" action="<%=basePath %>/pages/resource/tabulationsave.action" class="pagetabulation required-validate" onsubmit="return validateCallback(this, selfDialogAjaxDone);">
			<div style="display:none;">	
				<input id="tabulationId" type=hidden name="tabulation.id" value="${tabulation.id}"/>
				<input id="buttontabulationSubmit" type="submit" name="submit"/>
				<input id="domainSubmit" type="hidden" value="buttontabulationSubmit"/>
			</div>
      		<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		  	 		<tr>
		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
							列表名称
						</td>
						<td height="30" align="left" width="40%">
							<input type="text" id="tabulationName" name="tabulation.tabulationName" value="${tabulation.tabulationName}" class="textInput required" style="width: 180px;" maxlength="50"/><!-- <span class="star">*</span> -->
						</td>
						<td height="30" align="left" class="Input_Table_Label" width="10%">
							关联表单
						</td>
						<td height="30" align="left">
							<select id="_tabulationFormId" name="tabulation.formId" style="width:186px" class="required">
								<option value="-1">无</option>
								<c:forEach items="${forms}" var="form">
									<option value="${form.id}" <c:if test="${form.id==tabulation.formId }">selected="selected"</c:if>>${form.formName}</option>
								</c:forEach>
							</select>
						</td>
					<tr>
		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
							是否显示【选择】列
						</td>
						<td height="30" align="left" width="40%">
							<input type="radio" name="tabulation.isSelect" value="1" <c:if test="${tabulation.isSelect==1 }">checked="checked"</c:if> />是
							<input type="radio" name="tabulation.isSelect" value="0" <c:if test="${tabulation.isSelect==0 }">checked="checked"</c:if> />否
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							是否显示【序号】列
						</td>
						<td height="30" align="left" width="40%">
							<input type="radio" name="tabulation.isNumber" value="1" <c:if test="${tabulation.isNumber==1 }">checked="checked"</c:if> />是
							<input type="radio" name="tabulation.isNumber" value="0" <c:if test="${tabulation.isNumber==0 }">checked="checked"</c:if> />否
						</td>
						
		  	 		</tr>
		  	 		<tr>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							是否显示【操作】列
						</td>
						<td height="30" align="left" width="40%">
							<input type="radio" name="tabulation.isModify" value="1" <c:if test="${tabulation.isModify==1 }">checked="checked"</c:if> />是
							<input type="radio" name="tabulation.isModify" value="0" <c:if test="${tabulation.isModify==0 }">checked="checked"</c:if> />否
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							维护按钮名称
						</td>
						<td height="30" align="left" width="40%">
							<input type="text" name="tabulation.modifyName" value='${tabulation.modifyName==undefined?"维护":tabulation.modifyName}'/>
						</td>
		  	 		</tr> 
				   <tr>
				   	 	<td class="Input_Table_Label" align="left">
				   	 		<label>列表组件</label>
				   	 	</td>
				   	 	<td align="left">
				   	 		<select name="tabulation.listControlId" style="width:186px">
<%--				   	 			<option value="">无</option>--%>
								<c:forEach items="${listControls}" var="listControl">
									<option value="${listControl.id}" <c:if test="${listControl.id==tabulation.listControlId }">selected="selected"</c:if>>${listControl.listControlName}</option>
								</c:forEach>
							</select>
				   	 	</td>
				   	 	<td class="Input_Table_Label" align="left">
				   	 		<label>查询组件</label>
				   	 	</td>
				   	 	<td align="left">
				   	 		<select name="tabulation.queryControlId" style="width:186px">
<%--				   	 			<option value="">无</option>--%>
								<c:forEach items="${queryControls}" var="queryControl">
									<option value="${queryControl.id}" <c:if test="${queryControl.id==tabulation.queryControlId }">selected="selected"</c:if>>${queryControl.queryControlName}</option>
								</c:forEach>
							</select>
				   	 	</td>
				   </tr>
				   <tr>
				   	 	<td class="Input_Table_Label" align="left">
				   	 		<label>高级查询组件</label>
				   	 	</td>
				   	 	<td align="left" colspan="3">
				   	 		<select name="tabulation.advanceQueryControlId" style="width:186px">
				   	 			<option value="-1">无</option>
								<c:forEach items="${advanceQueryControls}" var="queryControl">
									<option value="${queryControl.id}" <c:if test="${queryControl.id==tabulation.advanceQueryControlId }">selected="selected"</c:if>>${queryControl.queryControlName}</option>
								</c:forEach>
							</select>
				   	 	</td>
				   </tr>
				   <tr>
				     <td class="Input_Table_Label" align="left"><label>&nbsp;功能说明</label></td>
				     <td colspan="3" align="left"><textarea id="remarks" name="tabulation.remarks" class="textInput required" rows="6" cols="103" >${tabulation.remarks}</textarea><!-- <span class="star">*</span> --></td>
				   </tr>
		  	 	</table>
			</form>
      	</div>
      	<div>
      		<div id="buttonId" align="center">
			</div>
			<div id="buttonListId" align="center">
			</div>
      	</div>
		<div>
			<div id="tabulationOptId" align="center"></div>
			<div id="tabulationOptListId" align="center"></div>
		</div>
		<div>
			<div id="tabulationQueryId" align="center"></div>
			<div id="tabulationQueryListId" align="center"></div>
		</div>
	</div>
	<!-- Tab结束层 -->
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>
</div>