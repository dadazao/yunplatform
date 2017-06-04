<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	var op="${op}";
	$(function(){
		$.ajaxSetup({async: false});
		compexDataJson('<%=basePath%>/pages/resource/${simpleModel}compexdataJson.action?formId=${formId}&params=${params}&model=${model}&subDomainId=${subDomainId}&partitionId=${partitionId}');
		$("#XG").attr("disabled","disabled");
		$("#DY").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		$("#DY").attr("class","listbuttonDisable");
		$("#BDDY").attr("disabled","disabled");
		$("#BDDY").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		$('#yunDialog').attr('style', 'height: '+(fSelfHeight-70)+'px;overflow-x:hidden;OVERFLOW-Y:auto;');
		ns.common.mouseForButton();
		$.ajaxSetup({async: true});
		
		//双击关闭
		$("#yunDialog").dblclick(function(){
			try{
				$.pdialog.close("xgDialog");
			}catch(e){}
			try{
				$.pdialog.close("newDialog");
			}catch(e){}
		});
		$("#yunDialog td").not(".Input_Table_Label").dblclick(function(event){
			event.stopPropagation(); 
		});
		formbzUrl = "<%=basePath%>/pages/resource/${simpleModel}compexshowFormHelp.action?formId=${formId}";
		
	});
	$(function(){
		${form.jiaoben}
	})
//-->
</script>
<div class="buttonPanel">
	<div style="display:none;">
		<input id="domainSubmit" type="submit"/>
		<input type="hidden" id="isFirstTab" value="true">
		<input id="formId" name="formId" value="${formId}"/>
		<input id="domainId" name="domainId" value="${domainId}"/>
		<input id="paramsId" name="params" value="${params}"/>
		<input id="mainTable" name="mainTable" value="${mainTable}"/>
	</div>
	<c:forEach items="${form.formButtons}" var="formButton" varStatus="status">
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