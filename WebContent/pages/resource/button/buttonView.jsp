<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		var urlString = "<%=basePath%>/pages/resource/buttonActionviewJson.action?params=${params}&formId=${formId}";
		//生成查看页面
		compexViewJson(urlString);
		//删除ACTION URL
		scUrl = "<%=basePath%>/pages/resource/buttonActionsingleDelete.action?params=${params}";
		//修改ACTION URL
		xgUrl = "<%=basePath%>/pages/resource/buttonActionedit.action?formId=${formId}&params=${params}" + "&op=edit";
		//将保存按钮置灰
		$("#BC").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		xgUrl="<%=basePath%>/pages/resource/${simpleModel}compexedit.action?formId=${formId}&params=${params}" + "&op=edit";
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
	<c:forEach items="${form.formButtons}" var="formButton" varStatus="status">
		<c:if test="${formButton.buttonType == '1'}">
			<c:forEach items="${formButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
				<button type="button" id="${buttonAndGroup.button.buttonBM}" name="b${status.count*stat.count}" class="listbutton" onclick="eventCompex${buttonAndGroup.button.buttonBM}();">${buttonAndGroup.button.buttonName}</button>
			</c:forEach>
		</c:if>
		<c:if test="${formButton.buttonType == '0'}">
			<button type="button" name="b${status.count}" class="listbutton" onclick="eventCompex${formButton.button.buttonBM}();" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;">${formButton.button.buttonName}</button>	
		</c:if>
	</c:forEach>
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
