<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/pages/core/compexDomainView.jsp"  %>	
<script type="text/javascript">
<!--
	$(function(){
		xgUrl="<%=basePath %>/pages/resource/print/edit.action?formId=${formId}&params=${params}&op=edit&recordId="+$('#recordId').val();
		
		$("#tabDivId table div").each(function() {
			if($(this).html() == '打印列表ID'){
				$(this).parent().parent().hide();
			}
		});
		ns.common.mouseForButton();
	});
//-->
</script>