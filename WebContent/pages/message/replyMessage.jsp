<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	ns.message.sendReplyMessage = function(){
		$('#replyMessageForm').submit();
		$.pdialog.closeCurrent();
	}
</script>
<div class="buttonPanel">
	<button type="button" class="listbutton" onclick="ns.message.sendReplyMessage();">回复</button>
</div>
<div id="receivedMessageDialog">
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul id="tabLiId"><li class="selected"><a><span>基本信息</span></a></li></ul>
	            </div>
	      </div>
	      <div class="tabsContent" id="tabDivId">
			<div>
				<form id="replyMessageForm" method="post" action="<%=basePath%>/pages/platform/message/sentMessage/send.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input type="hidden" name="sentMessage.replyMessageId" value="${sentMessage.replyMessageId}"/>
					<input type="hidden" name="sentMessage.receiver" value="${sentMessage.receiver}"/>
					<input type="hidden" name="sentMessage.subject" value="${sentMessage.subject}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">标题</td>
							<td align="left" width="90%">${receivedMessage.subject}</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">内容</td>
							<td align="left" width="90%">${receivedMessage.content}</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">回复内容</td>
							<td align="center" width="90%">
								<textarea name="sentMessage.content" class="editor" tools="simple" style="width:100%;" rows="16" ></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>	      	
		</div>
		<div class="tabsFooter"><div class="tabsFooterContent"></div></div>
	</div>
</div>
