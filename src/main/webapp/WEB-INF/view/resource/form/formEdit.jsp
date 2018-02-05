<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
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
function makeParam() {
	var action = $("#buttonFormID").attr("action");
	if(action.indexOf("?") > 0) {
		return;
	}
	var param = encodeURI($("#formBaseId").serialize());
	$("#buttonFormID").attr("action",action + "?" + param);
}
function selfDialogAjaxDone(json) {
	var formId = json.domainId;
	$("#formId").val(formId);
	//navTab.reload("", {navTabId: json.navTabId});
	//DWZ.ajaxDone(json);
	refreshList(json);
	loadFormColumn("edit");
}

$(function(){
	$.ajaxSetup( {async : false});
	loadButton("edit");
	loadButtonList("edit");
	loadTab("edit");
	loadTabList("edit");
	loadFormColumn("edit");
	$("#maintableId").combobox();
	//将修改按钮置灰
	$("#XG").attr("disabled","disabled");
	$("#XG").attr("class","listbuttonDisable");
	
	//新增情况：将【删除】按钮设置为：不可用（disabled）
	var op = '${op}';
	if( op != null && "new" == op ){
		$("#SC").attr("disabled","disabled");
		$("#SC").attr("class","listbuttonDisable");
		setFormUISizeAndTitle();
	}
	
	$('#BC').attr('onclick', 'eventCompexFormBC();');
	
	
	$.ajaxSetup( {async : true});
	$('#yunDialog').attr('style', 'height: '+(fSelfHeight-70)+'px;overflow-x:hidden;OVERFLOW-Y:auto;');
	ns.common.mouseForButton();
	
	formbzUrl = "<%=basePath %>/pages/resource/form/showFormHelp.action";
	
});

function setFormUISizeAndTitle() {
	$("#xjTitleId").val("新建");
	$("#whTitleId").val("维护");
	$("#widthId").val(950);
	$("#heightId").val(650);
}

function getCode(){
	var urlString = "<%=basePath %>/pages/resource/form/getCode.action";
		$.ajax({
			type:'post',
			data:$("#buttonFormID").serialize(),
			url: urlString,
			success: function(data){
				$("#code").val(data);
			}
		});
}

function eventCompexFormBC(){
	try{
		var jiaoben = $('#jiaoben').val();
		if(jiaoben != ''){
			eval(jiaoben);
		}
		eventCompexBC();
	}catch(e){
		alertMsg.error("脚本设置有误,请检查!"+e);
	}
}

