<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<style type="text/css">
<!--
	.mail-bold-input {font-size: 14px;font-weight: bold;width: 100%;font-family: 'Microsoft Yahei', verdana;}
	.mail-input {font-size: 14px;width: 100%;font-family: 'Microsoft Yahei', verdana;}
-->
</style>
<script type="text/javascript" charset="utf-8">
<!--
	var flag = true;
	<%--发送邮件	--%>
	ns.email.sendMailMessage = function(){
		$('#uploadify').uploadify('upload');
		if(flag){
			var account = $('#mailMessageFromSelect').val();
			if(account){
				$('#mailMessageForm').submit();
			}else{
				alertMsg.warn("您没有设置发邮件账户，请先设置!");
				return false;
			}
		}else{
			alertMsg.warn("附件正在上传，请稍后");
			return false;
		}
	}
	
	ns.email.draftenMailMessage = function(){
		alertMsg.confirm('您确定要将该邮件保存到草稿箱吗?',{
			okCall:function(){
				var url = '<%=basePath%>/pages/platform/email/mailMessage/draften.action';
				$('#mailMessageForm').attr('action',url).submit();
			}
		});
	}
	
	ns.email.resetMailMessage = function(){
		document.getElementById("mailMessageForm").reset();
		$('#mailMessageTo').focus();
	}
	
	ns.email.currentInputbox = null;
	
	ns.email.loadMailAccount = function(){
		$.post('<%=basePath%>/pages/platform/email/mailAccount/fromList.action',null,function(data){
			if(data.length>0){
				var _html ='';
				$.each(data,function(i,account){
					var deflt = account.deflt?'selected="selected"':'';
					_html += '<option value="'+account.address+'"'+deflt+'>'+account.address+'('+account.name+')</option>';
				});
				$('#mailMessageFromSelect').html(_html);
			}else{ //no account was set
				alertMsg.warn("您尚未设置邮箱账号!");
			}
		},'json');
	}
	ns.email.initUploadify = function(name){
		$("#"+name).uploadify({
        	'buttonText' : '添加附件',
        	'method' : 'get',
            'swf'      : '<%=basePath%>/js/uploadify/uploadify.swf',
            'uploader' : '<%=basePath%>/pages/platform/email/mailAttachment/upload.action;jsessionid=${pageContext.session.id}',
            'formData' :{"mailId":"${mailMessage.id}"},
            'fileObjName':'uploadFiles',
            'queueID'  : name+'fileQueue',
            'auto'     : true,
            'removeCompleted' : false,
            'debug':false,
            'height' : 20,
            'width' : 80,
            'onUploadSuccess' : function(file, data, response) {
				flag = true;
	        },
	        'onUploadStart' : function(file) {
	        	flag = false;
	        }
        });
	}
	ns.email.initContactTree = function(treeId){
		var setting = {
			data: {simpleData: {enable: true}},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};
		$.post('<%=basePath%>/pages/platform/email/contact/ztreeList.action',null,function(list){
			$.fn.zTree.init($('#'+treeId),setting, list);
		},'json');
		
		function beforeClick(treeId, treeNode, clickFlag) {
			return true;
		}
		function onClick(event, treeId, treeNode, clickFlag){
			var _value = $(ns.email.currentInputbox).val();
			if($.inArray(treeNode.name,_value.split(';'))>=0){
				return false;
			}
			if(_value.length==0){
				_value = treeNode.name;
			}else{
				_value +=";"+treeNode.name;
			}
			$(ns.email.currentInputbox).val(_value);
		}
	}
	function setCurrentInputbox(dom){
		ns.email.currentInputbox = dom;
	}
	$(function(){
		$.ajaxSetup({async: false});
		ns.email.loadMailAccount();
		ns.email.initUploadify('file_listenAudio');
		ns.email.initContactTree('contactsTree');
		$('#mailMessageTo').focus();
		var totalHeight = $("#container .tabsPageContent").height();
		$("#mailMessageForm textarea").height(totalHeight-35-170-70);
		$.ajaxSetup({async: true});
	});
//-->
</script>
<div class="pageContent">
	<div class="panelBar">
		<table>
			<tr><td>
				<button type="button" id="sendMailMessage" name="newMailMessage" class="listbutton" onclick="ns.email.sendMailMessage();" >发送</button>
				<button type="button" id="draftenMailMessage" name="draftenMailMessage" style="width:80px;" class="listbutton" onclick="ns.email.draftenMailMessage();" >保存草稿</button>				
<%--				<button type="button" id="resetMailMessage" name="resetMailMessage" class="listbutton" onclick="ns.email.resetMailMessage();" >重置</button>				--%>
			</td></tr>
		</table>
	</div>
	<form id="mailMessageForm" method="post" action="<%=basePath%>/pages/platform/email/mailMessage/send.action" class="pageForm required-validate" onsubmit="return validateCallback(this, DWZ.ajaxDone);">
		<input type="hidden" name="mailMessage.id" value="${mailMessage.id}" />
		<table width="100%" border="0">
			<tr>
				<td width="80%" style="padding-bottom:0;">
					<table  width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">发件人</td>
							<td align="left" width="90%">
								<select id="mailMessageFromSelect" name="mailMessage.from" class="mail-input" style="width:500px;height:25px;font-size:14px;"><option value="">请先设置发送邮件账号</option></select>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">收件人</td>
							<td align="left" width="90%">
								<input id="mailMessageTo" type="text" name="mailMessage.to" value="${mailMessage.to}"  class="mail-input required multiEmail" style="width:80%;height:25px;font-size:14px;" onfocus="setCurrentInputbox(this);"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">抄&nbsp;&nbsp;送</td>
							<td align="left" width="90%">
								<input type="text" name="mailMessage.cc" value="${mailMessage.cc}"  class="mail-input multiEmail" style="width:80%;height:25px;font-size:14px;" onfocus="setCurrentInputbox(this);"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">主&nbsp;&nbsp;题</td>
							<td align="left" width="90%">
								<input type="text" name="mailMessage.subject" value="${mailMessage.subject}"  class="mail-bold-input required" x-webkit-speech style="width:80%;height:25px;font-size:14px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">附&nbsp;&nbsp;件</td>
							<td align="left" width="90%" style="padding-top:10px;">
								<input id="file_listenAudio" type="file" />  
								<div id="file_listenAudiofileQueue"></div>
							</td>
						</tr>
						<tr><td colspan="2">
							<div id="mailMessageContentTextareaDiv">
							<textarea class="editor" name="mailMessage.content" tools="simple" style="width:100%;" height="80%">${mailMessage.content}</textarea>
							</div>
						</td></tr>
					</table>
				</td>
				<td width="20%"  align="left" class="Input_Table_Label" valign="top" style="padding:0;border:1px solid #CECCCD;">
					<div class="panelBar" style="width:100%;"><div style="margin-left:10px;line-height:32px;">常用联系人</div></div>
					<div id="contactsTree" class="ztree"></div>
				</td>
			</tr>
		</table>
	</form>
</div>