<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	ns.email.replyMessage = function(msgid,email){
		navTab.openTab('SendMessageTab','<%=basePath %>/pages/platform/email/inboxMessage/reply.action?inboxMessageId='+msgid+'&email='+email,{title:'回复邮件'});
	}
	ns.email.downloadFile = function(id){
		window.open('<%=basePath%>/pages/platform/email/mailAttachment/download.action?id='+id);
	}
	$(function(){
		var totalHeight = $("#container .tabsPageContent").height();
		$("#MailContentDiv").height(totalHeight-35-170-20);
	});
</script>
<div class="pageContent">
	<div class="panelBar">
		<table>
			<tr><td>
			<button type="button" class="listbutton" onclick="ns.email.replyMessage('${inboxMessage.id}','${inboxMessage.from}');" >回复</button>
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
							${inboxMessage.from}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">收件人</td>
						<td align="left" width="90%">${inboxMessage.to}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">抄&nbsp;&nbsp;送</td>
						<td align="left" width="90%">${inboxMessage.cc}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">主&nbsp;&nbsp;题</td>
						<td align="left" width="90%">${inboxMessage.subject}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">附&nbsp;&nbsp;件</td>
						<td align="left" width="90%">
							<div id="uploadify">
								${inboxMessage.attachments}
							</div>
						</td>
					</tr>
					<tr height="100%">
						<td colspan="2">
						<div id="MailContentDiv">${inboxMessage.content}</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>