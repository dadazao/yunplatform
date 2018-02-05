<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		$("#BC").attr("disabled","disabled");
		$("#SC").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		$("#SC").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		xgUrl="<%=basePath %>/pages/resource/tableedit.action?formId=${formId}&params=${params}" + "&op=edit";
		ns.common.mouseForButton();
		
		loadTableColumnForm();
		loadTableColumnList();
		
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
	});

//-->
</script>
<div id="tableEdit">
	<div style="display:none;">
		<input id="domainSubmit" type="submit">
		<input id="formId" name="formId" value="${formId}"/>
		<input id="domainId" name="domainId" value="${domainId}">
		<input id="paramsId" name="params" value="${params}">
		<input id="mainTable" name="mainTable" value="${mainTable}">
	</div>
	<div  class="buttonPanel">
		<c:forEach items="${formButtons}" var="formButton" varStatus="status">
			<c:if test="${formButton.hasAuth=='0'}">
				<c:if test="${formButton.buttonType == '0'}">
					<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbutton" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;" onclick="eventCompex${formButton.funcName}();" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;font-size: ${formButton.button.buttonNameFontSize};background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
				</c:if>
			</c:if>
			<c:if test="${formButton.hasAuth=='1'}">
				<c:if test="${formButton.buttonType == '0'}">
					<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbuttonDisable" disabled="disabled" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;font-size: ${formButton.button.buttonNameFontSize};background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
				</c:if>
			</c:if>
		</c:forEach>
	</div>
	<input id="tabId" type="hidden" value=""/>
	<input id="modelId" type="hidden" value="${id}"/>
	<div style="height:2px;"></div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                        <li class="selected"><a><span>基本信息</span></a></li>
	                        <li class="selected"><a><span>字段信息</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
				<input id="tableId" type=hidden name="table.id" value="${table.id}"/>
				<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr >
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							表中文名
						</td>
						<td width="40%" height="30" align="left">
							${table.tableZhName}&nbsp;
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							表拼音名
						</td>
						<td height="30" align="left">
							${table.tableName}&nbsp;
						</td>
					</tr>
					<tr>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							表英文名
						</td>
						<td width="40%" height="30" align="left">
							${table.tableEnName}&nbsp;
						</td>
						<td  align="left" class="Input_Table_Label">
							公有字段
						</td>
						<td align="left">
							<c:if test="${table.hasCommonColumn==1 }">包含</c:if> 
							<c:if test="${table.hasCommonColumn==0 }">不包含</c:if> 
						</td>
					</tr>
					<tr>
						<td height="30" align="left" class="Input_Table_Label">
							所属库
						</td>
						<td align="left">
							<c:forEach items="${tableSchemaList}" var="tableSchema">
								<c:if test="${table.tableSchema==tableSchema.value }">${tableSchema.name}</c:if>
							</c:forEach>
						</td>
						<td height="30" align="left" class="Input_Table_Label">
							类型
						</td>
						<td align="left">
							<c:forEach items="${tableTypeList}" var="tableType">
								<c:if test="${table.tableType==tableType.value }">${tableType.name}</c:if>
							</c:forEach>&nbsp;
						</td>
					<tr>
						<td height="30" align="left" class="Input_Table_Label">
							功能说明
						</td>
						<td align="left" colspan="3" >
							${table.tableFunction}&nbsp;
						</td>
					</tr>
					<tr>
						<td height="30" align="left" class="Input_Table_Label">
							创建人
						</td>
						<td align="left">
							${table.userName}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label">
							创建时间
						</td>
						<td height="30" align="left" >
							<fmt:formatDate value="${table.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
						</td>
					</tr>
					<tr>
						<td height="30" align="left" class="Input_Table_Label">
							修改人
						</td>
						<td align="left">
							${table.updateName}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label">
							修改时间
						</td>
						<td height="30" align="left" >
							<fmt:formatDate value="${table.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div>
				<div id="table_column_form_div" align="center"></div>
				<div id="table_column_list_div" align="center"></div>
			</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div>
	</div>
</div>