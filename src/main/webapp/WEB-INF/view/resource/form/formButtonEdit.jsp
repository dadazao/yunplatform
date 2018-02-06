<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<script type="text/javascript">
<!--
$(function(){
	showButtonTab();
	if(${tabulationButton.buttonType==1}){
		$("#buttonspan").html("按钮组");
	}else{
		$("#buttonspan").html("按钮");
	}
	ns.common.mouseForButton();
	if(${marking=='list'}){
		
	}else{
		showButtonComment();
	}
});

function showButtonTab(){
	var urlString = "<%=basePath%>/pages/resource/fbutton/showButtonTab.action?formId="+$("#formId").val()+"&buttonTabId="+$("#buttonTabId").val();
		$.ajax({
			type:'post',
			url: urlString,
			async:false,
			success: function(data){
				$("#buttontabID").html(data);
			}
		});
	showButtonPartition($("#buttonTabId").val());
}

function showButtonPartition(tabId){
	var urlString = "<%=basePath%>/pages/resource/fbutton/showPartition.action?buttonTabId="+tabId+"&buttonPartitionId="+$("#buttonPartitionId").val();
		$.ajax({
			type:'post',
			url: urlString,
			async:false,
			success: function(data){
				$("#partitiontabID").html(data);
				enableParSelect();
				if("${op}"=='view'){
					$("#formButtonTabId").attr("disabled","disabled");
					$("#formButtonPartitionId").attr("disabled","disabled");
				}
			}
		});
}

function showButtonComment(){
	var value = $("#buttonID option:selected").val()==null?"-1":$("#buttonID option:selected").val();
	var type = $("#buttonTYPE option:selected").val();
	$.ajax({
  		type:'POST',
  		url:"<%=basePath%>/pages/resource/button/findButtonOrGroupByID.action?id=" + value +"&type="+type,
  		dataType:'json',
  		success:function(data){
			if(data!=null){
				$("#formbuttoncomment").val(data.comment);
				$("#formbuttonshowName").val(data.buttonName);
				$("#formbuttonfuncName").val(data.buttonBM);
				var po = parseInt($("#formbuttonshowOrder").val())
				if(!po) {
					po=0;
				}
				$("#formbuttonshowOrder").val(po + 1);
			}
  		}
  	});
}

function enableParSelect(){
	if($("#formButtonPartitionId").val()==undefined){
		$("#formButtonPartitionId").attr("disabled","disabled");
	}else{
		$("#formButtonPartitionId").removeAttr("disabled");
	}
}
//-->
</script>
<form id="formButtonformId" name="formButton" class="pageform required-validate">
		<input id="btnId" type="hidden" name="formButton.id" value="${formButton.id}" />
 	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
					选择按钮
				</td>
				<td height="30" align="left">
					<select id="buttonTYPE" name="formButton.buttonType" style="width:100px;display: none;" onchange="changeButtonSpan(this.value);" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
						<option value="0" <c:if test="${formButton.buttonType==0}">selected="selected"</c:if>>按钮</option>
					</select>
					<span id="buttonorbuttongroupId">
						<select id="buttonID" name="formButton.buttonId" style="width:186px" onchange="showButtonComment()" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
<%--	 	 					<c:if test='${formButton.buttonType=="0"}'>--%>
							<c:forEach items="${buttons}" var="button">
								<option title="${button.comment}" value="${button.id}" <c:if test="${button.id==formButton.buttonId }">selected="selected"</c:if>>${button.buttonName}</option>
							</c:forEach>
<%--							</c:if>--%>
<%--							<c:if test='${formButton.buttonType=="1"||formButton.buttonType==undefined}'>--%>
<%--							<c:forEach items="${buttonGroups}" var="buttonGroup">--%>
<%--								<option title="${buttonGroup.comment}"  value="${buttonGroup.id}" <c:if test="${buttonGroup.id==formButton.buttonId }">selected="selected"</c:if>>${buttonGroup.buttonGroupName}</option>--%>
<%--							</c:forEach>--%>
<%--							</c:if>--%>
						</select>
					</span>
				</td>
				<td width="10%" height="30" align="left" class="Input_Table_Label">
					显示名称
				</td>
				<td height="30" align="left">
					<input type="text" id="formbuttonshowName" name="formButton.showName" <c:if test="${op=='view'}">disabled="disabled"</c:if> value="${formButton.showName}" style="width:175px" class="textInput required"  maxlength="25"/><!-- <span class="star">*</span> -->
				</td>
 	 		</tr>
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">功能方法</td>
 	 			<td height="30" align="left" width="40%">
 	 				<input type="text" id="formbuttonfuncName" name="formButton.funcName" <c:if test="${op=='view'}">disabled="disabled"</c:if> value="${formButton.funcName}" style="width:175px" class="textInput required"  maxlength="25"/><!-- <span class="star">*</span> -->
 	 			</td>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
					显示顺序
				</td>
				<td height="30" align="left">
					<input type="text" id="formbuttonshowOrder" name="formButton.showOrder" <c:if test="${op=='view'}">disabled="disabled"</c:if> value="${formButton.showOrder}" style="width:175px" class="textInput required" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onpaste='return false' maxlength="5"/><!-- <span class="star">*</span> -->
				</td>
 	 		</tr>
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">应用位置</td>
 	 			<td height="30" align="left" width="40%">
 	 				<div id="buttontabID"></div>
 	 				<input type="hidden" id="buttonTabId" name="buttonTabId" value="${formButton.tabId}" style="width:180px"/>
 	 			</td>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">所属分区</td>
 	 			<td height="30" align="left" width="40%">
 	 				<div id="partitiontabID">
 	 				
 	 				</div>
 	 				<input type="hidden" id="buttonPartitionId" name="buttonPartitionId" value="${formButton.partitionId}"/>
 	 			</td>
 	 		</tr>
 	 		<tr height=20>
				<td style="width: 80px;" class="Input_Table_Label" align="left">
					<label>
						&nbsp;按钮功能说明
					</label>
				</td>
				<td colspan="3" align="left">
					<textarea class="textInput" rows="5" cols="103" id="formbuttoncomment" name="formButton.fcomment" <c:if test="${op=='view'}">disabled="disabled"</c:if>>${formButton.fcomment}</textarea>
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
				<input type="button" name="add" class="listbutton" value="添加" onclick="addFormButton();">
				<input type="button" name="save" class="listbutton" value="保存" onclick="updateFormButton();">
				<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteFormButton();">
			</c:otherwise>
		</c:choose>
		
	</div>
</form>
