<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editMailAccount" name="editMailAccount" class="listbutton" onclick="ns.email.editMailAccount('${mailAccount.id}');">修改</button>
	<button type="button" id="saveMailAccount" name="saveMailAccount" class="listbuttonDisable" disabled="disabled">保存</button>
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
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							账号名称
						</td>
						<td align="left" width="40%">
							${mailAccount.name}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">邮箱类型</td>
						<td align="left" width="40%">
							${mailAccount.mailType}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							邮箱地址
						</td>
						<td align="left" width="40%">
							${mailAccount.address}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							邮箱密码
						</td>
						<td align="left" width="40%">**********</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							SMTP主机
						</td>
						<td align="left" width="40%">
							${mailAccount.smtpHost}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							SMTP端口
						</td>
						<td align="left" width="40%">
							${mailAccount.smtpPort}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							POP主机
						</td>
						<td align="left" width="40%">
							${mailAccount.popHost}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							POP端口
						</td>
						<td align="left" width="40%">
							${mailAccount.popPort}
						</td>
					</tr>
				</table>
		</div>
	      <!-- Tab结束层 -->
		  <div class="tabsFooter">
			  <div class="tabsFooterContent"></div>
		  </div>
	</div>
</div>


