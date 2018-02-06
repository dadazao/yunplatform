<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		var urlString = "<%=basePath %>/pages/resource/checkbox/dataJson.action?params=${params}&formId=${formId}";
		//生成查看/修改页面
		compexDataJson(urlString);
		//删除ACTION URL
		scUrl = "<%=basePath %>/pages/resource/checkbox/singleDelete.action?params=${params}";
		//保存ACTION URL
		bcUrl = "<%=basePath %>/pages/resource/checkbox/save.action";
		//将修改按钮置灰
		$("#XG").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		
		//新增情况：将【删除】按钮设置为：不可用（disabled）
		var op = $("#op").val();
		if( op != null && "new" == op ){
			$("#SC").attr("DISABLED","true");
		}
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		ns.common.mouseForButton();
	});

//-->
</script>
	<div>
		<div style="display:none;">
			<input id="domainSubmit" type="submit"/>
			<input type="hidden" id="isFirstTab" value="true">
			<input id="formId" name="formId" value="${formId}"/>
			<input id="domainId" name="domainId" value="${domainId}"/>
			<input id="paramsId" name="params" value="${params}"/>
			<input id="mainTable" name="mainTable" value="${mainTable}"/>
		</div>
		<div class="buttonPanel">
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
