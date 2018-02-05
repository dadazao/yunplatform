<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<%@include file="/pages/resource/compexDomainEdit.jsp"%>
<script type="text/javascript" src="/js/cloudstong/yun.base_template.js"></script>
<script type="text/javascript">
<!--
	$(function(){
		//修改默认的保存方法
		$('#BC').attr('onclick', 'eventFormTemplateBC()');
		
		//将自定义代码放入指定区域
		$.ajax({
		   url: "<%=basePath %>/pages/resource/template/base/base_template.jsp",
		   dataType: "text",
		   success: function(html){
		     $('#tabDivId table tbody tr').eq(1).after(html);
		     //初始化模版设计视图
			templateInit();
			viewContent();
		   }
		});
		//隐藏行
		$('#sys_template-tbl_designType').val('1');
		$('#sys_template-tbl_type').val('0');
		$('#sys_template-tbl_designType').parent().parent().parent().hide();
		$('#sys_template-tbl_trs').parent().parent().parent().hide();
		
		$('#sys_template-tbl_mobanyongtu').change(function(){
			changeMbyt();
		})
		ns.common.mouseForButton();
	});
	
	//-->
</script>