<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
	<head>
		<title>流程启动--${bpmDefinition.subject} --版本:${bpmDefinition.versionNo}</title>
		<script type="text/javascript" src="<%=basePath %>/js/bpm/BpmImageDialog.js"></script>
		
		<script type="text/javascript">
			var isExtForm=${isExtForm};
			var isFormEmpty=${isFormEmpty};
			var hasLoadComplete=false;
			var actDefId="${bpmDefinition.actDefId}";
			var form;
			$(function(){
				//设置表单。
				initForm();
				//启动流程事件绑定。
				$("button.run").click(startWorkFlow);
				//保存表单
				$("button.save").click(function(){
					eventCompexBC();
				});	
				//选择第一步任务的执行人
				//chooseJumpType();
				setTitle("startFlowFormDialog","流程启动--${bpmDefinition.subject} --版本:${bpmDefinition.versionNo}");
			});
			
			function setTitle(dlgid,title){
				var dialog = $("body").data(dlgid);
				dialog.find(".dialogHeader").find("h1").html(title);
			}
			
			//设置表单。
			function initForm(){
				$.ajaxSetup({async: false});
				compexDataJson('<%=basePath%>/pages/resource/${simpleModel}compexdataJson.action?formId=${formId}&params=&model=${model}&subDomainId=&partitionId=');
				bcUrl = "<%=basePath %>/pages/resource/${simpleModel}compexsave.action";
				$.ajaxSetup({async: true});
			};
			
			function selExeUsers(obj,nodeId){
				var destTaskId=$("#destTask").val();
				$("#lastDestTaskId").val(destTaskId);
				var span=$(obj).siblings("span");
				FlowUserDialog({callback:function(aryTypes,aryIds,aryNames){
					if(aryIds==null) return;
					var aryTmp=[];
					for(var i=0;i<aryIds.length;i++){
						var val=aryTypes[i] +"^" + aryIds[i] +"^" +aryNames[i];
						var check="<input type='checkbox' name='" + nodeId + "_userId' checked='checked' value='"+ val+"'/>&nbsp;"+aryNames[i];
						aryTmp.push(check);
					}
					span.html(aryTmp.join(''));
				}});
			}
			
			//是否点击了开始按钮。
			var isStartFlow=true;
			
			function saveForm(){
				isStartFlow=false;
				var  action="${ctx}/platform/bpm/task/saveData.ht";
				submitForm(action,"a.save");
			}
			
			function startWorkFlow(){
				isStartFlow=true;
				var onsubmit = "return validateCallback(this, saveFormBeforeStartWorkFlow);"
				var mainTab = $("#tabDivId div:first-child").find("form");
				mainTab.attr("onsubmit",onsubmit);
				eventCompexBC();
			}
			
			function saveFormBeforeStartWorkFlow(json){
				if(json.statusCode=="200") {
					var businessKey = json.domainId;
					var action="<%=basePath%>/pages/third/bpm/task/startFlow.action?businessKey=" + businessKey;
					$.ajax({
				  		type:'POST',
				  		dataType:'json',
				  		url:action,
				  		data:{actDefId:actDefId},
				  		success:function(json){
							if(json.statusCode=='200'){//成功
								alertMsg.correct(json.message);
							}
							else{
								alertMsg.error(json.message);
							}
						}
					});
				}else{
					alert(json.message);					
				}
			}
			
			
			
			//表单数据提交。
			//action:表单提交到的URL
			//button：点击按钮的样式。
			function submitForm(action,button){
				var operatorType=(isStartFlow)?1:6;
				//前置事件处理
				var rtn=beforeClick(operatorType);
				if( rtn==false){
					return;
				}
				if($(button).hasClass("disabled"))return;
				if(isFormEmpty){
					$.ligerDialog.warn('流程表单为空，请先设置流程表单!',"提示信息");
					return;
				}
				
				$('#frmWorkFlow').attr("action",action);
				
				if(isExtForm){
					var rtn=form.valid();
					if(rtn){
						
						if(isExtForm){//提交第三方表单时检查该表单的参数
							var frm=$('#frmWorkFlow');
							if(!frm.valid()) return ;
							if(frm.setData)frm.setData();
						}
						
						$(button).addClass("disabled");
						$('#frmWorkFlow').submit();
					}
				}else{
					//获取自定义表单的数据
					var data=CustomForm.getData();
					
					var rtn=CustomForm.validate();
					if(!rtn){
						return;
					}
					//Office控件提交。
					OfficePlugin.submit();
					//获取自定义表单的数据
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					$(button).addClass("disabled");
					$('#frmWorkFlow').submit();
				}
			}
		
			function showBpmImageDlg(){
				BpmImageDialog({actDefId:"${bpmDefinition.actDefId}"});
			}
			
			function BpmImageDialog(c) {
				if (!c) {
					c = {};
				}
				var url = __basePath + "/pages/third/bpm/bpmDefinition/flowImg.action?actDefId="	+ c.actDefId;
				$.pdialog.open(url,"flowImage","流程图",{width:800,height:600,mask:true,resizable:true});
			}
			
			function initSubForm(){
				$('#frmWorkFlow').ajaxForm({success:showResponse }); 
			}
			
			function showResponse(responseText){
				var button=(isStartFlow)? "a.run":"a.save";
				var operatorType=(isStartFlow)?1:6;
				
				$(button).removeClass("disabled");
				var obj=new com.hotent.form.ResultMessage(responseText);
				if(obj.isSuccess()){
					var msg=(isStartFlow)?"启动流程成功!":"保存表单数据成功!";
					$.ligerDialog.success(msg,'提示信息',function(){
						//添加后置事件处理
						var rtn=afterClick(operatorType);
						if( rtn==false){
							return;
						}
						if(window.opener){
							window.opener.location.reload();
							window.close();
						}
					});
					
				}
				else{
					var msg=(isStartFlow)?"启动流程失败!":"保存表单数据失败!";
					$.ligerDialog.err('提示信息',msg,obj.getMessage());
				}
			}
			
			function chooseJumpType(){
				var obj=$('#jumpDiv');
				var url="${ctx}/platform/bpm/task/tranTaskUserMap.ht?actDefId=${bpmDefinition.actDefId}&selectPath=0&isStart=1";
				//url=url.getNewUrl();
				obj.html(obj.attr("tipInfo")).show().load(url);
			}

		</script>
		<style type="text/css" media="print">
			.noprint{display:none;} 
			.printForm{display:block !important;} 
			.noForm{font-size: 14px;font-weight: bold;text-align: center;}
		</style>
	</head>
	<body>
		<div style="display:none;">
			<input id="domainSubmit" type="submit"/>
			<input type="hidden" id="isFirstTab" value="true">
			<input id="formId" name="formId" value="${formId}"/>
			<input id="domainId" name="domainId" value="${domainId}"/>
			<input id="paramsId" name="params" value="${params}"/>
			<input id="mainTable" name="mainTable" value="${mainTable}"/>
		</div>
		<%@include file="incToolBarStart.jsp" %>
		<div class="tabs">
		      <div class="tabsHeader">
		            <div class="tabsHeaderContent">
		                  <ul id="tabLiId">
		                  </ul>
		            </div>
		      </div>
		      <div class="tabsContent" id="tabDivId">
		      </div>
		      <!-- Tab结束层 -->
			  <div class="tabsFooter">
				  <div class="tabsFooterContent"></div>
			  </div>
		</div>
		<input type="hidden" name="actDefId" value="${bpmDefinition.actDefId}"/>
		<input type="hidden" name="defId" value="${bpmDefinition.defId}"/>
		<input type="hidden" name="businessKey" value="${businessKey}"/>
						
	</body>
	
</html>