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
		var urlString = "<%=basePath %>/pages/resource/templateFormpinyin.action";
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
	
	if(${templateForm.tpFormDevelopPath!=null&&templateForm.tpFormDevelopPath!=""}){
		showDevelopPath(1);
	}
//-->
</script>
<form method="post" action="<c:url value='/pages/resource/templatePartitionsavePatition.action'/>" class="pageForm required-validate"
	onsubmit="return LimitAttach(this, this.patitionFile.value);"
	enctype="multipart/form-data">
	<input id="partitionId" type=hidden name="patition.id"
		value="${patition.id}" />
	<table width="98%" cellspacing="0" cellpadding="2" border="0"
		class="Input_Table">
		<tr>
			<td height="30" align="left" class="Input_Table_Label" width="15%">
				分区名称：
			</td>
			<td height="30" align="left" width="35%">
				<input id="partitionName" type="text"
					name="partition.partitionName" value="${partition.partitionName}"
					class="required" style="width: 200px;" />
				<!-- <span class="star">*</span> -->
			</td>
			<td height="30" align="left" class="Input_Table_Label" width="15%">
				分区类型：
			</td>
			<td height="30" align="left" width="35%">
				<select id="partitionType" name="partition.partitionType" style="width: 135px"
					class="requried">
					<c:forEach items="${templateTypes}" var="templateType">
						<option value="${templateType.value}"
							<c:if test="${templateType.value==templateForm.tpType }">selected="selected"</c:if>>
							${templateType.name}
						</option>
					</c:forEach>
				</select>
			</td>
		<tr>
			<td width="15%" height="30" align="left" class="Input_Table_Label">
				分区模版文件：
			</td>
			<td height="30" align="left">
				<input type="file" style="width: 300px;" name="partitionFile">
				<input type="hidden" name="partition.partitionFileName"
					value="${partition.partitionFileName}" />
			</td>
			<td width="15%" height="30" align="left" class="Input_Table_Label">
				顺序：
			</td>
			<td height="30" align="left">
				<input type="text" name="partition.showOrder" value="${partition.showOrder}" class="required number"/><!-- <span class="star">*</span> -->
			</td>
		</tr>
		<tr>
			<td height="30" align="left" class="Input_Table_Label" width="15%">
				启用开发环境：
			</td>
			<td height="30" align="left" width="35%">
				<input name="tpLisDevelop" type="radio" value="1"
					<c:if test='${partition.partitionDevelopPath!=null&&partition.partitionDevelopPath!=""}'>checked</c:if>
					onclick="showDevelopPath(1);" />
				启用
				<input name="tpLisDevelop" type="radio" value="0"
					<c:if test='${partition.partitionDevelopPath==null&&partition.partitionDevelopPath==""}'>checked</c:if>
					onclick="showDevelopPath(0);" />
				不启用
			</td>
			<td width="15%" height="30" align="left" class="Input_Table_Label">
				开发环境路径：
			</td>
			<td height="30" align="left" width="35%">
				<input id="partitionDevelopPath" type="text"
					name="partition.partitionDevelopPath" size='50' disabled
					readonly="true" value="${partition.partitionDevelopPath}" />
			</td>
		</tr>
	</table>
</form>