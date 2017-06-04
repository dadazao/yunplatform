<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	ns.email.initMailAccountTree = function(){
		var setting = {data: {simpleData: {enable: true,idKey:'id',pIdKey:'pId',rootPId:null},key: {title: "title"}},callback: {beforeClick: beforeClick,onClick: onClick}};
		$.post('<%=basePath%>/pages/platform/email/mailAccount/ztreeMailAccount.action',null,function(list){
			$.fn.zTree.init($('#mailBoxTree'),setting, list);
		},'json');
		function beforeClick(treeId, treeNode, clickFlag) {
			return true;
		}
		function onClick(event, treeId, treeNode, clickFlag){
			if(treeNode.isParent){
				return false;
			}
			var url = null;
			if(treeNode.kind=='inbox'){
				url = '<%=basePath%>/pages/platform/email/inboxMessage/inbox.action?email='+treeNode.email+'&mailAccountId='+treeNode.accountId;
			}else if(treeNode.kind=='outbox'){
				url = '<%=basePath%>/pages/platform/email/mailMessage/outbox.action?email='+treeNode.email;
			}else if(treeNode.kind=='draftbox'){
				url = '<%=basePath%>/pages/platform/email/mailMessage/draftbox.action';
			}else if(treeNode.kind=='dustbin'){
				url = '<%=basePath%>/pages/platform/email/rubbishMail/list.action';
			}
			$('#mailBoxListArea').loadUrl(url);
		}
	}

	$(function(){
		$('#mailBoxListArea').loadUrl('<%=basePath%>/pages/platform/email/inboxMessage/defaultInbox.action');
		ns.email.initMailAccountTree();
	});
//-->
</script>
<div class="pageContent">
	<div layoutH="1">
		<table  width="100%" height="100%" border="0">
			<tr>
				<td width="15%" style="padding:0;border:1px solid #CECCCD;border-top:0px;" valign="top">
					<div class="panelBar" style="width:100%;"><div style="margin-left:10px;line-height:32px;">邮箱账号</div></div>
					<ul id="mailBoxTree" class="ztree"></ul>
				</td>
				<td width="85%"  align="left" class="Input_Table_Label" valign="top" style="padding:0;border-bottom:1px solid #CECCCD;">
					<div id="mailBoxListArea"></div>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
	//写邮件
	ns.email.newMailMessage = function(){
		navTab.openTab('SendMessageTab','<%=basePath%>/pages/platform/email/mailMessage/create.action',{title:'写邮件'});
	}
</script>