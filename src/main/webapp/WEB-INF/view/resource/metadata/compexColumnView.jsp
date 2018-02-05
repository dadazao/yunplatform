<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
		$("#BCBXZ").hide();
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		xgUrl="<%=basePath %>/pages/resource/columnedit.action?formId=${formId}&params=${params}" + "&op=edit";
		ns.common.mouseForButton();
		
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
	});

//-->
</script>
<div id="columnEdit">
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
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
			  	 	<input id="columnId" type=hidden name="column.id" value="${column.id}"/>
			  	 	<input id="publishId" name="column.isPublish" type="hidden" value="${column.isPublish}">
			  	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td width="10%" align="left" class="Input_Table_Label">
								所属表
							</td>
							<td width="40%" align="left">
								${column.tableZhName}&nbsp;
							</td>
							<td height="30" width="10%" align="left" class="Input_Table_Label">
								字段拼音名
							</td>
							<td width="40%" align="left">
								${column.columnName}&nbsp;
							</td>
						</tr>
						<tr>
							<td width="10%" align="left" class="Input_Table_Label">
								字段中文名
							</td>
							<td width="40%" align="left">
								${column.columnZhName}&nbsp;
							</td>
							<td width="10%" align="left" class="Input_Table_Label">
								字段英文名
							</td>
							<td width="40%" align="left">
								${column.columnEnName}&nbsp;
							</td>
						</tr>
						<tr>
							<td height="30" align="left" class="Input_Table_Label">
								数据类型
							</td>
							<td align="left">
								${column.dataType}&nbsp;
							</td>
							<td height="30" align="left" class="Input_Table_Label">
								数据长度
							</td>
							<td align="left">
								${column.length}&nbsp;
							</td>
						</tr>
<%--						<tr>--%>
<%--							<td height="30" align="left" class="Input_Table_Label">--%>
<%--								显示规则--%>
<%--							</td>--%>
<%--							<td align="left">--%>
<%--								<c:forEach items="${showRuleList}" var="showRule">--%>
<%--									<c:if test="${column.showRule==showRule.value }">${showRule.name}</c:if>--%>
<%--								</c:forEach>--%>
<%--							</td>--%>
<%--							<td align="left" class="Input_Table_Label">--%>
<%--								验证值--%>
<%--							</td>--%>
<%--							<td align="left">--%>
<%--								${column.checkValue}&nbsp;--%>
<%--							</td>--%>
<%--						</tr>--%>
						<tr>
							<td height="30" align="left" class="Input_Table_Label">
								是否空值
							</td>
							<td align="left" >
									<c:if test="${column.isNullable==1 }">是</c:if>
									<c:if test="${column.isNullable==0 }">否</c:if>
							</td>
							<td align="left" class="Input_Table_Label">
								数据库默认值
							</td>
							<td align="left">
								${column.defaultValue}&nbsp;
							</td>
<%--							<td height="30" align="left" class="Input_Table_Label">--%>
<%--								是否发布--%>
<%--							</td>--%>
<%--							<td align="left">--%>
<%--									<c:if test="${column.isPublish==1 }">是</c:if>--%>
<%--									<c:if test="${column.isPublish==0 }">否</c:if>--%>
<%--							</td>--%>
						</tr>
						<tr>
							<td height="30" align="left" class="Input_Table_Label">
								功能说明
							</td>
							<td align="left" colspan="3">
								${column.comment}&nbsp;
							</td>
						</tr>
						<tr>
							<td height="30" align="left" class="Input_Table_Label">
								备注
							</td>
						
							<td colspan="3" align="left">
								${column.remark}&nbsp;
							</td>
						</tr>
					 <tr>
						<td height="30" align="left" class="Input_Table_Label">
							创建人
						</td>
						<td align="left">
							${column.userName}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label">
							创建时间
						</td>
						<td height="30" align="left" >
							<fmt:formatDate value="${column.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
						</td>
					</tr>
					<tr>
						<td height="30" align="left" class="Input_Table_Label">
							修改人
						</td>
						<td align="left">
							${column.updateName}&nbsp;
						</td>
						<td align="left" class="Input_Table_Label">
							修改时间
						</td>
						<td height="30" align="left" >
							<fmt:formatDate value="${column.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
						</td>
					</tr>
					</table>
			</div>
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div>
	</div>
</div>
