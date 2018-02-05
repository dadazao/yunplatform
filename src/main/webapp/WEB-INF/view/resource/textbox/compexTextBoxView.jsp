<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		var urlString = "<%=basePath %>/pages/resource/textBoxviewJson.action?params=${params}&formId=${formId}";
		//生成查看页面
		compexViewJson(urlString);
		//删除ACTION URL
		scUrl = "<%=basePath %>/pages/resource/textBoxsingleDelete.action?params=${params}";
		//修改ACTION URL
		xgUrl = "<%=basePath %>/pages/resource/textBoxedit.action?formId=${formId}&params=${params}" + "&op=edit";
		//将保存按钮置灰
		$("#BC").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		ns.common.mouseForButton();
		
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
	});
//-->
</script>
<div>
	<div style="display:none;">
		<input id="domainSubmit" type="submit">
		<input id="formId" name="formId" value="${formId}"/>
		<input id="domainId" name="domainId" value="${domainId}">
		<input id="paramsId" name="params" value="${params}">
		<input id="mainTable" name="mainTable" value="${mainTable}">
	</div>
	<div class="buttonPanel">
		<c:forEach items="${form.formButtons}" var="formButton" varStatus="status">
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
</div>
<div class="tabs">
      <div class="tabsHeader">
            <div class="tabsHeaderContent">
                  <ul id="tabLiId">
                  </ul>
            </div>
      </div>
      <div class="tabsContent" id="tabDivId">
      </div>
      <!-- Tab结束层 -->
	  <div class="tabsFooter">
		  <div class="tabsFooterContent"></div>
	  </div>
</div>
