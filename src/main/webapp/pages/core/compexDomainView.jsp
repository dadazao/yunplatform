<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	var op="${op}";
	$(function(){
		compexViewJson('<%=basePath%>/pages/resource/${simpleModel}compexviewJson.action?params=${params}&formId=${formId}');
		$("#BC").attr("disabled","disabled");
		$("#BCBXZ").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		$("#BCBXZ").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		xgUrl="<%=basePath%>/pages/resource/${simpleModel}compexedit.action?formId=${formId}&params=${params}" + "&op=edit";
		$('#yunDialog').attr('style', 'height: '+(fSelfHeight-70)+'px;overflow-x:hidden;OVERFLOW-Y:auto;');
		ns.common.mouseForButton();
		
		//双击关闭
		$("#yunDialog").dblclick(function(){
			$.pdialog.close("whDialog");
		});
		$("#yunDialog td").not(".Input_Table_Label").dblclick(function(event){
			event.stopPropagation(); 
		});
		
		formbzUrl = "<%=basePath%>/pages/resource/${simpleModel}compexshowFormHelp.action?formId=${formId}";
	});
	${form.jiaoben}
//--> 
</script>

<div class="buttonPanel">
	<div style="display:none;">
		<input id="domainSubmit" type="submit">
		<input id="formId" name="formId" value="${formId}"/>
		<input id="domainId" name="domainId" value="${domainId}">
		<input id="paramsId" name="params" value="${params}">
		<input id="mainTable" name="mainTable" value="${mainTable}">
	</div>
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
	<input id="tabId" type="hidden" value=""/>
	<input id="modelId" type="hidden" value="${id}"/>
</div>
<div id="yunDialog">
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
</div>
