<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	ns.email.pageResetMailState = function(id){
		var url = "<%=basePath%>/pages/platform/email/rubbishMail/resetState.action?rubbishMailIDs="+id;
		$.post(url,null,function(json){
			DWZ.ajaxDone(json);
		},'json');
	}
	ns.email.pageDeleteMailRubbish = function(id){
		var url = "<%=basePath%>/pages/platform/email/rubbishMail/delete.action?rubbishMailIDs="+id;
			alertMsg.confirm("删除后数据将不能恢复,您确定吗?", {okCall:function(){ajaxTodo(url,function(json){
				DWZ.ajaxDone(json);
			});}});
	}
	$(function(){
		var totalHeight = $("#container .tabsPageContent").height();
		$("#rubbishMailContentViewDiv").height(totalHeight-35-170-20);
	});
</script>
<div class="pageContent">
	<div class="panelBar">
		<table>
			<tr><td>
				<button type="button" id="sendMailMessage" name="newMailMessage" class="listbutton" style="width:80px;" onclick="ns.email.pageResetMailState('${rubbishMail.id}');" >恢复</button>
				<button type="button" id="draftenMailMessage" name="draftenMailMessage" style="width:80px;" class="listbutton" onclick="ns.email.pageDeleteMailRubbish('${rubbishMail.id}');" >删除</button>				
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
							${rubbishMail.from}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">收件人</td>
						<td align="left" width="90%">${rubbishMail.to}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">抄&nbsp;&nbsp;送</td>
						<td align="left" width="90%">${rubbishMail.cc}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">主&nbsp;&nbsp;题</td>
						<td align="left" width="90%">${rubbishMail.subject}</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">附&nbsp;&nbsp;件</td>
						<td align="left" width="90%">
							<input id="rubbishMailAttachments" type="hidden" name="rubbishMail.attachments" value="${rubbishMail.attachments}"/>
							<div id="uploadify"></div>
						</td>
					</tr>
					<tr><td colspan="2"><div id="rubbishMailContentViewDiv">${rubbishMail.content}</div></td></tr>
				</table>
			</td>
		</tr>
	</table>
</div>
