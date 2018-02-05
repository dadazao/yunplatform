<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path1;
%>
<%@include file="/pages/core/compexDomainView.jsp"  %>	
<script type="text/javascript">
<!--		
	$(function(){
		var content = $('#templateView table').parent().html();
		$('#templateView table').closest('tr').hide();
		if(content!=null){
			if($.browser.msie){
				for (var i = 0; i < 30; i++) {
					content = content.replace("<DIV id=compexDomainTabEdit_label_t"+i+"></DIV>", "[数据字段]");
					content = content.replace("<DIV id=compexDomainTabEdit_value_t"+i+"></DIV>", "[数据值]");
				}
			}else{
				for (var i = 0; i < 30; i++) {
					content = content.replace("<div id=\"compexDomainTabEdit_label_t"+i+"\"></div>", "[数据字段]");
					content = content.replace("<div id='compexDomainTabEdit_label_t"+i+"'></div>", "[数据字段]");
					content = content.replace("<div id=\"compexDomainTabEdit_value_t"+i+"\"></div>", "[数据值]");
					content = content.replace("<div id='compexDomainTabEdit_value_t"+i+"'></div>", "[数据值]");
				}
			}
		}
		$('#tblContent').val(content);
		xgUrl="<%=basePath %>/pages/resource/libraryedit.action?formId=${formId}&params=${params}" + "&op=edit";
		$('#BC').hide();
		$('#XG').hide();
		ns.common.mouseForButton();
	});

//-->
</script>
<textarea id="tblContent"></textarea>