<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	ns.email.reSendMailMessage = function(id){
		navTab.openTab('SendMessageTab','<%=basePath%>/pages/platform/email/mailMessage/resend.action?mailMessageId='+id,{title:'再次发送'});
	}
	ns.email.downloadFile = function(id){
		window.open('<%=basePath%>/pages/platform/email/mailAttachment/download.action?id='+id);
	}
	ns.email.redirectSendMailMessage = function(id){
		navTab.openTab('SendMessageTab','<%=basePath%>/pages/platform/email/mailMessage/forward.action?mailMessageId='+id,{title:'转发邮件'});
	}
	$(function(){
		var totalHeight = $("#container .tabsPageContent").height();
		$("#mailMessageContentViewDiv").height(totalHeight-35-170-20);
	});
</script>
<div class="pageContent">
	<div class="panelBar">
		<table>
			<tr><td>
				<button type="button" class="listbutton" style="width:80px;" onclick="ns.email.reSendMailMessage('${mailMessage.id}');" >再次发送</button>
				<button type="button" style="width:80px;" class="listbutton" onclick="ns.email.redirectSendMailMessage('${mailMessage.id}');" >转发</button>				
			</td></tr>
		</table>
	</div>
	<table  width="100%" border="0">
		<tr>
			<td width="100%">
				<table  width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">发件人</td>
						<td align="left" width="90%">
							${mailMessage.from}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">收件人</td>
						<td align="left" width="90%">${mailMessage.to}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">抄&nbsp;&nbsp;送</td>
						<td align="left" width="90%">${mailMessage.cc}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">主&nbsp;&nbsp;题</td>
						<td align="left" width="90%">${mailMessage.subject}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">附&nbsp;&nbsp;件</td>
						<td align="left" width="90%">
							${mailMessage.attachments}
						</td>
					</tr>
					<tr><td colspan="2"><div id="mailMessageContentViewDiv">${mailMessage.content}</div></td></tr>
				</table>
			</td>
		</tr>
	</table>
</div>