<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/commons/include/customForm.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>打印流程表单</title>
<script type="text/javascript">
	$(function(){
		$(".taskopinion").each(function(){
			$(this).removeClass("taskopinion");
			var actInstId=$(this).attr("instanceId");
			$(this).load("${ctx}/platform/bpm/taskOpinion/listform.ht?actInstId="+actInstId);
		});
	});
	
	function onPrint(){
		//记录日志
		//$.post(url, {data : JSON2.stringify(data)});
		//打印
		window.print();
	};

	function reloadUseDefaultForm(obj){
		var param={
				genDefault:1
			}

		var url = window.location.href;
		if(url.indexOf("?")>0){
			window.location.href = url+"&genDefault=1";
		}else{
			window.location.href = url+"?genDefault=1";
		}
	};
</script>
<style type="text/css" media="print">
	.noprint{display:none;} 
	.printForm{display:"block";} 
</style>
</head>
<body>
	<div class="panal">
		<div class="panel-toolbar noprint">
			<div class="toolBar">
				<div class="group noprint"><a class="link print" onclick="onPrint();"><span></span>打印</a></div>
			</div>
		</div>
		<div class="panel-body">
			<div class="printForm">
		<c:choose>
			<c:when test="${not empty html}">${html}</c:when>
			<c:otherwise>
				<table align="center"  height="111" width="457" border="0" cellpadding="0" cellspacing="0">
				  <tbody>
				  	   <tr valign="middle" >
					    <td height="111" valign="middle" width="480" background="/ecp/styles/default/images/warm.gif">
						    <table height="89" width="449" border="0" cellpadding="0" cellspacing="0">
						      <tbody>
							      <tr>
							        <td height="12" width="67">&nbsp;</td>
							        <td colspan="2">&nbsp;</td>
							      </tr>
							      <tr>
							        <td rowspan="2" height="45">&nbsp;</td>
							        <td height="44" valign="top" width="390">&nbsp;</td>
							        <td valign="middle" width="50">&nbsp;</td>
							      </tr>
							      <tr>
							        <td height="30" valign="top"><span class="STYLE1">该流程表单尚未添加打印模版，是否使用默认打印模版？<a href="#" onclick="reloadUseDefaultForm(this)">【确定】</a></span></td>
							        <td valign="bottom" width="50"><a href="#"></a></td>
							      </tr>
							    </tbody>
						    </table>
					    </td>
					  </tr>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
		</div>
		</div>
	</div>
</body>
</html>
<%--update 2013-1-1 end--%>