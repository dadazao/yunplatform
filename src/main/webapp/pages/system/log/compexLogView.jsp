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
		compexViewJson('<%=basePath %>/pages/resource/compexviewJson.action?params=${params}&formId=${formId}');
		$("#BC").attr("disabled","disabled");
		$("#BCBXZ").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		$("#BCBXZ").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		xgUrl="<%=basePath %>/pages/resource/${simpleModel}compexedit.action?formId=${formId}&params=${params}" + "&op=edit";
		$('#yunDialog').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
		ns.common.mouseForButton();
		
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
	});
	${form.jiaoben}
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
