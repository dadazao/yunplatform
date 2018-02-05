<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%

String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

String catalogId = request.getParameter("catalogId");
%>
<script type="text/javascript">
<!--
	$(function() {
		var catalogId = '<%=catalogId%>';
		$.ajax({
			url : "<%=basePath%>/pages/resource/treecomment.action",
			data : "rootId="+catalogId,
			dataType : "json",
			success : function(data) {
				var html = '<table width="80%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #dfdfdf solid;">';
				html += '<thead><tr><th class="thClass" style="width:15%;border-bottom:1px #dfdfdf solid; border-right:1px #dfdfdf solid;">包含模块</th><th class="thClass" style="width:85%;border-bottom:1px #dfdfdf solid; border-right:1px #dfdfdf solid;">功能说明</th></tr></thead>';
				for(var i=0; i<data.length; i++){
					if(data[i].id == catalogId){
						html+='<tr><td class="tdClass" style="height:40px;text-align:left;width: 5%; border-bottom:1px #dfdfdf solid; border-right:1px #dfdfdf solid;">&nbsp;&nbsp;&nbsp;&nbsp;'+
						data[i].name+'</td><td class="tdClass" style="width: 5%; border-bottom:1px #dfdfdf solid; border-right:1px #dfdfdf solid;">'+data[i].comment+'</td></tr>';
					}
				}
				for(var i=0; i<data.length; i++){
					if(data[i].id != catalogId){
						html+='<tr><td class="tdClass" style="height:40px;text-align:left;width: 5%; border-bottom:1px #dfdfdf solid; border-right:1px #dfdfdf solid;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
						data[i].name+'</td>' +'<td class="tdClass" style="width: 5%; border-bottom:1px #dfdfdf solid; border-right:1px #dfdfdf solid;">'+data[i].comment+'</td></tr>';
					}
				}
				$('#menuComment').html(html);
			},
			error : function(msg) {
				alertMsg.warn("数据返回异常");
			}
		});
	})
//-->
</script>
<style>
<!--
div#menuCommentDiv {     
	width:100%;   
	height:100%;   
	position:relative;   
}   
div#menuComment {   
	position:absolute;
	width:100%;
    top:20%;   
} 
-->
</style>
<div id="menuCommentDiv">
<div id="menuComment" align="center"></div>
</div>
