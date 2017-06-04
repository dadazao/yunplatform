<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	ns.message.sendMessage = function(){
		var $form = $('#sentMessageForm');
		if (!$form.valid()) {
			return false;
		}
		
		var receiver = $("#sentMessageReceiverHidden").val();
		var group = $("#sentMessageReceiveGroupsHidden").val();
		if(receiver||group){
			$form.submit();
		}else{
			alertMsg.warn("请选择收信人或组织");
			return false;
		}
	}
	ns.message.resetInput = function(inputId){
		$('#'+inputId+',#'+inputId+'Hidden').val('');
	}
	ns.message.selectUsers = function(){
		var callbackFunction = function(userIds,fullNames){
			$("#sentMessageReceiver").val(fullNames);
			$("#sentMessageReceiverHidden").val(userIds);
		};
		ns.common.userDialog({callback:callbackFunction});
	}
	ns.message.selectOrgs = function(){
		var callbackFunction = function(orgIds,orgNames){
			$("#sentMessageReceiveGroups").val(orgNames);
			$("#sentMessageReceiveGroupsHidden").val(orgIds);
		};
		ns.common.orgDialog({callback:callbackFunction});
	}
	
</script>
<div id="sentMessageDialog">
	<div>
		<form id="sentMessageForm" method="post" action="<%=basePath%>/pages/platform/message/sentMessage/send.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
	  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
				<tr>
					<td align="left" class="Input_Table_Label" width="10%">标题</td>
					<td align="left" width="90%">
						<input type="text" name="sentMessage.subject" value="${sentMessage.subject}"  class="textInput required" style="width:500px;height:25px;" maxlength="200" />
					</td>
				</tr>
				<tr>
					<td align="left" class="Input_Table_Label" width="10%">收信人</td>
					<td align="left" width="90%">
						<input type="text" id="sentMessageReceiver"  class="textInput readonly" style="width: 500px;height:25px;" readonly="readonly">
						<input type="hidden" id="sentMessageReceiverHidden" name="sentMessage.receiver" value="${sentMessage.receiveGroups}"/>
						<button type="button" class="listbutton" onclick="ns.message.selectUsers();">选择</button>
						<button type="button" class="listbutton" onclick="ns.message.resetInput('sentMessageReceiver')">清空</button>
					</td>
				</tr>
				<tr>
					<td align="left" class="Input_Table_Label" width="10%">收信组织</td>
					<td align="left" width="90%">
						<input type="text" id="sentMessageReceiveGroups" class="textInput readonly" style="width: 500px;height:25px;" readonly="readonly">
						<input type="hidden" id="sentMessageReceiveGroupsHidden" name="sentMessage.receiveGroups" value="${sentMessage.receiveGroups}"/>
						<button type="button" class="listbutton" onclick="ns.message.selectOrgs();">选择</button>
						<button type="button" class="listbutton" onclick="ns.message.resetInput('sentMessageReceiveGroups')">清空</button>
					</td>
				</tr>
				<tr>
					<td align="left" class="Input_Table_Label" width="10%">需要回复</td>
					<td align="left" width="90%">
						<input type="radio" name="sentMessage.needReply" value="true" />是
						<input type="radio" name="sentMessage.needReply" value="false" checked="checked" />否
					</td>
				</tr>
				<tr>
					<td align="left" class="Input_Table_Label" width="10%">内容</td>
					<td align="center" width="90%">
						<textarea class="editor" name="sentMessage.content" rows="16" tools="simple" style="width:100%;">${sentMessage.content}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>	      	
</div>
<div class="buttonPanel" style="text-align:center;margin-top:10px;">
	<button type="button" class="listbutton" onclick="ns.message.sendMessage();" style="width: 60px;">发送</button>
</div>
