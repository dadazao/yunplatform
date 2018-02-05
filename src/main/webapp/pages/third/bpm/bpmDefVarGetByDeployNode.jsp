<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程变量选择</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	window.name="win";
	$(function(){
		jQuery.highlightTableRows();
	});
	
	function selectVars(key,name){
		var obj={key:key,name:name};
		window.returnValue=obj;
		window.close();
	}

</script>
</head>
<body>
		<div class="panel" >
		<div class="hide-panel">
			<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">流程变量选择</span>
					</div>
				
				</div>
				</div>
			<div class="panel-body">
				<div class="panel-data">
				    <display:table name="bpmDefVarList" id="bpmDefVarItem"  cellpadding="1" cellspacing="1"   class="table-grid">
				    	
						<display:column property="varName" title="变量名称"  ></display:column>
						<display:column  title="变量数据类型" >
						<c:choose>
							<c:when test="${bpmDefVarItem.varDataType eq 'string'}">字符串</c:when>
							<c:when test="${bpmDefVarItem.varDataType eq 'number'}">数字</c:when>
							<c:when test="${bpmDefVarItem.varDataType eq 'date'}">日期</c:when>
						</c:choose>
						</display:column>
					
						<display:column title="节点名称" >
						<c:choose>
							<c:when test="${bpmDefVarItem.varScope eq 'global'}">全局变量</c:when>
							<c:when test="${bpmDefVarItem.varScope eq 'task'}">${bpmDefVarItem.nodeName}</c:when>
						</c:choose>
						</display:column>
						
						<display:column title="选择">
							<a href="#" onclick="selectVars('${bpmDefVarItem.varKey}','${bpmDefVarItem.varName}')" class="link edit" target="win">选择</a>
						</display:column>
					
					</display:table>
				</div>
			</div><!-- end of panel-body -->	
		</div>
</body>
</html>


