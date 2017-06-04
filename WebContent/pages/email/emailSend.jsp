<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<script type="text/javascript">
			var settings = {
				check : {
					enable : true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data : {
					simpleData : {
						enable : true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: 0						
					}
				},
				callback: {
					onCheck: onCheck
				}
			};
		
			$(document).ready(function() {
<%--				$('ul[class="ztree"]').css({'height':(fDefaultHeight-150)+'px'});--%>
				$.ajax({
					url : "pages/resource/personChoisedepmUserTree.action?treeId=96408e4e0a3c421a867606dbb2503a6c",
					dataType : "json",
					success : function(zNodes) {
						$.fn.zTree.init($("#privilegeTree"), settings, zNodes);
					},
					error : function(msg) {
						alertMsg.warn("数据返回异常");
					}
				});
			})
			
			function onCheck(){
				getCheckedNodes();
			}
			
			function getCheckedNodes(){
				var treeObj = $.fn.zTree.getZTreeObj("privilegeTree");
				var nodes = treeObj.getCheckedNodes();
				if(nodes.length>0){
					var _html = '';
					for (var i=0,j=1; i<nodes.length; i++) {
						if(nodes[i].isParent == false){
							_html += nodes[i].name+';';
							j++;
						}
					}
					$('#recivers').val(_html);
				}else{
					$('#recivers').val('');
				}
			}
			
			function setCheckedNodes(){
				var treeObj = $.fn.zTree.getZTreeObj("privilegeTree");
				var nodes = treeObj.getCheckedNodes();
				if(nodes.length==0){
					alertMsg.warn("请选择接收人！");
					return false;
				}
				var _userId = '';
				var _personName = '';
				for (var i=0; i<nodes.length; i++) {
					if(nodes[i].isParent == false){
						var nodeId = nodes[i].id;
						if(nodeId != ''){
							_userId += nodeId+";";
						}
					}
				}
				$("#receiverIds").val(_userId);
				return true;
			}
			
			function closeCurPage(){
				$.pdialog.close("emailDialog");
			}
			//发送邮件
			function send(){
				//先验证外部邮箱地址是否正确
				if(emailCheck()){
					//根据选择的树，设置收件人id
					if(setCheckedNodes()){
						$("#sendForm").submit();
					}
				}
			}
			//验证邮箱格式
			function emailCheck(){
				var pattern = new RegExp(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/);
				var emailStr = $("#outreceicers").val();
				var isTrue = true;
				if(emailStr.trim()!=''){
					var emails = emailStr.split(';');
					var _html = '';
					for(var i=0;i<emails.length;i++){
						var email = emails[i];
						if(!pattern.test(email)){
							_html+='<span style="color:red;">'+email+'</span>;';
							isTrue = false;						
						}
					}
					if(!isTrue){
						alertMsg.error("以下邮箱格式不正确，请修改后再发送!<br/><p>"+_html+"</p>",{time:20});
					}
				}
				return isTrue;
			}
		</script>
		<style>
			ul.ztree {
				border: 1px solid #617775;
				overflow-y: scroll;
				overflow-x: auto;
			}
			.userArea,.emailtitle,.emailtext{
				border-bottom:1px solid #CECCCD;
				margin:2px 0px;
			}
		</style>
	</head>

	<body>
	<div id="emailDialog">
		<div class="tabs" ><%--
			      <div class="tabsHeader">
			            <div class="tabsHeaderContent">
			                  <ul>
			                        <li class="selected"><a><span>邮件通知</span></a></li>
			                  </ul>
			            </div>
			      </div>
			      --%><div class="tabsContent" style="overflow:hidden;border-top:1px #b8d1d6 solid;border-bottom:1px #b8d1d6 solid;">
			            <div id="treeTable" style="width:100%;height:100%">
			            <table width="98%" border="0" cellspacing="0" cellpadding="0" style=" padding: 0px; margin: 0px; border: 1px #b8d1d6 solid; width: 100%; height: 100%; display: table; background-color: #eef3f6; font-size: 12px;">
						<tr>
						<!-- 左边区域，生成树 -->
							<td align="center" valign="top" style="padding: 7px;">
								<table border="0" cellspacing="0" cellpadding="0">
								<tr><td style="background-image: url(images/system/grayLeft.jpg); width: 3px; height: 22px; padding: 0px; background-repeat: no-repeat;"></td><td align="left" style="background-image: url(images/system/gray.jpg); height: 22px; width: 37%; padding: 0px; background-repeat: repeat-x; font-size: 12px; color: #0B062E;font-weight:bold; line-height: 22px;">&nbsp;人员选择树</td><td style="background-image: url(images/system/img.jpg); padding: 0px; width: 21px; height: 22px; background-repeat: no-repeat;"></td><td id="ztreeButton" style="background-image: url(images/system/blue.jpg); padding: 0px; height: 22px; width: 53%; background-repeat: repeat-x; text-align: right;">
								</td><td style="background-image: url(images/system/blueRight.jpg); padding: 0px; width: 3px; height: 22px; background-repeat: no-repeat;"></td></tr>
								<tr><td colspan="5" style="background-color: #ffffff; padding: 0px;">
								<ul id="privilegeTree" class="ztree" style="width: 225px;height:500px;overflow-y: scroll; overflow-x: scroll; background-color: #FFFFFF;"></ul></td></tr>
								</table>
							</td>
						   <td valign="top" style="padding: 0px; width: 100%; border-left: 1px #b8d1d6 solid;">
								<form action="pages/module/mail/send.action" id="sendForm" method="post" onsubmit="return validateCallback(this,null)" >
							    	<input type="hidden" id="formId" name="formId" value="${formId}"/>
							    	<input type="hidden" id="domainId" name="domainId" value="${domainId}"/>
							    	<input type="hidden" id="mainTable" name="mainTable" value="${mainTable}"/>
								<div style="border-bottom:1px solid #B8D1D6;padding-bottom:2px;margin-top:5px;">
									<ul>
										<table valign="top" border="0" cellspacing="0" cellpadding="0" width="100%" style="padding:2px 7px 2px 7px;"><tr><td style="background-image: url(images/system/grayLeft.jpg); width: 1%; height: 22px; padding: 0px; background-repeat: no-repeat;"></td>
										<td align="left" style="background-image: url(images/system/gray.jpg); height: 22px; width: 23%; padding: 0px; background-repeat: repeat-x; font-size: 12px; color: #0B062E;font-weight:bold; line-height: 22px;">&nbsp;收件人</td>
										<td style="background-image: url(images/system/img.jpg); padding: 0px; width: 50%; height: 22px; background-repeat: no-repeat;"></td>
										<td id="operAreaButton" align="left" style="background-image: url(images/system/blue.jpg); padding: 0px; height: 22px; width: 20%; background-repeat: repeat-x; text-align: right;">
										</td>
										<td style="background-image: url(images/system/blueRight.jpg); padding: 0px; width: 0.5%; height: 22px; background-repeat: no-repeat;"></td></tr>
										</table>
									</ul>
									<ul class="ztree_table" style="width: 100%;overflow-x: hidden;">
										<div style="margin-left:5px;font-weight:bold; border-top:1px solid #B8D1D6;">内部联系人</div>
										<input type="text" id="recivers" name="receivers" disabled="disabled" style="margin:5px 0px 2px 5px;width:655px;height:25px;" class="disabled" />
										<div style="margin-left:5px;"><span style="font-weight:bold;">外部联系人邮件地址</span>(多个请用英文分号;隔开)</div>
										<textarea id="outreceicers" class="textarea" name="outrecievers" style="margin:5px 0px 2px 5px;width:655px;height:25px;"></textarea>
										<input type="hidden" id="receiverIds" name="receivers" value=""/>
									</ul>
									
								</div>
								<div id="operArea" style="margin-top:2px;width:100%;height:340px;">
									<ul>
									<table valign="top" border="0" cellspacing="0" cellpadding="0" width="100%" style="padding:2px 7px 2px 7px;">
										<tr>
											<td style="background-image: url(images/system/grayLeft.jpg); width: 1%; height: 22px; padding: 0px; background-repeat: no-repeat;"></td>
											<td align="left" style="background-image: url(images/system/gray.jpg); height: 22px; width: 23%; padding: 0px; background-repeat: repeat-x; font-size: 12px; color: #0B062E;font-weight:bold; line-height: 22px;">&nbsp;邮件内容</td>
											<td style="background-image: url(images/system/img.jpg); padding: 0px; width: 50%; height: 22px; background-repeat: no-repeat;"></td>
											<td id="operAreaButton" align="left" style="background-image: url(images/system/blue.jpg); padding: 0px; height: 22px; width: 20%; background-repeat: repeat-x; text-align: right;"></td>
											<td style="background-image: url(images/system/blueRight.jpg); padding: 0px; width: 0.5%; height: 22px; background-repeat: no-repeat;"></td>
										</tr>
									</table>
									</ul>
									<ul style="width:100%;overflow-x:hidden;overflow-y:hidden;">
										<li style="width:100%;margin-left:5px;">
										<div style="width:100%;height:25px;border:1px solid #B8D1D6;padding:5px 0px 5px 5px;">
										<span style="line-height:25px;font-weight:bold;">邮件标题：</span>
										<span style="line-height:25px;">
											<input value="" name="mailSubject" id="mailSubject" class="textInput" style="width:590px;height:18px;" maxlength="100"/>
										</span></div>
										<div style="width:100%;height:300px;overflow-y:hidden">
										<textarea class="editor textInput" id="emailcont" name="mailContent" rows="18" cols="125">${mailContent}</textarea>
										</div>
										</li>
									</ul>
								</div>
								
								<div style="height:30px;margin-top:30px;float:left;">
									<div id="NoticeWay" style="border:1px solid #b8d1d6;margin-left:5px;padding:0px 5px;height:30px;float:left;line-height:30px;">
										<b>提醒方式：</b>
										<input type="checkbox" name="noticeWay" value="message" checked="checked"/>消息提醒								
										<input type="checkbox" name="noticeWay" value="email" checked="checked"/>邮件提醒	
									</div>
								</div>
								
								</form>
								<div style="margin-top:30px;">
									<button style="width:60px;heith:21px;float:right; margin:5px 5px;" onclick="closeCurPage();" class="listbutton">取消</button>
									<button style="width:60px;heith:21px;float:right; margin:5px 5px;" onclick="send();" class="listbutton">发送</button>
								</div>
							</td>
						</tr>
						</table>
						</div>
					</div>
		</div>	
	</div>
</body>
</html>
