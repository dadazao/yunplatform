<%
	//某个任务节点的手机访问的设置
%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>手机访问配置</title>
    <%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript">
		$(function(){
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			$('#setMobileForm').ajaxForm(options);
			$(".save").click(function(){
				$('#setMobileForm').submit();
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.success(obj.getMessage(),"提示信息", function(rtn) {
					if(rtn){
						window.close();
					}
				});
			} else {
				$.ligerDialog.err("出错信息","编辑手机访问设置失败",obj.getMessage());
			}
		}
	</script>
</head>
<body>
<div class="panel-top">
	<div class="tbar-title">
		<span class="tbar-label">
			设置流程任务节点手机访问
	    </span>
	</div>
	<div class="panel-toolbar">
		<div class="toolBar">
			<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
			<div class="l-bar-separator"></div>
			<div class="group"><a class="link del" href="#" onclick="window.close()"><span></span>关闭</a></div>
		</div>
	</div>
</div>
<form id="setMobileForm" method="post" action="setMobile.ht">
	<div style="padding:8px 8px 8px 8px">
		<b>说明：</b>
		<ul>
			<li>若勾选，用户可使用手机登录该系统对该任务进行处理</li>
		</ul>
	</div>
	<div style="padding:8px 8px 8px 8px" id="nodeTypeDiv">
		<label><b>${bpmNodeSet.nodeName} : </b></label><input type="checkbox" name="isAllowMobile" value="1" <c:if test="${bpmNodeSet.isAllowMobile==1}">checked="checked"</c:if> />允许手机访问
		<input type="hidden" name="actDefId" value="${bpmNodeSet.actDefId}" />
		<input type="hidden" name="nodeId" value="${bpmNodeSet.nodeId}" />
	</div>
</form>
</body>
</html>