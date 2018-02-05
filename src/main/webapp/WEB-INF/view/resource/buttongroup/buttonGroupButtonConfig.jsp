<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<html>
	<head>
		<script type="text/javascript">
$(function() {
	$.ajaxSetup( {async : false});
	$('#divlist')
			.loadUrl("<%=basePath %>/pages/resource/buttonAndGrouplist.action?buttonGroupID="+$("#domainId").val(),
					"", function() {

					});
	$("#buttonBC").click(function() {
		buttonGroupSave();
	});
	loadButtonCombox();
	showButtonComment($("#buttonID option:selected").val());
	$.ajaxSetup( {async : true});
});
//加载按钮
function loadButtonCombox() {
	$.getJSON('<%=basePath %>/pages/resource/buttonlist.action', {},
			function(data) {
				if (data != null && data.length > 0) {
					for ( var nCount = 0; nCount < data.length; nCount++) {
						$('#buttonID')
								.append(
										"<option value=\"" + data[nCount].id
												+ "\" >"
												+ data[nCount].buttonName
												+ "</option>");
					}
				}
			});
}

function freshAjaxDone(json){
	DWZ.ajaxDone(json);
	$('#divlist').loadUrl("<%=basePath %>/pages/resource/buttonAndGrouplist.action?buttonGroupID="
									+ $("#domainId").val(), "", function() {
								$("#buttonID").attr("value", "-1");
								$("#buttonAndGroupID").attr("value", "");
								//$("#buttonDisplayOrder").attr("value", "");
							});
	loadButtonCombox();
	showButtonComment($("#buttonID option:selected").val());
}
function batchdel() {
	var urlString = "<%=basePath %>/pages/resource/buttonAndGroupbatchDel.action";
	var param = $("#buttonAndGroupForm").serialize();
	var result = urlString + "?" + param;
	var items = $("input[name='checkboxAndGroupIDs']:checked").length;
	if(items == 0){
		alertMsg.info("请选择要删除的数据!");
		return;
	}
	alertMsg.confirm("确定要删除吗?", {
		okCall : function() {
			$.ajax({
		  		type:'POST',
		  		url:result,
		  		async:false,
		  		success:function(data){
				
		  		}
		  	});
			$('#divlist')
				.loadUrl(
						"<%=basePath %>/pages/resource/buttonGroupfindByIDForButtonConfig.action?buttonGroupID="+$("#domainId").val(),
						"", function() {
						});
		}
	});
}

function showButtonComment(value){
	$.ajax({
  		type:'POST',
  		url:'<%=basePath %>/pages/resource/buttonActionfindButtonByID.action?id=' + value,
  		dataType:'json',
  		success:function(data){
			$("#buttonCommentId").html(data.comment);
			$("#formbuttonshowName").val(data.buttonName);
  		}
  	});
}

function addButton() {
	$("#buttonAndGroupID").val("");
}
</script>
	</head>
	<body>
		<!-- Tab内容,Tab1为按钮组信息,Tab2为按钮信息 -->
			<div id="Tab2">
				<form method="post" action="<%=basePath %>/pages/resource/buttonAndGroupsave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, freshAjaxDone);">
				<input id="buttonGroupID" name="buttonAndGroup.buttonGroupID" type="hidden">
				<input id="buttonAndGroupID" name="buttonAndGroup.id" type="hidden">
				<table align=center style="width: 100%;" cellspacing="0"
					cellpadding="2" border="0" class="Input_Table">
					<tr height=20>
						<td style="width: 10%;" class="Input_Table_Label">
							<label>
								&nbsp;选择按钮
							</label>
						</td>
						<td style="width: 40%;">
							<select id="buttonID" name="buttonAndGroup.buttonID"
								style="width: 186px" onchange="showButtonComment(this.value);"></select>
						</td>
						<td style="width: 10%;" class="Input_Table_Label">
							<label>
								&nbsp;显示次序
							</label>
						</td>
						<td style="width: 40%;">
							<input type="text" id="buttonDisplayOrder"
								name="buttonAndGroup.buttonDisplayOrder" style="width: 180px;" maxlength="5" class="required number"/>
								<!-- <span class="star">*</span> -->
						</td>
					</tr>

					<tr>
						<!-- 
							<td style="width: 80px;" class="Input_Table_Label">
								<label>
									&nbsp;启用状态
								</label>
							</td>
							<td>
								<select id="buttonEnabledStatus"
									name="buttonAndGroup.buttonEnabledStatus" style="width: 186px">
									<option value="1">
										启用
									</option>
									<option value="2">
										不启用
									</option>
								</select>
							</td>
						 -->
						<td style="width: 80px;" class="Input_Table_Label">
							<label>
								&nbsp;显示状态
							</label>
						</td>
						<td colspan="3">
							<select id="buttonDisplayStatus"
								name="buttonAndGroup.buttonDisplayStatus" style="width: 186px">
								<option value="1">
									显示
								</option>
								<option value="2">
									隐藏
								</option>
							</select>
						</td>
					</tr>
					<tr height=20>
						<td style="width: 80px;" class="Input_Table_Label">
							<label>
								&nbsp;按钮功能说明
							</label>
						</td>
						<td colspan="3">
							<div id="buttonCommentId" style="line-height:25px;"></div>
						</td>
					</tr>
				</table>
				<table align="right" style="width: 100%;" cellspacing="0"
					cellpadding="2" border="0">
					<tr height="25">
						<td align=right>
							<button type="submit" id="btnSave" name="btnSave" class="listbutton" onclick="addButton();">
								添加
							</button>
							<button type="submit" id="btnSave" name="btnSave" class="listbutton">
								保 存
							</button>
							&nbsp;
							<button type="button" id="btnDel" name="btnDel" class="listbutton"
								onClick="batchdel();">
								删 除
							</button>
						</td>
					</tr>
				</table>
				</form>
				<input type="hidden" id="buttonEnabledStatus"
					name="buttonAndGroup.buttonEnabledStatus" value="1">
				<div style="height: 2px;"></div>
				<div id="divlist"></div>
			</div>
	</body>
</html>
