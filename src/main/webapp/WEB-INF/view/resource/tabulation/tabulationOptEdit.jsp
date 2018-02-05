 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
$(function(){
	if(${marking=='list'}){
		
	}else{
		showOptButtonComment();
	}
});

function showOptButtonComment(){
	var value = $("#optButtonID option:selected").val()==null?"":$("#optButtonID option:selected").val();
	var type = "0";
	$.ajax({
  		type:'POST',
  		url:'<%=basePath %>/pages/resource/buttonfindButtonOrGroupByID.action?id=' + value +"&type="+type,
  		dataType:'json',
  		success:function(data){
			$("#tabulationoptcomment").val(data.comment);
			$("#optShowName").val(data.buttonName);
			$("#optFuncName").val(data.buttonBM);
  		}
  	});
}

//-->
</script>
<form id="tabulationOptTabulationId" name="tabulationQuery" class="pagetabulation required-validate" method="post">
		<input id="tqId" type="hidden" name="tabulationOpt.id" value="${tabulationOpt.id}" />
 	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
 	 		<tr>
<%-- 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--					名称--%>
<%--				</td>--%>
<%--				<td height="30" align="left" width="40%">--%>
<%--					<input type="text" name="tabulationOpt.name" value="${tabulationOpt.name}"/>--%>
<%--				</td>--%>
				<td width="10%" height="30" align="left" class="Input_Table_Label">
					操作按钮
				</td>
				<td height="30" align="left" width="40%">
					<select id="optButtonID" name="tabulationOpt.buttonId" style="width:186px" onchange="showOptButtonComment();" <c:if test="${op=='view'}">disabled="disabled"</c:if>>
						<c:forEach items="${buttons}" var="button">
							<option value="${button.id}" <c:if test="${button.id==tabulationOpt.buttonId }">selected="selected"</c:if>>${button.buttonName}</option>
						</c:forEach>
					</select>
				</td>
				<td width="10%" height="30" align="left" class="Input_Table_Label">
					显示名称
				</td>
				<td height="30" align="left">
					<input type="text" id="optShowName" name="tabulationOpt.showName" <c:if test="${op=='view'}">disabled="disabled"</c:if> value="${tabulationOpt.showName}" style="width:180px" class="textInput required" maxlength="25"/><!-- <span class="star">*</span> -->
				</td>
 	 		</tr>
 	 		<tr>
 	 			<td width="10%" height="30" align="left" class="Input_Table_Label">功能方法</td>
 	 			<td height="30" align="left" width="40%">
 	 				<input type="text" id="optFuncName" name="tabulationOpt.funcName" <c:if test="${op=='view'}">disabled="disabled"</c:if> value="${tabulationOpt.funcName}" style="width:180px" class="textInput required" maxlength="25"/><!-- <span class="star">*</span> -->
 	 			</td>
 	 			<td height="30" align="left" class="Input_Table_Label" width="10%">
 	 				显示顺序
 	 			</td>
 	 			<td height="30" align="left" width="40%">
					<input type="text" name="tabulationOpt.order" value="${tabulationOpt.order}" style="width:180px" class="textInput required" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onpaste='return false' maxlength="5" <c:if test="${op=='view'}">disabled="disabled"</c:if>/><!-- <span class="star">*</span> -->
 	 			</td>
 	 		</tr>
 	 		<tr height=20>
				<td style="width: 80px;" class="Input_Table_Label" align="left">
					<label>
						&nbsp;按钮功能说明
					</label>
				</td>
				<td colspan="3" align="left">
					<textarea class="textInput" rows="3" cols="103" id="tabulationoptcomment" name="tabulationOpt.fcomment" <c:if test="${op=='view'}">disabled="disabled"</c:if>>${tabulationOpt.fcomment}</textarea>
				</td>
			</tr>
	</table>
	<div align="right" style="padding-top: 5px;padding-bottom: 5px;">
		<c:choose>
			<c:when test="${op=='view'}">
				<input type="button" name="add" class="listbuttonDisable" disabled="disabled" value="添加" onclick="addTabulationOpt();">
				<input type="button" name="save" class="listbuttonDisable" disabled="disabled" value="保存" onclick="updateTabulationOpt();">
				<input type="button" name="delete" class="listbuttonDisable" disabled="disabled" value="删除" onclick="deleteTabulationOpt();">
			</c:when>
			<c:otherwise>
				<input type="button" name="add" class="listbutton" value="添加" onclick="addTabulationOpt();">
				<input type="button" name="save" class="listbutton" value="保存" onclick="updateTabulationOpt();">
				<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteTabulationOpt();">
			</c:otherwise>
		</c:choose>
	</div>
</form>
