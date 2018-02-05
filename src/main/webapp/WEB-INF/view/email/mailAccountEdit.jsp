<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" charset="utf-8">
<!--

	ns.email.connectTest = function(){
		$form = $('#mailAccountForm');
		if (!$form.valid()) {
			return false;
		}
		$.ajax({
			type: 'POST',
			url:'<%=basePath%>/pages/platform/email/mailAccount/connectTest.action',
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	
	ns.email.saveMailAccount = function(){
		$('#mailAccountForm').submit();
	}
	
	ns.email.changeServerType = function(dom){
		$type = $(dom);
		var _type = $type.val();
		if(_type=="POP3"){
			$('#serverTypeTD').html('POP3主机');
			$('#serverPortTD').html('POP3端口');
		}else if(_type=="IMAP"){
			$('#serverTypeTD').html('IMAP主机');
			$('#serverPortTD').html('IMAP端口');
		}
		return true;
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editMailAccount" name="editMailAccount" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveMailAccount" name="saveMailAccount" class="listbutton" onclick="ns.email.saveMailAccount();">保存</button>
	<button type="button" id="connectTest" name="saveMailAccount" class="listbutton" style="width:80px;" onclick="ns.email.connectTest()">连接测试</button>
</div>
<div id="mailAccountDialog">
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul id="tabLiId">
	                  	<li class="selected"><a><span>基本信息</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" id="tabDivId">
			<div>
				<form id="mailAccountForm" method="post" action="<%=basePath%>/pages/platform/email/mailAccount/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="mailAccountId" type=hidden name="mailAccount.id" value="${mailAccount.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${mailAccount.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								账号名称
							</td>
							<td align="left" width="40%">
								<input type="text" name="mailAccount.name" value="${mailAccount.name}"  class="textInput required" style="width:360px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">邮箱类型</td>
							<td align="left" width="40%">
								<select name="mailAccount.mailType" style="width:180px;" onchange="ns.email.changeServerType(this);">
									<option value="POP3" selected="selected">POP3</option>
									<option value="IMAP">IMAP</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								邮箱地址
							</td>
							<td align="left" width="40%">
								<input type="text" name="mailAccount.address" value="${mailAccount.address}"  class="textInput required email" style="width:360px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								邮箱密码
							</td>
							<td align="left" width="40%">
								<input type="password" name="mailAccount.password" value="${mailAccount.password}"  class="textInput required" style="width:360px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								SMTP主机
							</td>
							<td align="left" width="40%">
								<input type="text" name="mailAccount.smtpHost" value="${mailAccount.smtpHost}"  class="textInput required" style="width:360px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								SMTP端口
							</td>
							<td align="left" width="40%">
								<input type="text" name="mailAccount.smtpPort" value="${mailAccount.smtpPort}"  class="textInput required digits" style="width:360px;"/>
							</td>
						</tr>
						<tr>
							<td id="serverTypeTD" align="left" class="Input_Table_Label" width="10%">
								POP主机
							</td>
							<td align="left" width="40%">
								<input type="text" name="mailAccount.popHost" value="${mailAccount.popHost}"  class="textInput required" style="width:360px;"/>
							</td>
						</tr>
						<tr>
							<td id="serverPortTD" align="left" class="Input_Table_Label" width="10%">
								POP端口
							</td>
							<td align="left" width="40%">
								<input type="text" name="mailAccount.popPort" value="${mailAccount.popPort}"  class="textInput required digits" style="width:360px;"/>
							</td>
						</tr>
					</table>
				</form>
			</div>	      	
		</div>
	      <!-- Tab结束层 -->
		  <div class="tabsFooter">
			  <div class="tabsFooterContent"></div>
		  </div>
	</div>
</div>