//-->
</script>
<div class="buttonPanel">
	<%--<button type="button" id="buttonXG" name="buttonXG" class="listbutton"  <c:if test="${op=='new' || op=='edit'}">disabled="disabled"</c:if> onClick="modify();">修 改</button>
	<button type="button" id="buttonBC" name="buttonBC" class="listbutton" onclick="save();">保 存</button>
	<button type="button" id="buttonSC" name="buttonSC" class="listbutton" >删 除</button>
	<button type="button" id="buttonDY" name="buttonDY" class="listbutton" disabled>打 印</button>
	<button type="button" id="buttonFF" name="buttonFF" class="listbutton" disabled>分 发</button>
	<button type="button" id="buttonLZ" name="buttonLZ" class="listbutton" disabled>流 转</button>
	<button type="button" id="buttonFB" name="buttonFB" class="listbutton" disabled>发 布</button>
	<button type="button" id="buttonSQ" name="buttonSQ" class="listbutton" disabled>授 权</button>
	<button type="button" id="buttonDR" name="buttonDR" class="listbutton" disabled>导 入</button>
	<button type="button" id="buttonDC" name="buttonDC" class="listbutton" disabled>导 出</button>
	<button type="button" id="buttonBZ" name="buttonBZ" class="listbutton" disabled>帮 助</button>
	--%>
	
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
<div id="yunDialog">
	<div class="tabs" id="formTab">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="selected">
						<a><span>基本信息</span>
						</a>
					</li>
					<li>
						<a><span>选项卡信息</span>
						</a>
					</li>
					<li>
						<a><span>按钮信息</span>
						</a>
					</li>
					<li>
						<a><span id="ziduanId">字段信息</span>
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div align="center">
				<form onkeydown="return enterNotSubmit(event);" id="buttonFormID"
					method="post"
					action="<%=basePath%>/pages/resource/form/save.action"
					class="pageForm required-validate"
					onsubmit="return validateCallback(this, selfDialogAjaxDone);">
					<div style="display: none;">
						<input id="formId" type=hidden name="form.id" value="${form.id}" />
						<input id="buttonSubmit" type="submit" name="submit" />
						<input id="domainSubmit" type="hidden" value="buttonSubmit" />
					</div>
					<%--      	<form id="formBaseId" name="form" method="post">--%>
					<table width="100%" cellspacing="0" cellpadding="2" border="0"
						class="Input_Table">
						<tr>
							<td width="15%" height="30" align="left"
								class="Input_Table_Label">
								表单名称
							</td>
							<td height="30" align="left" width="35%">
								<input type="text" name="form.formName" value="${form.formName}" class="textInput required" maxlength="50" style="width: 180px" />
								<input type="hidden" name="form.systemTeam" value="${form.systemTeam}" >
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td style="border: 0px;" align="left">
											<!-- <span class="star">*</span> -->
										</td>
									</tr>
								</table>
							</td>
							<td width="15%" height="30" align="left"
								class="Input_Table_Label">
								主表
							</td>
							<td height="30" align="left" width="35%">
								<select id="maintableId" name="form.tableId"
									style="width: 186px">
									<c:forEach items="${tables}" var="table">
										<option value="${table.id}" <c:if test="${table.id==form.tableId }">selected="selected"</c:if>>${table.tableZhName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td width="15%" height="30" align="left"
								class="Input_Table_Label">
								宽度
							</td>
							<td height="30" align="left" width="35%">
								<input id="widthId" type="text" name="form.width"
									value="${form.width}" class="textInput required number"
									maxlength="5" style="width: 180px" />
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td style="border: 0px;" align="left">
											<!-- <span class="star">*</span> -->
										</td>
									</tr>
								</table>
							</td>
							<td height="30" align="left" class="Input_Table_Label"
								width="15%">
								高度
							</td>
							<td height="30" align="left" width="35%">
								<input id="heightId" type="text" name="form.height"
									value="${form.height}" class="textInput required number"
									maxlength="5" style="width: 180px" />
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td style="border: 0px;" align="left">
											<!-- <span class="star">*</span> -->
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="15%" height="30" align="left"
								class="Input_Table_Label">
								新建标题
							</td>
							<td height="30" align="left" width="35%">
								<input id="xjTitleId" type="text" name="form.xjTitle"
									value="${form.xjTitle}" maxlength="20" style="width: 180px" />
							</td>
							<td height="30" align="left" class="Input_Table_Label"
								width="15%">
								维护标题
							</td>
							<td height="30" align="left" width="35%">
								<input id="whTitleId" type="text" name="form.whTitle"
									value="${form.whTitle}" maxlength="20" style="width: 180px" />
							</td>
						</tr>
						<tr>
							<td class="Input_Table_Label" align="left">
								<label>
									&nbsp;脚本设置
								</label>
							</td>
							<td colspan="3" align="left">
								<textarea id="jiaoben" name="form.jiaoben" rows="8" cols="103"
									class="textInput">${form.jiaoben}</textarea>
							</td>
						</tr>
						<tr>
							<td class="Input_Table_Label" align="left">
								<label>
									&nbsp;功能说明
								</label>
							</td>
							<td colspan="3" align="left">
								<textarea id="remarks" name="form.remarks" rows="8" cols="103" class="textInput">${form.remarks}</textarea>
							</td>
						</tr>
						<c:if test="${form.id==null}">
						<tr>
							<td class="Input_Table_Label" align="left">
								<label>
									&nbsp;是否创建关联列表
								</label>
							</td>
							<td align="left" colspan="3">
								<input type="radio" name="createListAndCatalog" value="1">是
								<input type="radio" name="createListAndCatalog" value="0" checked="checked">否
							</td>
						</tr>
						</c:if>
<%--						<tr>--%>
<%--							<td class="Input_Table_Label" align="left">--%>
<%--								<label>--%>
<%--									&nbsp;备注--%>
<%--								</label>--%>
<%--							</td>--%>
<%--							<td colspan="3" align="left">--%>
<%--								<textarea id="desc" name="form.desc" rows="3" cols="109"--%>
<%--									class="textInput">${for
m.desc}</textarea>--%>
<%--							</td>--%>
<%--						</tr>--%>
					</table>
					<%--		  	 </form>--%>
				</form>
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
