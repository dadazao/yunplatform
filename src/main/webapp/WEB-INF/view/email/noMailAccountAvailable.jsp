<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
	function gotoEmailCfg(){
		navTab.openTab('mailAccountTab','<%=basePath%>/pages/platform/email/mailAccount/list.action',{title:"邮箱配置"});
	}
	function gotoAddEmailAccount(){
		$.pdialog.open("<%=basePath%>/pages/platform/email/mailAccount/add.action","newMailAccountDialog","新建",{width:950,height:650,mask:true,resizable:true});
	}
</script>
<style type="text/css">
div.noAccount{width:500px;height:150px;background-color:#E5EFFF;margin:100px auto;border: 5px solid #dedede;
    -moz-border-radius: 15px;-webkit-border-radius: 15px;border-radius:15px;}
.noAccountFont{font-family: 'Microsoft Yahei', verdana;font-size:14px;}
div.tip-header{padding:10px 0px 5px 10px;background-color:#E5EFFF;font-size:16px;color:red;font-weight:bold;font-family: 'Microsoft Yahei', verdana;-moz-border-radius: 15px;-webkit-border-radius: 15px;border-radius:15px;}
</style>
<div class="noAccount">
	<div class="tip-header">友情提示</div>
	<div class="divider"></div>
	<div style="margin:40px auto;">
		<span class="noAccountFont" style="padding:20px;">您没有添加邮箱账号,请先在<a onclick="gotoEmailCfg();" class="noAccountFont" style="cursor: pointer;color:blue;">【邮箱配置模块】</a>添加邮箱,再操作</span>
	</div>
</div>