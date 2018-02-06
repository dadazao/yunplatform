<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	extArray = new Array(".gif", ".jpg", ".png",".bmp");
	function LimitAttach(form, file1) {
		var allowSubmit = false;
		if(file1){
			var tempext=file1.slice(file1.indexOf(".")).toLowerCase();
			if(tempext!=".jsp"){
				alertMsg.warn("模板文件只能上传:  .jsp\n请重新选择文件再上传.");
				return false;
			}
		}
		return iframeCallback(form,selfDialogAjaxDone);
	}

	function selfDialogAjaxDone(json) {
		$.pdialog.closeCurrent();
		if(json.success == 'true') {
			alertMsg.correct("操作成功");
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		}else{
			if(json.indexOf("success") > 0) {
				alertMsg.correct("操作成功");
				navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
			}else{
				alertMsg.error("操作失败");
			}		
		}
	}
	
	function setPinyin() {
		var urlString = "<%=basePath %>/pages/resource/template/Formpinyin.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"tpFormChName="+$("#tpFormChName").val(),
			success: function(data){
				$("#tpFormName").html(data);
				genPath();
			}
		});
	}
	
	function showDevelopPath(b) {
		if(b==1){
			$("#tpFormDevelopPath").removeAttr("disabled");
			$("#tpFormDevelopPath").attr("class","listbutton");
			$("#tpFormDevelopPath").removeAttr("readonly");
			$("#tpFormDevelopPath").removeClass("readonly");
		}else{
			$("#tpFormDevelopPath").attr("disabled","disabled");
			$("#tpFormDevelopPath").attr("class","listbuttonDisable");
			$("#tpFormDevelopPath").attr("readonly","true");
			$("#tpFormDevelopPath").addClass("readonly");
		}
	}
	function genPath() {
		
		var name=$("#tpFormName  option:selected").val();
		var urlString = "<%=basePath %>/pages/system/"+name+"/edit.action?model="+name;
		$("#biaodanlujing").val(urlString);
	}
	function publish() {
		var urlString = "<%=basePath %>/pages/resource/table/publish.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tableId").val(),
			success: function(data){
				alertMsg.correct("操作成功");
			}
		});
	}
	
	function showTemplate(obj){
		var urlString = "<%=basePath %>/pages/resource/partition/showTemplate.action?partitionType=" + obj;
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#templateSpan").html(data);
			}
		});
	}
	
	if(${templateForm.tpFormDevelopPath!=null&&templateForm.tpFormDevelopPath!=""}){
		showDevelopPath(1);
	}
//-->
</script>
<form id="partitionForm" name="partitionForm" class="pagetabulation required-validate" method="post">
	<input id="partitionId" type="hidden" name="partition.id" value="${partition.id}" />
	<table width="98%" cellspacing="0" cellpadding="2" border="0"
		class="Input_Table">
		<tr>
			<td height="30" align="left" class="Input_Table_Label" width="10%">
				分区名称
			</td>
			<td height="30" align="left" width="40%">
				<input id="partitionName" type="text" style="width:180px;height:15px;"
					name="partition.partitionName" value="${partition.partitionName}"
					class="required" />
				<!-- <span class="star">*</span> -->
			</td>
			<td height="30" align="left" class="Input_Table_Label" width="10%">
				分区类型
			</td>
			<td height="30" align="left" width="40%">
				<select id="partitionType" name="partition.partitionType" style="width: 186px"
					class="requried" onchange="showTemplate(this.value)">
					<c:forEach items="${partitionTypes}" var="partitionType">
						<option value="${partitionType.value}"
							<c:if test="${partitionType.value==partition.partitionType }">selected="selected"</c:if>>
							${partitionType.name}
						</option>
					</c:forEach>
				</select>
			</td>
		<tr>
			<td width="10%" height="30" align="left" class="Input_Table_Label">
				选择模板
			</td>
			<td height="30" align="left">
				<span id="templateSpan">
					<select name="partition.baseTemplate" style="width:186px">
						<option value=""></option>
						<c:forEach items="${templates}" var="template">
							<option value="${template.id}" <c:if test="${template.id==partition.baseTemplate }">selected="selected"</c:if>>${template.templateChName}</option>
						</c:forEach>
					</select>
				</span>
			</td>
			<td width="10%" height="30" align="left" class="Input_Table_Label">
				顺序
			</td>
			<td height="30" align="left">
				<input type="text" name="partition.showOrder" value="${partition.showOrder}" class="required number" style="width:180px;height:15px;"/><!-- <span class="star">*</span> -->
			</td>
		</tr>
	</table>
	<div align="right" style="margin-top: 5px;margin-bottom: 5px;">
		<input type="button" name="add" class="listbutton" value="添加" onclick="addPartition();">
		<input type="button" name="save" class="listbutton" value="保存" onclick="updatePartition();">
		<input type="button" name="delete" class="listbutton" value="删除" onclick="deletePartition();">
	</div>
</form>