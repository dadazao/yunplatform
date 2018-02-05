<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
$(function (){
	var type = "${tab.type}";
	if(type == '') {
		type = 0;
	}
	$.ajaxSetup({async : false});
	showTemplate(type,0);
	$.ajaxSetup({async : true});
});

//获得缺省的字体
function loadDefaultFont() {
	$.ajax({
		type:'POST',
		url:'<%=basePath%>/pages/deployment/fontload.action',
	    dataType:'json',
		success:function(data){
			$("#tabFontStyle").val(data.fontFamily);
			$("#tabFontColor").val(data.color);
		}
	});
}

function showTemplateType(type){
	var templatetype=$("#tabtemplateType").val()==""?0:$("#tabtemplateType").val();
	var urlString = "<%=basePath%>/pages/resource/tab/showTemplateType.action?type=" + type+"&tabtemplateType="+$("#tabtemplateType").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#templateTypeID").html(data);
			}
		});
	showTemplate(type,templatetype);
}

function showTemplate(type,templateType){
	var urlString = "<%=basePath%>/pages/resource/tab/showTemplate.action?type=" + type+"&templateType="+templateType+"&tabtemplate="+$("#tabtemplate").val();
	$.ajax({
		type:'post',
		url: urlString,
		success: function(data){
			$("#templateID").html(data);
			if("${op}"=='view'){
				$("#tabtemplateType").attr("disabled","disabled");
				$("#combobox_tabtemplateId").attr("disabled","disabled");
				$("#combobox_tabtemplateId").next().attr("disabled","disabled");
			}
		}
	});
}

function changeTabType(obj){
	if(obj.value==1){
		$("#tabtype").attr("disabled","disabled");
		$("#tabtemplateType").attr("disabled","disabled");
		$("#tabtemplateId").attr("disabled","disabled");
	}else if(obj.value==0){
		$("#tabtype").removeAttr("disabled");
		$("#tabtemplateType").removeAttr("disabled");
		$("#tabtemplateId").removeAttr("disabled");
	}
}

function xinjianmuban(){
	$.pdialog.open("<%=basePath%>/pages/resource/templateaddBaseByForm.action?op=new&formId=109&tabop=${op}","newTemplateDialog","新建模板",{width:950,height:650,mask:true,resizable:true});
}
//-->
</script>
<form id="tabFormId" name="tab" class="pageform required-validate">
		<input id="tId" type="hidden" name="tab.id" value="${tab.id}" />
 	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
					选项卡
				</td>
				<td height="30" align="left" width="40%">
					<input style="width:180px;" type="text" id="tabName" name="tab.tabName" value="${tab.tabName}" <c:if test="${op=='view'}">disabled="disabled"</c:if> class="required" maxlength="50"/><!-- <span class="star">*</span> -->
				</td>
				<td width="10%" height="30" align="left" class="Input_Table_Label">
					显示顺序
				</td>
				<td height="30" align="left" width="40%">
					<input style="width:180px" type="text" id="tabOrder" name="tab.tabOrder" value="${tab.tabOrder}" class="textInput required" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onpaste='return false' maxlength="5" <c:if test="${op=='view'}">disabled="disabled"</c:if>/><!-- <span class="star">*</span> -->
				</td>
 	 		</tr>
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
					字体
				</td>
				<td height="30" align="left" width="40%">
					<select id="tabFontStyle" name="tab.tabFontStyle" style="width: 186px" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
						<option value="">系统默认</option>
						<c:forEach items="${fontStyles}" var="fontStyle">
							<option value="${fontStyle.value}" <c:if test="${fontStyle.value==tab.tabFontStyle }">selected="selected"</c:if>>${fontStyle.name}</option>
						</c:forEach>
					</select>
				</td>
 	 			<td height="30" align="left" class="Input_Table_Label" width="10%">文字颜色</td>
 	 			<td height="30" align="left" width="40%">
 	 				<select id="tabFontColor" name="tab.tabFontColor" style="width: 186px" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
 	 					<option value="">系统默认</option>
 	 					<c:forEach items="${fontColors}" var="fontColor">
							<option value="${fontColor.value}" <c:if test="${fontColor.value==tab.tabFontColor }">selected="selected"</c:if>>${fontColor.name}</option>
						</c:forEach>
 	 				</select>
 	 			</td>
 	 		</tr>
 	 		<tr>
				<td height="30" align="left" class="Input_Table_Label" width="10%">选择模板</td>
 	 			<td height="30" align="left" width="90%" colspan="3">
 	 				<input type="radio" onclick="showTemplate(this.value,0)" name="tab.type" value="0"  <c:if test="${tab.type!=1 }">checked="checked"</c:if>>表单模板
 	 				<input type="radio" onclick="showTemplate(this.value,0)" name="tab.type" value="1"  <c:if test="${tab.type==1}">checked="checked"</c:if>>组合模板
 	 				<span id="templateTypeID"></span>
 	 				<input type="hidden" id="tabtemplateType" name="tabtemplateType" value="${tab.templateType}">
					<span id="templateID"></span>
					<input type="hidden" id="tabtemplate" name="tabtemplate" value="${tab.templateId}">
					<a href="#" onclick="xinjianmuban()" style="color: blue;">新建模板</a>
 	 			</td>
 	 		</tr>
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
					功能说明
				</td>
				<td height="30" align="left" colspan="3">
					<textarea name="tab.comment" rows="5" cols="103" <c:if test="${op=='view'}">disabled="disabled"</c:if>>${tab.comment}</textarea><!-- <span class="star">*</span> -->
				</td>
 	 		</tr>
		</table>
		<div align="right" style="padding-top: 5px;padding-bottom: 5px;">
			<c:choose>
				<c:when test="${op=='view'}">
					<input type="button" name="add" class="listbuttonDisable" disabled="disabled" value="添加" onclick="addTab();"/>
					<input type="button" name="save" class="listbuttonDisable" disabled="disabled" value="保存" onclick="updateTab();"/>
					<input type="button" name="delete" class="listbuttonDisable" disabled="disabled" value="删除" onclick="deleteTab();"/>
				</c:when>
				<c:otherwise>
					<input type="button" name="add" class="listbutton" value="添加" onclick="addTab();"/>
					<input type="button" name="save" class="listbutton" value="保存" onclick="updateTab();"/>
					<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteTab();"/>
				</c:otherwise>
			</c:choose>
		</div>
</form>