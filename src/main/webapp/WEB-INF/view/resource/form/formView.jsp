<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<script type="text/javascript">
<!--
	//function eventXG() {
	//	var urlString = "<%=basePath %>/pages/resource/form/edit.action?id="+$("#formId").val() + "&op=edit&formManageId=${formManageId}";
	//	$.pdialog.reload(urlString);
	//}

	$(function(){
		loadButton("view");
		loadButtonList("view");
		loadTab("view");
		loadTabList("view");
		loadFormColumn("view");
		//loadFormColumnList();
		xgUrl="<%=basePath %>/pages/resource/form/edit.action?id="+$("#formId").val() + "&op=edit&formManageId=${formManageId}";
		$("#BC").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		$("#XG").attr("onclick","eventCompexFORMXG()");
		$('#yunDialog').attr('style', 'height: '+(fSelfHeight-70)+'px;overflow-x:hidden;OVERFLOW-Y:auto;');
		ns.common.mouseForButton();
	});
//-->
</script>
<form id="buttonFormID" method="post" action="<%=basePath %>/pages/resource/form/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div style="display:none;">	
		<input id="formId" type=hidden name="form.id" value="${form.id}"/>
		<input id="buttonSubmit" type="submit" name="submit"/>
		<input id="domainSubmit" type="hidden" value="buttonSubmit"/>
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
</form>
<div id="yunDialog"> 
<div class="tabs" >
      <div class="tabsHeader">
            <div class="tabsHeaderContent">
                  <ul>
                  	<li class="selected"><a><span>基本信息</span></a></li>
                  	<li><a><span>选项卡信息</span></a></li>
                  	<li><a><span>按钮信息</span></a></li>
                    <li><a><span id="ziduanId">字段信息</span></a></li>
                  </ul>
            </div>
      </div>
      <div class="tabsContent" >
      	<div align="center">
      	<form name="form">
      		<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		  	 		<tr>
		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
							表单名称
						</td>
						<td height="30" align="left" width="40%">
							${form.formName}&nbsp;
						</td>
<%--		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">--%>
<%--							编码--%>
<%--						</td>--%>
<%--		  	 			<td height="30" align="left" width="40%">--%>
<%--		  	 				${form.code}&nbsp;--%>
<%--		  	 			</td>--%>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							主表
						</td>
						<td height="30" align="left" width="40%">
							${form.tableChName}&nbsp;
						</td>
		  	 		</tr>
<%--		  	 		<tr>--%>
<%--		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--							权限--%>
<%--						</td>--%>
<%--						<td height="30" align="left" width="40%">--%>
<%--							${form.right}&nbsp;--%>
<%--						</td>--%>
<%--		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">--%>
<%--							是否上传文件--%>
<%--						</td>--%>
<%--		  	 			<td height="30" align="left" width="40%" colspan="3">--%>
<%--		  	 				<c:if test="${form.isUploadFile == 1}">是</c:if>--%>
<%--							<c:if test="${form.isUploadFile == 0}">否</c:if>--%>
<%--		  	 			</td>--%>
<%--		  	 		</tr>--%>
		  	 		<tr>
		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
							宽度
						</td>
						<td height="30" align="left" width="40%">
							${form.width}像素&nbsp;
						</td>
		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">
							高度
						</td>
		  	 			<td height="30" align="left" width="40%">
		  	 				${form.height}像素&nbsp;
		  	 			</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
							新建标题&nbsp;
						</td>
						<td height="30" align="left" width="40%">
							${form.xjTitle}&nbsp;
						</td>
		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">
							维护标题
						</td>
		  	 			<td height="30" align="left" width="40%">
		  	 				${form.whTitle}&nbsp;
		  	 			</td>
		  	 		</tr>
				   <tr>
				     <td class="Input_Table_Label" align="left"><label>&nbsp;脚本设置</label></td>
				     <td colspan="3" align="left">
				     	${form.jiaoben}&nbsp;
				     </td>
				   </tr>
				   <tr>
				     <td class="Input_Table_Label" align="left"><label>&nbsp;功能说明</label></td>
				     <td colspan="3" align="left">
				     	${form.remarks}&nbsp;
				     </td>
				   </tr>
				   <tr>
		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">
							创建人
						</td>
						<td height="30" align="left" width="40%">
							${form.userName}&nbsp;
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							创建时间
						</td>
						<td height="30" align="left" width="40%">
							<fmt:formatDate value="${form.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">
							修改人
						</td>
						<td height="30" align="left" width="40%">
							${form.updateName}&nbsp;
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							修改时间
						</td>
						<td height="30" align="left" width="40%">
							<fmt:formatDate value="${form.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
						</td>
		  	 		</tr>
		  	 	</table>
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
		<div align="center">
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