<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
$(function(){
	if(${tabulationButton.buttonType==1}){
		$("#buttonspan").html("按钮组");
	}else{
		$("#buttonspan").html("按钮");
	}
	if(${marking=='list'}){
		
	}else{
		showButtonComment();
	}
});

function showButtonComment(){
	var value = $("#buttonID option:selected").val()==null?"":$("#buttonID option:selected").val();
	var type = $("#buttonTYPE option:selected").val();
	$.ajax({
  		type:'POST',
  		url:'<%=basePath %>/pages/resource/buttonfindButtonOrGroupByID.action?id=' + value +"&type="+type,
  		dataType:'json',
  		success:function(data){
			$("#tabulationbuttoncomment").val(data.comment);
			$("#showName").val(data.buttonName);
			$("#funcName").val(data.buttonBM);
  		}
  	});
}

//-->
</script>
<form id="tabulationButtonTabulationId" onkeydown="return enterNotSubmit(event);" name="tabulationButton" class="pagetabulation required-validate">
		<input id="btnId" type="hidden" name="tabulationButton.id" value="${tabulationButton.id}" />
 	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
				选择按钮
				</td>
				<td height="30" align="left">
					<select id="buttonTYPE" name="tabulationButton.buttonType" style="width:100px;display: none;" onchange="changeButtonSpan(this.value)" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
						<option value="0" <c:if test="${tabulationButton.buttonType==0}">selected="selected"</c:if>>按钮</option>
					</select>
					<span id="buttonorbuttongroupId">
	 	 				<select id="buttonID" name="tabulationButton.buttonId" style="width:186px" onchange="showButtonComment();" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
							<c:forEach items="${buttons}" var="button">
								<option title="${button.comment}" value="${button.id}" <c:if test="${button.id==tabulationButton.buttonId }">selected="selected"</c:if>>${button.buttonName}</option>
							</c:forEach>
						</select>
					</span>
				</td>
				<td width="10%" height="30" align="left" class="Input_Table_Label">
					显示名称
				</td>
				<td height="30" align="left">
					<input type="text" id="showName" name="tabulationButton.showName" <c:if test="${op=='view'}">disabled="disabled"</c:if> value="${tabulationButton.showName}" style="width:180px" class="textInput required"  maxlength="25"/><!-- <span class="star">*</span> -->
				</td>
 	 		</tr>
			<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">功能方法</td>
 	 			<td height="30" align="left" width="40%">
 	 				<input type="text" id="funcName" name="tabulationButton.funcName" <c:if test="${op=='view'}">disabled="disabled"</c:if> value="${tabulationButton.funcName}" style="width:180px" class="textInput required" maxlength="25"/><!-- <span class="star">*</span> -->
 	 			</td>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
					显示顺序
				</td>
				<td width="30%" height="30" align="left">
					<input type="text" id="showOrder" name="tabulationButton.showOrder" value="${tabulationButton.showOrder}" style="width:180px" class="textInput required" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onpaste='return false' maxlength="5" <c:if test="${op=='view'}">disabled="disabled"</c:if>/><!-- <span class="star">*</span> -->
				</td>
 	 		</tr>
			<tr height=20>
				<td style="width: 80px;" class="Input_Table_Label" align="left">
					<label>
						&nbsp;按钮功能说明
					</label>
				</td>
				<td colspan="3" align="left">
					<textarea class="textInput" rows="3" cols="111" id="tabulationbuttoncomment" name="tabulationButton.fcomment" <c:if test="${op=='view'}">disabled="disabled"</c:if>>${tabulationButton.fcomment}</textarea>
				</td>
			</tr>
	</table>
	<div align="right" style="padding-top: 5px;padding-bottom: 5px;">
		<c:choose>
			<c:when test="${op=='view'}">
				<input type="button" name="add" class="listbuttonDisable" disabled="disabled" value="添加" onclick="addTabulationButton();">
				<input type="button" name="save" class="listbuttonDisable" disabled="disabled" value="保存" onclick="updateTabulationButton();">
				<input type="button" name="delete" class="listbuttonDisable" disabled="disabled" value="删除" onclick="deleteTabulationButton();">
			</c:when>
			<c:otherwise>
				<input type="button" name="add" class="listbutton" value="添加" onclick="addTabulationButton();">
				<input type="button" name="save" class="listbutton" value="保存" onclick="updateTabulationButton();">
				<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteTabulationButton();">
			</c:otherwise>
		</c:choose>
	</div>
</form>
