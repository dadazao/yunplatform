<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/pages/core/compexDomainEdit.jsp"%>

<script type="text/javascript">	
	$(function(){
		$.ajaxSetup({async: false});
		if($.browser.msie){
			$('#sys_dayinmoban-tbl_formId').change(function(){
				window.frames["ifrWeboffice"].document.getElementById("formList").value = $(this).val();
				$('#sys_dayinmoban-tbl_name').val($(this).find("option:selected").text()+'-打印模板-'+getDay());
			});
			var si;
			try{
				si = setInterval(function(){
					if(window.frames["ifrWeboffice"].document.getElementById("formList") != null){
						window.frames["ifrWeboffice"].document.getElementById("formList").value = $('#sys_dayinmoban-tbl_formId').val();
						clearInterval(si);
					}			
				},1000);
			}catch(e){
				clearInterval(si);
			}
		}
		$('#sys_dayinmoban-tbl_ids').parent().parent().parent().hide();
		ns.common.mouseForButton();
		$.ajaxSetup({async: true});
	});
	${form.jiaoben}
	
	//打开打印模板时使用,红头设置 方法
	function eventCompexHTSZ(){
		window.frames["ifrWeboffice"].setRedHeadContent();
	}
</script>
