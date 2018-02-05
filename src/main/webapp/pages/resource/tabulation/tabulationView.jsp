<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<script type="text/javascript">
<!--
//function modify() {
//	var urlString = "<%=basePath %>/pages/resource/tabulationedit.action?tabulationId="+$("#tabulationId").val() + "&op=edit";
//	$.pdialog.reload(urlString);
//}

$(function(){
	loadButton("view");
	loadButtonList("view");
	//loadTabulationDetails();
	//loadtabulationColumn();
	//loadtabulationColumnList();
	loadTabulationOpt("view");
	loadTabulationOptList("view");
	loadTabulationQuery("view");
	loadTabulationQueryList("view");
	xgUrl="<%=basePath %>/pages/resource/tabulationedit.action?tabulationId="+$("#tabulationId").val() + "&op=edit&formManageId=${formManageId}";
	$("#BC").attr("disabled","disabled");
	$("#BC").attr("class","listbuttonDisable");
	$("#XG").attr("onclick","eventCompexFORMXG()");
	$('#yunDialog').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
	ns.common.mouseForButton();
	
	formbzUrl = "<%=basePath %>/pages/resource/tabulationshowFormHelp.action";
});

//-->
</script>
<div id="yunDialog">
<form id="buttontabulationId" method="post" action="<%=basePath %>/pages/resource/tabulationsave.action" class="pagetabulation required-validate" onsubmit="return validateCallback(this, selfDialogAjaxDone);">
	 <input id="tabulationId" type=hidden name="tabulation.id" value="${tabulation.id}"/>
 	 <div  class="buttonPanel">
<%--		<button type="button" id="buttonXG" name="buttonXG" class="listbutton"  <c:if test="${op=='new' || op=='edit'}">disabled="disabled"</c:if> onClick="modify();">修 改</button>--%>
<%--		<button type="submit" id="buttonBC" name="buttonBC" class="listbutton" disabled="disabled">保 存</button>--%>
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
</form>
<div class="tabs">
      <div class="tabsHeader">
            <div class="tabsHeaderContent">
                  <ul>
                  	<li class="selected"><a><span>基本信息</span></a></li>
                  	<li><a><span>按钮信息</span></a></li>
<%--                    <li><a><span>列表元素</span></a></li>--%>
                    <li><a><span>操作信息</span></a></li>
                    <li><a><span>筛选条件</span></a></li>
                  </ul>
            </div>
      </div>
      <div class="tabsContent" >
      	<div align="center">
      	<form id="tabulationBaseId" name="tabulation">
      		<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
		  	 		<tr>
		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">
							列表名称
						</td>
						<td height="30" align="left" width="40%">
							${tabulation.tabulationName}&nbsp;
						</td>
						<td height="30" align="left" class="Input_Table_Label" width="10%">
							关联表单
						</td>
						<td height="30" align="left" width="40%">
							${tabulation.formName}&nbsp;
						</td>
		  	 		</tr>
<%--		  	 		<tr>--%>
<%--		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--							权限--%>
<%--						</td>--%>
<%--						<td height="30" align="left" width="40%">--%>
<%--							${tabulation.right}&nbsp;--%>
<%--						</td>--%>
<%--						<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--							引用模板--%>
<%--						</td>--%>
<%--						<td height="30" align="left" width="40%">--%>
<%--							${tabulation.templateListName}&nbsp;--%>
<%--						</td>--%>
<%--		  	 		</tr>--%>
<%--		  	 		<tr>--%>
<%--		  	 			<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--							是否选择--%>
<%--						</td>--%>
<%--						<td height="30" align="left" width="40%">--%>
<%--							<c:if test="${tabulation.isSelect==1 }">是</c:if>--%>
<%--							<c:if test="${tabulation.isSelect==0 }">否</c:if>--%>
<%--						</td>--%>
<%--						<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--							是否维护--%>
<%--						</td>--%>
<%--						<td height="30" align="left" width="40%">--%>
<%--							<c:if test="${tabulation.isModify==1 }">是</c:if>--%>
<%--							<c:if test="${tabulation.isModify==0 }">否</c:if>--%>
<%--						</td>--%>
<%--		  	 		</tr>--%>
<%--		  	 		<tr>--%>
<%--						<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--							维护字段名称--%>
<%--						</td>--%>
<%--						<td height="30" align="left" width="40%">--%>
<%--							${tabulation.modifyName}--%>
<%--						</td>--%>
<%--		  	 		</tr>--%>
<%--		  	 		<tr>--%>
<%--				   	 	<td class="Input_Table_Label" align="left">--%>
<%--				   	 		<label>&nbsp;编码</label>--%>
<%--				   	 	</td>--%>
<%--				   	 	<td colspan="3" align="left">--%>
<%--				   	 		${tabulation.code}&nbsp;--%>
<%--				   	 	</td>--%>
<%--				   </tr>--%>
				   <tr>
				   	 	<td class="Input_Table_Label" align="left">
				   	 		<label>列表组件</label>
				   	 	</td>
				   	 	<td align="left">
				   	 		${tabulation.listControl.listControlName}
				   	 	</td>
				   	 	<td class="Input_Table_Label" align="left">
				   	 		<label>查询组件</label>
				   	 	</td>
				   	 	<td align="left">
				   	 		${tabulation.queryControl.queryControlName}
				   	 	</td>
				   </tr>
				   <tr>
				   	 	<td class="Input_Table_Label" align="left">
				   	 		<label>高级查询组件</label>
				   	 	</td>
				   	 	<td align="left" colspan="3">
				   	 		${tabulation.advanceQueryControl.queryControlName}
				   	 	</td>
				   </tr>
				   <tr>
				     <td class="Input_Table_Label" align="left"><label>&nbsp;功能说明</label></td>
				     <td colspan="3" align="left">
				     	${tabulation.remarks}&nbsp;
				     </td>
				   </tr>
		  	 		<tr>
<%--		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">--%>
<%--							是否上传文件--%>
<%--						</td>--%>
<%--		  	 			<td height="30" align="left" width="40%">--%>
<%--		  	 				<c:if test="${tabulation.isUploadFile == 1}">是</c:if>--%>
<%--							<c:if test="${tabulation.isUploadFile == 0}">否</c:if>&nbsp;--%>
<%--		  	 			</td>--%>
<%--						<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--							所属表--%>
<%--						</td>--%>
<%--						<td height="30" align="left" width="40%">--%>
<%--							${tabulation.tableZhName}&nbsp;--%>
<%--						</td>--%>
		  	 		</tr>
		  	 		<tr>
		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">
							创建人
						</td>
						<td height="30" align="left" width="40%">
							${tabulation.userName}&nbsp;
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							创建时间
						</td>
						<td height="30" align="left" width="40%">
							<fmt:formatDate value="${tabulation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
		  	 		</tr>
		  	 		<tr>
		  	 			<td height="30" align="left" class="Input_Table_Label" width="10%">
							修改人
						</td>
						<td height="30" align="left" width="40%">
							${tabulation.updateName}&nbsp;
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							修改时间
						</td>
						<td height="30" align="left" width="40%">
							<fmt:formatDate value="${tabulation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
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
<%--      	<div>--%>
<%--      		<div id="tabulationDetailId" align="center">--%>
<%--			</div>--%>
<%--      	</div>--%>
<%--      	<div>--%>
<%--			<div id="tabulationColumnId" align="center">--%>
<%--			</div>--%>
<%--			<br>--%>
<%--			<div id="tabulationColumnListId" align="center">--%>
<%--			</div>--%>
<%--		</div>--%>
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